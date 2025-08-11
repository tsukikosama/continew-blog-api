package top.continew.admin.api;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.exceptions.CheckedUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.continew.starter.core.validation.CheckUtils;

import java.io.File;
import java.util.Random;

import static cn.hutool.crypto.digest.DigestUtil.sha256Hex;


@Tag(name = "通用API")
@RestController
@RequestMapping(value = "api/common")
public class CommonApiController {
    @Value("${file.image.folder}")
    private String IMAGE_DIR;

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
