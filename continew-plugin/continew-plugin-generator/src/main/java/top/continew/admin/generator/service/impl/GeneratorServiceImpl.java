/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.extra.template.engine.freemarker.FreemarkerEngine;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.admin.generator.config.properties.GeneratorProperties;
import top.continew.admin.generator.enums.FormTypeEnum;
import top.continew.admin.generator.enums.QueryTypeEnum;
import top.continew.admin.generator.mapper.FieldConfigMapper;
import top.continew.admin.generator.mapper.GenConfigMapper;
import top.continew.admin.generator.model.entity.FieldConfigDO;
import top.continew.admin.generator.model.entity.GenConfigDO;
import top.continew.admin.generator.model.entity.InnerGenConfigDO;
import top.continew.admin.generator.model.query.GenConfigQuery;
import top.continew.admin.generator.model.req.GenConfigReq;
import top.continew.admin.generator.model.resp.GeneratePreviewResp;
import top.continew.admin.generator.service.GeneratorService;
import top.continew.starter.core.autoconfigure.project.ProjectProperties;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.enums.BaseEnum;
import top.continew.starter.core.exception.BusinessException;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.data.core.enums.DatabaseType;
import top.continew.starter.data.core.util.MetaUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.core.util.FileUploadUtils;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 代码生成业务实现
 *
 * @author Charles7c
 * @since 2023/4/12 23:58
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final DataSource dataSource;
    private final GeneratorProperties generatorProperties;
    private final ProjectProperties projectProperties;
    private final FieldConfigMapper fieldConfigMapper;
    private final GenConfigMapper genConfigMapper;
    private static final List<String> TIME_PACKAGE_CLASS = Arrays.asList("LocalDate", "LocalTime", "LocalDateTime");

    @Override
    public PageResp<GenConfigDO> pageGenConfig(GenConfigQuery query, PageQuery pageQuery) {
        // 查询所有表
        List<Table> tableList = MetaUtils.getTables(dataSource);
        tableList.removeIf(table -> StrUtil.equalsAnyIgnoreCase(table.getTableName(), generatorProperties
            .getExcludeTables()));
        String tableName = query.getTableName();
        if (StrUtil.isNotBlank(tableName)) {
            tableList.removeIf(table -> !StrUtil.containsAnyIgnoreCase(table.getTableName(), tableName));
        }
        // 查询生成配置
        List<GenConfigDO> list = tableList.parallelStream().map(table -> {
            GenConfigDO genConfig = genConfigMapper.selectById(table.getTableName());
            if (genConfig == null) {
                genConfig = new GenConfigDO(table.getTableName());
            }
            genConfig.setComment(table.getComment());
            return genConfig;
        })
            .sorted(Comparator.comparing(GenConfigDO::getTableName)
                .thenComparing(GenConfigDO::getUpdateTime, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(GenConfigDO::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder())))
            .toList();
        // 分页
        return PageResp.build(pageQuery.getPage(), pageQuery.getSize(), list);
    }

    @Override
    public GenConfigDO getGenConfig(String tableName) throws SQLException {
        GenConfigDO genConfig = genConfigMapper.selectById(tableName);
        if (genConfig == null) {
            genConfig = new GenConfigDO(tableName);
            // 默认包名（当前包名）
            String packageName = ClassUtil.getPackage(GeneratorService.class);
            genConfig.setPackageName(StrUtil.subBefore(packageName, StringConstants.DOT, true));
            // 默认业务名（表注释）
            List<Table> tableList = MetaUtils.getTables(dataSource, tableName);
            if (CollUtil.isNotEmpty(tableList)) {
                Table table = tableList.get(0);
                genConfig.setBusinessName(StrUtil.replace(table.getComment(), "表", StringConstants.EMPTY));
            }
            // 默认作者名称（上次保存使用的作者名称）
            GenConfigDO lastGenConfig = genConfigMapper.selectOne(Wrappers.lambdaQuery(GenConfigDO.class)
                .orderByDesc(GenConfigDO::getCreateTime)
                .last("LIMIT 1"));
            if (lastGenConfig != null) {
                genConfig.setAuthor(lastGenConfig.getAuthor());
            }
        }
        return genConfig;
    }

    @Override
    public List<FieldConfigDO> listFieldConfig(String tableName, Boolean requireSync) {
        List<FieldConfigDO> fieldConfigList = fieldConfigMapper.selectListByTableName(tableName);
        if (CollUtil.isNotEmpty(fieldConfigList) && Boolean.FALSE.equals(requireSync)) {
            return fieldConfigList;
        }
        List<FieldConfigDO> latestFieldConfigList = new ArrayList<>();
        // 获取最新数据表列信息
        Collection<Column> columnList = MetaUtils.getColumns(dataSource, tableName);
        // 获取数据库对应的类型映射配置
        DatabaseType databaseType = MetaUtils.getDatabaseType(dataSource);
        Map<String, List<String>> typeMappingMap = generatorProperties.getTypeMappings().get(databaseType);
        CheckUtils.throwIfEmpty(typeMappingMap, "请先配置对应数据库的类型映射");
        Set<Map.Entry<String, List<String>>> typeMappingEntrySet = typeMappingMap.entrySet();
        // 新增或更新字段配置
        Map<String, FieldConfigDO> fieldConfigMap = fieldConfigList.stream()
            .collect(Collectors.toMap(FieldConfigDO::getColumnName, Function.identity(), (key1, key2) -> key2));
        int i = 1;
        for (Column column : columnList) {
            FieldConfigDO fieldConfig = Optional.ofNullable(fieldConfigMap.get(column.getName()))
                .orElseGet(() -> new FieldConfigDO(column));
            // 更新已有字段配置
            if (fieldConfig.getCreateTime() != null) {
                fieldConfig.setColumnType(column.getTypeName());
                fieldConfig.setColumnSize(column.getSize());
            }
            String fieldType = typeMappingEntrySet.stream()
                .filter(entry -> entry.getValue().contains(fieldConfig.getColumnType()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
            fieldConfig.setFieldType(fieldType);
            fieldConfig.setFieldSort(i++);
            latestFieldConfigList.add(fieldConfig);
        }
        return latestFieldConfigList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveConfig(GenConfigReq req, String tableName) {
        // 保存字段配置（先删除再保存）
        fieldConfigMapper.delete(Wrappers.lambdaQuery(FieldConfigDO.class).eq(FieldConfigDO::getTableName, tableName));
        List<FieldConfigDO> fieldConfigList = req.getFieldConfigs();
        for (int i = 0; i < fieldConfigList.size(); i++) {
            FieldConfigDO fieldConfig = fieldConfigList.get(i);
            // 重新设置排序
            fieldConfig.setFieldSort(i + 1);
            if (Boolean.TRUE.equals(fieldConfig.getShowInForm())) {
                fieldConfig.setFormType(ObjectUtil.defaultIfNull(fieldConfig.getFormType(), FormTypeEnum.INPUT));
            } else {
                // 在表单中不显示，不需要设置必填
                fieldConfig.setIsRequired(false);
            }
            if (Boolean.TRUE.equals(fieldConfig.getShowInQuery())) {
                fieldConfig.setFormType(ObjectUtil.defaultIfNull(fieldConfig.getFormType(), FormTypeEnum.INPUT));
                fieldConfig.setQueryType(ObjectUtil.defaultIfNull(fieldConfig.getQueryType(), QueryTypeEnum.EQ));
            } else {
                // 在查询中不显示，不需要设置查询方式
                fieldConfig.setQueryType(null);
            }
            // 既不在表单也不在查询中显示，不需要设置表单类型
            if (Boolean.FALSE.equals(fieldConfig.getShowInForm()) && Boolean.FALSE.equals(fieldConfig
                .getShowInQuery())) {
                fieldConfig.setFormType(null);
            }
            fieldConfig.setTableName(tableName);
        }
        fieldConfigMapper.insert(fieldConfigList);
        // 保存或更新生成配置信息
        GenConfigDO newGenConfig = req.getGenConfig();
        GenConfigDO oldGenConfig = genConfigMapper.selectById(tableName);
        if (oldGenConfig != null) {
            BeanUtil.copyProperties(newGenConfig, oldGenConfig);
            genConfigMapper.updateById(oldGenConfig);
        } else {
            genConfigMapper.insert(newGenConfig);
        }
    }

    @Override
    public List<GeneratePreviewResp> preview(List<String> tableNames) {
        List<GeneratePreviewResp> generatePreviewList = new ArrayList<>();
        for (String tableName : tableNames) {
            generatePreviewList.addAll(this.preview(tableName));
        }
        return generatePreviewList;
    }

    @Override
    public void downloadCode(List<String> tableNames, HttpServletResponse response) {
        try {
            String tempDir = SystemUtil.getUserInfo().getTempDir();
            // 删除旧代码
            FileUtil.del(tempDir + projectProperties.getAppName());
            tableNames.forEach(tableName -> {
                // 初始化配置及数据
                List<GeneratePreviewResp> generatePreviewList = this.preview(tableName);
                // 生成代码
                this.generateCode(generatePreviewList, genConfigMapper.selectById(tableName));
            });
            // 打包下载
            File tempDirFile = new File(tempDir, projectProperties.getAppName());
            String zipFilePath = tempDirFile.getPath() + jodd.io.ZipUtil.ZIP_EXT;
            ZipUtil.zip(tempDirFile.getPath(), zipFilePath);
            FileUploadUtils.download(response, new File(zipFilePath));
        } catch (Exception e) {
            log.error("Generate code of table '{}' occurred an error. {}", tableNames, e.getMessage(), e);
            throw new BusinessException("代码生成失败，请手动清理生成文件");
        }
    }

    @Override
    public void generateCode(List<String> tableNames) {
        try {
            String projectPath = System.getProperty("user.dir");
            tableNames.forEach(tableName -> {
                // 初始化配置及数据
                List<GeneratePreviewResp> generatePreviewList = this.preview(tableName);
                // 生成代码
                for (GeneratePreviewResp generatePreview : generatePreviewList) {
                    // 后端：continew-admin/continew-system/src/main/java/top/continew/admin/system/service/impl/XxxServiceImpl.java
                    // 前端：continew-admin/continew-admin-ui/src/views/system/user/index.vue
                    File file = new File(projectPath + generatePreview.getPath()
                        .replace("continew-admin\\continew-admin", ""), generatePreview.getFileName());
                    // 如果已经存在，且不允许覆盖，则跳过
                    if (!file.exists() || Boolean.TRUE.equals(genConfigMapper.selectById(tableName).getIsOverride())) {
                        FileUtil.writeUtf8String(generatePreview.getContent(), file);
                    }
                }
            });

        } catch (Exception e) {
            log.error("Generate code of table '{}' occurred an error. {}", tableNames, e.getMessage(), e);
            throw new BusinessException("代码生成失败，请手动清理生成文件");
        }
    }

    /**
     * 生成预览
     *
     * @param tableName 表名称
     * @return 预览信息
     */
    private List<GeneratePreviewResp> preview(String tableName) {
        List<GeneratePreviewResp> generatePreviewList = new ArrayList<>();
        // 初始化配置
        GenConfigDO genConfig = genConfigMapper.selectById(tableName);
        CheckUtils.throwIfNull(genConfig, "请先进行数据表 [{}] 生成配置", tableName);
        List<FieldConfigDO> fieldConfigList = fieldConfigMapper.selectListByTableName(tableName);
        CheckUtils.throwIfEmpty(fieldConfigList, "请先进行数据表 [{}] 字段配置", tableName);

        InnerGenConfigDO innerGenConfig = new InnerGenConfigDO(genConfig);
        List<String> imports = new ArrayList<>();
        // 处理枚举字段
        List<FieldConfigDO> fieldConfigRecords = fieldConfigList.stream()
            .map(s -> convertToFieldConfigDO(s, imports))
            .toList();
        innerGenConfig.setImports(imports);

        // 渲染代码
        String classNamePrefix = innerGenConfig.getClassNamePrefix();
        Map<String, GeneratorProperties.TemplateConfig> templateConfigMap = generatorProperties.getTemplateConfigs();
        TemplateEngine engine = TemplateUtil
            .createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        // 在模板中允许使用静态方法
        if (engine instanceof FreemarkerEngine freemarkerEngine) {
            DefaultObjectWrapper wrapper = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_33).build();
            freemarkerEngine.getConfiguration().setSharedVariable("statics", wrapper.getStaticModels());
        }
        for (Map.Entry<String, GeneratorProperties.TemplateConfig> templateConfigEntry : templateConfigMap.entrySet()) {
            GeneratorProperties.TemplateConfig templateConfig = templateConfigEntry.getValue();
            // 移除需要忽略的字段
            innerGenConfig.setFieldConfigs(fieldConfigRecords.stream()
                .filter(fieldConfig -> !StrUtil.equalsAny(fieldConfig.getFieldName(), templateConfig
                    .getExcludeFields()))
                .toList());
            // 预处理配置
            this.pretreatment(innerGenConfig);
            // 处理其他配置
            innerGenConfig.setSubPackageName(templateConfig.getPackageName());
            String classNameSuffix = templateConfigEntry.getKey();
            String className = classNamePrefix + StrUtil.blankToDefault(templateConfig.getSuffix(), classNameSuffix);
            innerGenConfig.setClassName(className);
            boolean isBackend = templateConfig.isBackend();
            String extension = templateConfig.getExtension();
            GeneratePreviewResp generatePreview = new GeneratePreviewResp();
            generatePreview.setBackend(isBackend);
            generatePreviewList.add(generatePreview);
            String fileName = className + extension;
            if (!isBackend) {
                fileName = ".vue".equals(extension) && "index".equals(classNameSuffix)
                    ? "index.vue"
                    : this.getFrontendFileName(classNamePrefix, className, extension);
            }
            generatePreview.setFileName(fileName);
            generatePreview.setContent(engine.getTemplate(templateConfig.getTemplatePath())
                .render(BeanUtil.beanToMap(innerGenConfig)));
            this.setPreviewPath(generatePreview, innerGenConfig, templateConfig);
        }
        return generatePreviewList;
    }

    /**
     * 添加枚举类型的属性，生成对应的import
     *
     * @param fieldConfigDO 属性配置信息
     * @param imports       待导入包集合
     * @return 新的属性配置信息
     */
    private FieldConfigDO convertToFieldConfigDO(FieldConfigDO fieldConfigDO, List<String> imports) {
        FieldConfigDO fieldConfig = new FieldConfigDO();
        BeanUtil.copyProperties(fieldConfigDO, fieldConfig);
        String dictCode = fieldConfig.getDictCode();
        if (StringUtils.isBlank(dictCode)) {
            return fieldConfig;
        }
        Set<Class<?>> classSet = ClassUtil.scanPackageBySuper(projectProperties.getBasePackage(), BaseEnum.class);
        Optional<Class<?>> clazzOptional = classSet.stream()
            .filter(s -> StrUtil.toUnderlineCase(s.getSimpleName()).toLowerCase().equals(dictCode))
            .findFirst();
        if (clazzOptional.isEmpty()) {
            return fieldConfig;
        }
        Class<?> clazz = clazzOptional.get();
        imports.add(clazz.getName());
        fieldConfig.setFieldType(clazz.getSimpleName());
        return fieldConfig;
    }

    /**
     * 设置预览路径
     *
     * @param generatePreview 预览信息
     * @param genConfig       生成配置
     * @param templateConfig  模板配置
     */
    private void setPreviewPath(GeneratePreviewResp generatePreview,
                                InnerGenConfigDO genConfig,
                                GeneratorProperties.TemplateConfig templateConfig) {
        // 获取前后端基础路径
        String backendBasicPackagePath = this.buildBackendBasicPackagePath(genConfig, templateConfig);
        String frontendBasicPackagePath = String.join(File.separator, projectProperties.getAppName(), projectProperties
            .getAppName() + "-ui");
        String packagePath;
        if (generatePreview.isBackend()) {
            // 例如：continew-admin/continew-system/src/main/java/top/continew/admin/system/service/impl
            packagePath = String.join(File.separator, backendBasicPackagePath, templateConfig.getPackageName()
                .replace(StringConstants.DOT, File.separator));
        } else {
            // 例如：continew-admin/continew-admin-ui/src/views/system
            packagePath = String.join(File.separator, frontendBasicPackagePath, templateConfig.getPackageName()
                .replace(StringConstants.SLASH, File.separator), genConfig.getApiModuleName());
            // 例如：continew-admin/continew-admin-ui/src/views/system/user
            packagePath = ".vue".equals(templateConfig.getExtension())
                ? packagePath + File.separator + StrUtil.lowerFirst(genConfig.getClassNamePrefix())
                : packagePath;
        }
        generatePreview.setPath(packagePath);
    }

    /**
     * 生成代码
     *
     * @param generatePreviewList 生成预览列表
     * @param genConfig           生成配置
     */
    private void generateCode(List<GeneratePreviewResp> generatePreviewList, GenConfigDO genConfig) {
        for (GeneratePreviewResp generatePreview : generatePreviewList) {
            // 后端：continew-admin/continew-system/src/main/java/top/continew/admin/system/service/impl/XxxServiceImpl.java
            // 前端：continew-admin/continew-admin-ui/src/views/system/user/index.vue
            File file = new File(SystemUtil.getUserInfo().getTempDir() + generatePreview.getPath(), generatePreview
                .getFileName());
            // 如果已经存在，且不允许覆盖，则跳过
            if (!file.exists() || Boolean.TRUE.equals(genConfig.getIsOverride())) {
                FileUtil.writeUtf8String(generatePreview.getContent(), file);
            }
        }
    }

    /**
     * 构建后端包路径
     *
     * @param genConfig      生成配置
     * @param templateConfig 模板配置
     * @return 后端包路径
     */
    private String buildBackendBasicPackagePath(GenConfigDO genConfig,
                                                GeneratorProperties.TemplateConfig templateConfig) {
        String extension = templateConfig.getExtension();
        // 例如：continew-admin/continew-system/src/main/java/top/continew/admin/system
        return String.join(File.separator, projectProperties.getAppName(), projectProperties.getAppName(), genConfig
            .getModuleName(), "src", "main", FileNameUtil.EXT_JAVA.equals(extension)
                ? "java"
                : "resources") + (FileNameUtil.EXT_JAVA.equals(extension)
                    ? File.separator + genConfig.getPackageName().replace(StringConstants.DOT, File.separator)
                    : StringConstants.EMPTY);
    }

    /**
     * 获取前端文件名
     *
     * @param classNamePrefix 类名前缀
     * @param className       类名
     * @param extension       扩展名
     * @return 前端文件名
     */
    private String getFrontendFileName(String classNamePrefix, String className, String extension) {
        return (".ts".equals(extension) ? StrUtil.lowerFirst(classNamePrefix) : className) + extension;
    }

    /**
     * 预处理生成配置
     *
     * @param genConfig 生成配置
     */
    private void pretreatment(InnerGenConfigDO genConfig) {
        List<FieldConfigDO> fieldConfigList = genConfig.getFieldConfigs();
        // 统计部分特殊字段特征
        Set<String> dictCodeSet = new HashSet<>();
        for (FieldConfigDO fieldConfig : fieldConfigList) {
            String fieldType = fieldConfig.getFieldType();
            // 必填项
            if (Boolean.TRUE.equals(fieldConfig.getIsRequired())) {
                genConfig.setHasRequiredField(true);
            }
            // 数据类型
            if ("BigDecimal".equals(fieldType)) {
                genConfig.setHasBigDecimalField(true);
            }
            if (TIME_PACKAGE_CLASS.contains(fieldType)) {
                genConfig.setHasTimeField(true);
            }
            // 字典码
            if (StrUtil.isNotBlank(fieldConfig.getDictCode())) {
                genConfig.setHasDictField(true);
                dictCodeSet.add(fieldConfig.getDictCode());
            }
        }
        genConfig.setDictCodes(dictCodeSet);
    }
}
