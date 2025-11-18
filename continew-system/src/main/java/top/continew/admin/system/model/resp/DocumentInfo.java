package top.continew.admin.system.model.resp;

public  class DocumentInfo {
        private String documentId;
        private String title;
        private String content;

        public DocumentInfo(String documentId, String title, String content) {
            this.documentId = documentId;
            this.title = title;
            this.content = content;
        }

        // Getters
        public String getDocumentId() { return documentId; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
    }