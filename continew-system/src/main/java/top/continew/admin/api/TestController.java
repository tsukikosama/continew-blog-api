package top.continew.admin.api;

import cn.dev33.satoken.annotation.SaIgnore;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.DocsScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.admin.system.service.GoogleDocService;
import top.continew.admin.system.service.GoogleDriveService;
import top.continew.starter.log.annotation.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Tag(name = "测试API")
@Log(ignore = true)
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
@SaIgnore
@Slf4j
public class TestController {

    private final GoogleDocService googleDocService;

    private final GoogleDriveService googleDriveService;
    @Value("${google.docs.service-account-key-path}")
    private Resource serviceAccountKeyFile;
    @GetMapping("/doc")
    public Object getDoc() throws IOException {
        return googleDocService.readDocument("1tY5L3oY0HFGZY3e8PGloLN_anMwX4GFERiFSMuPTOOE");
    }

    private static final List<String> SCOPES = Arrays.asList(
            // Docs API 权限
            DocsScopes.DOCUMENTS,
            // Drive API 权限，用于访问共享文档
            "https://www.googleapis.com/auth/drive"
    );

    @GetMapping("/debug-auth")
    public ResponseEntity<Map<String, Object>> debugAuth() {
        Map<String, Object> result = new HashMap<>();

        try {
            log.info("=== 开始调试认证 ===");

            // 1. 读取文件
            InputStream in = serviceAccountKeyFile.getInputStream();
            result.put("step1_fileRead", "成功");

            // 2. 创建凭据
            GoogleCredentials credentials = ServiceAccountCredentials
                    .fromStream(in)
                    .createScoped(SCOPES);
            result.put("step2_credentialsCreated", "成功");
            log.info("凭据创建成功");

            // 3. 刷新令牌（这里会调用 Google OAuth API）
            try {
                credentials.refreshIfExpired();
                result.put("step3_tokenRefresh", "成功");
                log.info("令牌刷新成功");

                // 4. 获取访问令牌
                var token = credentials.getAccessToken();
                if (token != null) {
                    result.put("step4_tokenValue", token.getTokenValue().substring(0, 20) + "...");
                    result.put("step4_tokenExpiry", token.getExpirationTime().toString());
                }

            } catch (Exception e) {
                result.put("step3_tokenRefresh_error", e.getMessage());
                result.put("step3_errorType", e.getClass().getName());
                log.error("令牌刷新失败", e);

                // 详细错误信息
                if (e.getCause() != null) {
                    result.put("step3_causeMessage", e.getCause().getMessage());
                }
            }

            // 5. 尝试创建 Docs 服务
            try {
                NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                Docs docs = new Docs.Builder(
                        httpTransport,
                        GsonFactory.getDefaultInstance(),
                        new HttpCredentialsAdapter(credentials))
                        .setApplicationName("Test App")
                        .build();

                result.put("step5_docsServiceCreated", "成功");

                // 6. 尝试创建一个测试文档
                try {
                    var doc = docs.documents()
                            .create(new com.google.api.services.docs.v1.model.Document()
                                    .setTitle("Auth Test - " + System.currentTimeMillis()))
                            .execute();

                    result.put("step6_documentCreated", "成功");
                    result.put("step6_documentId", doc.getDocumentId());
                    result.put("step6_documentUrl",
                            "https://docs.google.com/document/d/" + doc.getDocumentId() + "/edit");

                } catch (Exception e) {
                    result.put("step6_documentCreate_error", e.getMessage());
                    result.put("step6_errorType", e.getClass().getName());
                    log.error("文档创建失败", e);
                }

            } catch (Exception e) {
                result.put("step5_docsService_error", e.getMessage());
                log.error("Docs 服务创建失败", e);
            }

        } catch (Exception e) {
            result.put("error", e.getMessage());
            result.put("errorType", e.getClass().getName());
            log.error("调试过程出错", e);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/docList")
    public List<File> getDocList() throws IOException {
        return googleDriveService.listAllDocs();
    }
}
