package top.continew.admin.api;

import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.continew.admin.system.model.resp.file.FileUploadResp;
import top.continew.admin.system.service.FileService;
import top.continew.starter.core.validation.CheckUtils;
import top.continew.starter.core.validation.ValidationUtils;
import top.continew.starter.log.annotation.Log;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static cn.hutool.crypto.digest.DigestUtil.sha256Hex;

@Tag(name = "公共 API")
@Log(ignore = true)
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
@SaIgnore
public class CommonApiController {
    @Value("${file.image.folder}")
    private String IMAGE_DIR;

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

    @SaIgnore
    @GetMapping("/random")
    public String getRandomUrl(@RequestParam("path")String path, @RequestParam("t")String t, @RequestParam("sign")String sign){

        String secret = "wscywlwhh"; // 同前端混淆秘钥
        String raw = path + "|" + t + "|" + secret;
        String reversed = new StringBuilder(raw).reverse().toString();
        String expectedSign = sha256Hex(reversed);

        CheckUtils.throwIfNotEqual(expectedSign,sign,"签名不正确");

        File dir = new File(IMAGE_DIR);
        String[] files = dir.list((d, name) -> {
            // 过滤图片格式，根据需要调整
            String lower = name.toLowerCase();
            return lower.endsWith(".jpg") || lower.endsWith(".png") || lower.endsWith(".jpeg") || lower.endsWith(".gif");
        });

        if (files == null || files.length == 0) {
            return "";
        }
        int randomIndex = new Random().nextInt(files.length);
        return files[randomIndex];

    }
}
