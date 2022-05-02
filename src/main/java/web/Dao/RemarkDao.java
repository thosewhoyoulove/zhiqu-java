package web.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import web.Entry.Remark;

import java.util.List;

@Repository
public interface RemarkDao {
    public int addRemark(Remark remark);

    public List<Remark> getSpecificFileRemark(@Param("file_id") String file_id);

    public int deleteARecord(@Param("remark_id") String remark_id);
}
