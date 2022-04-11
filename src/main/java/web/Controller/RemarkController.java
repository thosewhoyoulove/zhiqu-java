package web.Controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.Dao.RemarkDao;
import web.Dao.VisitDao;
import web.Entry.Remark;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class RemarkController {
    @Autowired
    RemarkDao remarkDao;

    //添加一条评论
    @RequestMapping("/addRecord")
    public boolean addRecord(@RequestParam("user_id") String user_id, @RequestParam("file_id") String file_id, @RequestParam("remark_content") String remark_content) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String remark_time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String remark_id = "remark_" + timestamp;
        Remark remark = new Remark(remark_id, user_id, file_id, remark_content, remark_time);
        if (remarkDao.addRemark(remark) > 0){
            return true;
        }

        return false;
    }
}
