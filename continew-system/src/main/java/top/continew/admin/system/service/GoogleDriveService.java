package top.continew.admin.system.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class GoogleDriveService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${google.drive.service-account-key-path}")
    private Resource serviceAccountKeyFile;

    @Value("${google.drive.application-name}")
    private String applicationName;

    private Drive drive;

    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        log.info("初始化 Google Drive 服务...");
        InputStream in = serviceAccountKeyFile.getInputStream();
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(in)
                .createScoped(Collections.singleton(DriveScopes.DRIVE_READONLY));

        drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(applicationName).build();

        log.info("Google Drive 服务初始化成功");
    }

    /**
     * 获取所有 Google Docs 文档
     */
    public List<File> listAllDocs() throws IOException {
        List<File> allFiles = new ArrayList<>();
        String pageToken = null;

        do {
            FileList result = drive.files().list()
                    .setQ("mimeType='application/vnd.google-apps.document'")
                    .setFields("nextPageToken, files(id, name, createdTime, modifiedTime)")
                    .setPageToken(pageToken)
                    .execute();
            if (result.getFiles() != null) {
                allFiles.addAll(result.getFiles());
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);

        return allFiles;
    }

    /**
     * 获取指定时间之后新增的文档
     */
    public List<File> listNewDocsAfter(String isoDateTime) throws IOException {
        List<File> newFiles = new ArrayList<>();
        String pageToken = null;
        String query = String.format("mimeType='application/vnd.google-apps.document' and createdTime > '%s'", isoDateTime);

        do {
            FileList result = drive.files().list()
                    .setQ(query)
                    .setFields("nextPageToken, files(id, name, createdTime, modifiedTime)")
                    .setPageToken(pageToken)
                    .execute();

            if (result.getFiles() != null) {
                newFiles.addAll(result.getFiles());
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);

        return newFiles;
    }
}
