package web.Controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.Dao.RemarkDao;
import web.Entry.Remark;
import web.Entry.Result;
import web.Utils.ResultUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class RemarkController {
    @Autowired
    RemarkDao remarkDao;

    //添加一条评论
    @RequestMapping("/addRecord")
    public Result addRecord(@RequestParam("user_id") String user_id, @RequestParam("file_id") String file_id, @RequestParam("remark_content") String remark_content) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String remark_time = new SimpleDateFormat("yyyy年MM月dd日HH:mm").format(new Date());
        String remark_id = "remark_" + timestamp;
        Remark remark = new Remark(remark_id, user_id, file_id, remark_content, remark_time);
        if (remarkDao.addRemark(remark) > 0) {
            return ResultUtils.success("添加成功");
        }

        return ResultUtils.error(300, "添加失败");
    }

    //获取对应文件的评论
    @RequestMapping("/getSpecificFileRemark")
    public Result getSpecificFileRemark(@RequestParam("file_id") String file_id) {
        List<Remark> remarkList = remarkDao.getSpecificFileRemark(file_id);
        if (remarkList.size() == 0){
            return ResultUtils.error(300, "列表为空");
        }
        return ResultUtils.data(remarkList, "获取成功");
    }

    //删除一条评论
    @RequestMapping("/deleteARecord")
    public Result deleteARecord(@RequestParam("remark_id") String remark_id) {
        if (remarkDao.deleteARecord(remark_id) > 0) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error(300, "删除失败");
    }
}
