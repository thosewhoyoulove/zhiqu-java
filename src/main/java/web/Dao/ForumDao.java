package web.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import web.Entry.Forum;
import web.Entry.Remark;

import java.util.List;

@Repository
public interface ForumDao {
    public List<Forum> getAllTag();
    public int addTag(Forum forum);
    public int addForumRecord(Remark remark);
    public Forum getSpecificForum(@Param("forum_id") String forum_id);
    public List<Remark> getSpecificForumAllRecord(@Param("forum_id") String forum_id);
    public int deleteAForumRecord(@Param("remark_id") String remark_id);
}
