package web.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import web.Entry.File;

import java.util.List;

@Repository
public interface FileDao {
    public List<File> getFileList();
    public int addFileLike(@Param("file_id") String file_id, @Param("like_count") int like_count);
    public File getSingleFile(@Param("file_id") String file_id);
}
