package top.continew.admin.system.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.continew.admin.system.model.resp.DocumentInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 谷歌文档api服务
 * @author weilai
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleDocService {

    private final Docs docsService;


    /**
     * 创建新文档
     * @param title 文档标题
     * @return 文档ID
     */
    public String createDocument(String title) throws IOException {
        Document doc = new Document().setTitle(title);
        doc = docsService.documents().create(doc).execute();
        String documentId = doc.getDocumentId();

        log.info("创建文档成功: {} (ID: {})", title, documentId);
        log.info("访问链接: https://docs.google.com/document/d/{}/edit", documentId);
        return documentId;
    }

    /**
     * 读取文档内容
     * @param documentId 文档ID
     * @return 文档文本内容
     */
    public  DocumentInfo readDocument(String documentId) throws IOException {
        Document document = docsService.documents().get(documentId).execute();

        String title = document.getTitle();
        log.info("读取文档: {}", title);
        StringBuilder content = new StringBuilder();
        List<StructuralElement> elements = document.getBody().getContent();
        if (elements != null) {
            for (StructuralElement element : elements) {
                if (element.getParagraph() != null) {
                    Paragraph paragraph = element.getParagraph();
                    List<ParagraphElement> paragraphElements = paragraph.getElements();

                    if (paragraphElements != null) {
                        for (ParagraphElement paragraphElement : paragraphElements) {
                            // 处理文字
                            TextRun textRun = paragraphElement.getTextRun();
                            if (textRun != null && textRun.getContent() != null) {
                                content.append(textRun.getContent());
                            }
                            // 处理图片（InlineObject）
                            if (paragraphElement.getInlineObjectElement() != null) {
                                String objectId = paragraphElement.getInlineObjectElement().getInlineObjectId();
                                InlineObject inlineObject = document.getInlineObjects().get(objectId);

                                if (inlineObject != null && inlineObject.getInlineObjectProperties() != null) {
                                    EmbeddedObject embeddedObject = inlineObject.getInlineObjectProperties().getEmbeddedObject();

                                    if (embeddedObject != null && embeddedObject.getImageProperties() != null) {
                                        // Google Docs API 不提供直接 URL
                                        log.info("找到图片 objectId={}, 需要使用 Drive API 下载或通过共享访问", objectId);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new DocumentInfo(documentId, title, content.toString());
    }



}
