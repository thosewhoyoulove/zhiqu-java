package web.Controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.Dao.ForumDao;
import web.Entry.Forum;
import web.Entry.Remark;
import web.Entry.Result;
import web.Utils.DateCompareUtils;
import web.Utils.ResultUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
public class ForumController {
    @Autowired
    ForumDao forumDao;

    //获取所有话题
    @RequestMapping("/getAllTag")
    public Result getAllTag(@RequestParam("sort_way") String sort_way){
        List<Forum> forumList = forumDao.getAllTag();
        if (forumList.size() == 0){
            return ResultUtils.error(300, "列表为空");
        }
        if (sort_way.equals("sortByTime")) {                    //按时间排序
            Collections.sort(forumList, new Comparator<Forum>() {
                @Override
                public int compare(Forum o1, Forum o2) {

                    if (DateCompareUtils.compare(o1.getForum_time(), o2.getForum_time())){
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (sort_way.equals("sortByReplyCount")) {       //按回复数排序
            Collections.sort(forumList, new Comparator<Forum>() {
                @Override
                public int compare(Forum o1, Forum o2) {
                    if (o1.getForum_replyCount() > o2.getForum_replyCount()){
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
        return ResultUtils.data(forumList, "返回成功");
    }
    //添加一个话题
    @RequestMapping("/addTag")
    public Result addTag(@RequestParam("forum_topic") String forum_topic, @RequestParam("user_id") String user_id, @RequestParam("forum_tag") String forum_tag) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String forum_time = new SimpleDateFormat("yyyy年MM月dd日HH:mm").format(new Date());
        String forum_id = "forum_" + timestamp;
        Forum forum = new Forum(forum_id, forum_topic, user_id, forum_time, 0, 0, forum_tag);
        if (forumDao.addTag(forum) > 0) {
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error(300, "添加失败");
    }

    //添加一条评论
    @RequestMapping("/addForumRecord")
    public Result addRecord(@RequestParam("user_id") String user_id, @RequestParam("forum_id") String forum_id, @RequestParam("remark_content") String remark_content) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String remark_time = new SimpleDateFormat("yyyy年MM月dd日HH:mm").format(new Date());
        String remark_id = "remark_" + timestamp;
        Remark remark = new Remark(remark_id, user_id, forum_id, remark_content, remark_time);
        if (forumDao.addForumRecord(remark) > 0) {
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error(300, "添加失败");
    }

    //获取指定评论区对象
    @RequestMapping("/getSpecificForum")
    public Result getSpecificForum(@RequestParam("forum_id") String forum_id) {
        Forum forum = forumDao.getSpecificForum(forum_id);
        if (forum == null){
            return ResultUtils.error(300,"获取失败");
        }
        return ResultUtils.data(forum, "获取成功");
    }

    //获取指定评论区的所有评论
    @RequestMapping("/getSpecificForumAllRecord")
    public Result getSpecificForumAllRecord(@RequestParam("forum_id") String forum_id) {
        List<Remark> remarkList = forumDao.getSpecificForumAllRecord(forum_id);
        if (remarkList.size() == 0){
            return ResultUtils.error(300, "列表为空");
        }
        return ResultUtils.data(remarkList, "获取成功");
    }

    //删除一条论坛评论
    @RequestMapping("/deleteAForumRecord")
    public Result deleteAForumRecord(@RequestParam("remark_id") String remark_id) {
        if (forumDao.deleteAForumRecord(remark_id) > 0) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error(300, "删除失败");
    }
}
