package top.continew.admin.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.blog.model.resp.ApiBlogResp;
import top.continew.admin.blog.model.resp.ApiFriendLinkResp;
import top.continew.admin.blog.service.BlogService;
import top.continew.admin.blog.service.FriendlinkService;
import top.continew.starter.extension.crud.model.resp.BasePageResp;

@Tag(name = "友链API")
@RestController
@RequestMapping(value = "/api/friendLink")
@RequiredArgsConstructor
public class FriendlinkApiController {
    private final FriendlinkService friendlinkService;

    @GetMapping("/page")
    public BasePageResp<ApiFriendLinkResp> page() {
        return new BasePageResp<>();
    }
}
