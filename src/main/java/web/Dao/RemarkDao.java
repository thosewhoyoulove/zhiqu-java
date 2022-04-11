package web.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import web.Entry.Remark;

@Repository
public interface RemarkDao {
    public int addRemark(Remark remark);
}
