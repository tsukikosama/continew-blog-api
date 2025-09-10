package top.continew.admin.api;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.continew.admin.system.model.resp.file.FileUploadResp;
import top.continew.admin.system.service.FileService;
import top.continew.starter.core.validation.ValidationUtils;
import top.continew.starter.log.annotation.Log;

import java.io.IOException;

@Tag(name = "公共 API")
@Log(ignore = true)
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
@SaIgnore
public class CommonApiController {

    private final FileService fileService;
    @Operation(summary = "上传文件", description = "上传文件")
    @PostMapping("/file")
    public FileUploadResp upload(@NotNull(message = "文件不能为空") @RequestParam("file") MultipartFile file) throws IOException {
        ValidationUtils.throwIf(file::isEmpty, "文件不能为空");
        FileInfo fileInfo = fileService.upload(file);

        return FileUploadResp.builder()
                .id(fileInfo.getId())
                .url(fileInfo.getUrl())
                .thUrl(fileInfo.getThUrl())
                .metadata(fileInfo.getMetadata())
                .build();
    }
}
