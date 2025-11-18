package top.continew.admin.job;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.continew.admin.blog.model.entity.BlogDO;
import top.continew.admin.blog.service.BlogService;
import top.continew.admin.system.model.resp.DocumentInfo;
import top.continew.admin.system.service.GoogleDocService;
import top.continew.admin.system.service.GoogleDriveService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 谷歌文档的定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleDocJob {

    private final BlogService blogService;

    private final GoogleDocService googleDocService;

    private final GoogleDriveService googleDriveService;
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void publishNoticeWithSchedule() {
        log.info("========开始获取谷歌文档数据=======");
        //每天0点执行一次同步文档功能
        try {
            List<File> files = googleDriveService.listAllDocs();
            List<BlogDO> blogDOS = new ArrayList<>();
            for (File file : files) {
                //校验当前的id是否被保存过了
                BlogDO blogDO = blogService.getBlogByGoogleId(file.getId());
                if (ObjectUtil.isNull(blogDO)){
                    DocumentInfo documentInfo = googleDocService.readDocument(file.getId());
                    BlogDO blog = new BlogDO();
                    blog.setGoogleDocId(file.getId());
                    blog.setTitle(documentInfo.getTitle());
                    blog.setContent(documentInfo.getContent());
                    blog.setSimpleTitle(documentInfo.getTitle());
                    blog.setVisit(0);
                    blog.setUserId(1L);
                    blog.setStatus(1);
                    blog.setCreateUser(1L);
                    blog.setCreateTime(LocalDateTime.now());
                    blogDOS.add(blog);
                }
            }
            blogService.batchSave(blogDOS);
        log.info("获取谷歌文档数据完毕,本次一共保存{}个文档",blogDOS.size());
        }catch (IOException e){
            log.info("获取谷歌文档内容失败{}",e);
        }
    }
}
