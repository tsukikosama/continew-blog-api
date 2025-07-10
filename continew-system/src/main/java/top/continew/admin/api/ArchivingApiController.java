package top.continew.admin.api;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.blog.model.resp.ArchiveResp;
import top.continew.admin.blog.service.BlogService;

import java.util.List;

@Tag(name = "归档api")
@RestController
@RequestMapping(value = "/api/archive")
@RequiredArgsConstructor
public class ArchivingApiController {

    private final BlogService blogService;

    @GetMapping("/list")
    @Operation(summary = "归档",description = "归档")
    public List<ArchiveResp> getArchive(){
       return this.blogService.getArchive(StpUtil.getLoginIdAsLong());
    }
}
