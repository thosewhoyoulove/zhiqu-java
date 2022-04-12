package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.Dao.VisitDao;
import web.Entry.Visit;
import web.Utils.IfTodayUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class VisitController {
    @Autowired
    VisitDao visitDao;

    //增加一条访问量
    @RequestMapping("/addVisitCount")
    public boolean addVisitCount(@RequestParam("user_id") String user_id) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String visit_time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());

        if (visitDao.addVisitCount(new Visit("visit_" + timestamp, user_id, visit_time)) > 0) {
            return true;
        }
        return false;
    }

    //获取今日访问量
    @RequestMapping("/getTodayVisitNumber")
    public int getTodayVisitNumber() {
        List<Visit> visitList = getVisitList();
        int count = 0;
        for (Visit visit : visitList) {
            try {
                if (IfTodayUtils.isToday(visit.getVisit_time())) {
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    //获取总访问量
    @RequestMapping("/getTotalVisitNumber")
    public int getTotalVisitNumber() {
        return getVisitList().size();
    }

    //获取访问列表
    @RequestMapping("/getVisitList")
    public List<Visit> getVisitList() {
        return visitDao.getVisitList();
    }


}
