package top.continew.admin.blog.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.BaseServiceImpl;
import top.continew.admin.blog.mapper.ModuleMapper;
import top.continew.admin.blog.model.entity.ModuleDO;
import top.continew.admin.blog.model.query.ModuleQuery;
import top.continew.admin.blog.model.req.ModuleReq;
import top.continew.admin.blog.model.resp.ModuleDetailResp;
import top.continew.admin.blog.model.resp.ModuleResp;
import top.continew.admin.blog.service.ModuleService;

/**
 * 模块业务实现
 *
 * @author weilai
 * @since 2025/07/20 10:56
 */
@Service
@RequiredArgsConstructor
public class ModuleServiceImpl extends BaseServiceImpl<ModuleMapper, ModuleDO, ModuleResp, ModuleDetailResp, ModuleQuery, ModuleReq> implements ModuleService {}