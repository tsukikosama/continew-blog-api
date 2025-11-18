package top.continew.admin.config.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.DocsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
public class GoogleDocsConfig {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DocsScopes.DOCUMENTS);

    @Value("${google.docs.service-account-key-path}")
    private Resource serviceAccountKeyFile;

    @Value("${google.docs.application-name}")
    private String applicationName;



    /**
     * 创建 Google Docs 服务 Bean（使用 Service Account）
     */
    @Bean
    public Docs googleDocsService() {
        try {
            log.info("开始初始化 Google Docs 服务（Service Account 模式）...");
            log.info("应用名称: {}", applicationName);
            log.info("Service Account 密钥文件: {}", serviceAccountKeyFile.getDescription());

            // 检查文件是否存在
            if (!serviceAccountKeyFile.exists()) {
                throw new IllegalStateException(
                        "Service Account 密钥文件不存在: " + serviceAccountKeyFile.getDescription()
                );
            }

            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            GoogleCredentials credentials = getCredentials();

            Docs docs = new Docs.Builder(
                    httpTransport,
                    JSON_FACTORY,
                    new HttpCredentialsAdapter(credentials))
                    .setApplicationName(applicationName)
                    .build();

            log.info("Google Docs 服务初始化成功（Service Account）");
            return docs;

        } catch (GeneralSecurityException e) {
            log.error("安全异常: {}", e.getMessage(), e);
            throw new RuntimeException("Google Docs 服务初始化失败 - 安全异常", e);
        } catch (IOException e) {
            log.error("IO 异常: {}", e.getMessage(), e);
            throw new RuntimeException("Google Docs 服务初始化失败 - IO 异常", e);
        } catch (Exception e) {
            log.error("未知异常: {}", e.getMessage(), e);
            throw new RuntimeException("Google Docs 服务初始化失败", e);
        }
    }

    /**
     * 获取 Service Account 凭据
     */
    private GoogleCredentials getCredentials() throws IOException {
        log.info("加载 Service Account 凭据...");
        InputStream in = serviceAccountKeyFile.getInputStream();
        GoogleCredentials credentials = ServiceAccountCredentials
                .fromStream(in)
                .createScoped(SCOPES);
        log.info("Service Account 凭据加载成功");
        return credentials;
    }

}
