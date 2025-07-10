package top.continew.admin.api;


import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.blog.model.query.TagQuery;
import top.continew.admin.blog.model.req.TagReq;
import top.continew.admin.blog.model.resp.ApiTagResp;
import top.continew.admin.blog.model.resp.TagDetailResp;
import top.continew.admin.blog.model.resp.TagResp;
import top.continew.admin.blog.service.TagService;
import top.continew.admin.common.controller.BaseController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;
import top.continew.starter.extension.crud.model.query.SortQuery;

import java.util.List;

/**
 * 标签管理 API
 *
 * @author weilai
 * @since 2025/07/08 13:45
 */
@Tag(name = "web标签管理 API")
@RestController
@CrudRequestMapping(value = "/api/tag", api = {Api.PAGE, Api.GET, Api.CREATE, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class TagApiController  extends BaseController<TagService, TagResp, TagDetailResp, TagQuery, TagReq> {
    @GetMapping("/list")
    public List<ApiTagResp> list(){
        List<TagResp> list = this.baseService.list(new TagQuery(), new SortQuery());
        return BeanUtil.copyToList(list, ApiTagResp.class);
    }

}
