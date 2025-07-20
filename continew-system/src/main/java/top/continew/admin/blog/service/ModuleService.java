package top.continew.admin.blog.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.blog.model.query.ModuleQuery;
import top.continew.admin.blog.model.req.ModuleReq;
import top.continew.admin.blog.model.resp.ModuleDetailResp;
import top.continew.admin.blog.model.resp.ModuleResp;

/**
 * 模块业务接口
 *
 * @author weilai
 * @since 2025/07/20 10:56
 */
public interface ModuleService extends BaseService<ModuleResp, ModuleDetailResp, ModuleQuery, ModuleReq> {}