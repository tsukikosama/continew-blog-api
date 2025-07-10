package top.continew.admin.blog.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "归档resp")
public class ArchiveResp {
    private String year;
    private List<ArchiveItem> archiveList;

    public class ArchiveItem{
        private Long id;
        private String title;
        private LocalDate createTime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public LocalDate getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDate createTime) {
            this.createTime = createTime;
        }
    }
}


