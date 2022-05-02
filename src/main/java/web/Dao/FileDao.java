package web.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import web.Entry.File;

import java.util.List;

@Repository
public interface FileDao {
    public List<File> getFileList();

    public int addFileLike(@Param("file_id") String file_id, @Param("like_count") int like_count);

    public int addFileDownload(@Param("file_id") String file_id, @Param("download_count") int download_count);

    public int addFileClick(@Param("file_id") String file_id, @Param("click_count") int click_count);

    public File getSingleFile(@Param("file_id") String file_id);

    public int addFile(File file);

    public File getFileByFileID(@Param("file_id") String file_id);

    public boolean deleteDBFile(@Param("file_id") String file_id);

    public List<File> getSpecificFileList(@Param("file_subject") String file_subject, @Param("level") int level, @Param("file_type") String file_type);
}
