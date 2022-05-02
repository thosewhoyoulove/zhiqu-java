package web.Controller;


import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.Dao.ClassesDao;
import web.Entry.Classes;
import web.Entry.Result;
import web.Utils.ResultUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
public class ClassesController {
    @Autowired
    ClassesDao classesDao;

    //查询所有班级信息
    @RequestMapping("/getAllClass")
    public String getAllClass() {
        return JSON.toJSONString(classesDao.getAllClass());
    }

    //增加一个班级
    @RequestMapping("/addClass")
    public Result addClass(@RequestParam("class_teacher") String class_teacher, @RequestParam("class_name") String class_name) {
        //判断班级是否存在
        if (ifExistClass(class_name)) {
            return ResultUtils.error(300, "班级不存在");
        }
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Classes classes = new Classes("class_" + timestamp, class_teacher, class_name);
        if (classesDao.addClass(classes) > 0) {
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error(400, "添加失败");
    }


    //通过班级老师名查询班级信息
    @RequestMapping("/getClassByTeacherName")
    public Result getClassByTeacherName(@RequestParam("class_teacher") String class_teacher) {
        List<Classes> classesList = classesDao.getClassByTeacherName(class_teacher);
        if (classesList.size() == 0) {
            ResultUtils.error(300, "无相关信息");
        }
        return ResultUtils.data(classesList, "查询成功");
    }

    //通过班级名查看班级
    @RequestMapping("/getClassByClassName")
    public Result getClassByClassName(@RequestParam("class_name") String class_name) {
        Classes classes = classesDao.getClassByClassName(class_name);
        if (classes == null) {
            ResultUtils.error(300, "该班级不存在");
        }
        return ResultUtils.data(classes, "查询成功");
    }

    //更新班级信息
    @RequestMapping("/updateClassInfo")
    public Result updateClassInfo(@Param("class_id") String class_id, @Param("class_teacher") String class_teacher, @Param("class_name") String class_name) {
        if (classesDao.updateClassInfo(new Classes(class_id, class_teacher, class_name)) > 0) {
            return ResultUtils.success("更新成功");
        }
        return ResultUtils.error(300, "更新成功");
    }


    //删除班级
    @RequestMapping("/deleteClass")
    public Result deleteClass(@Param("class_id") String class_id) {
        if (classesDao.deleteClass(class_id) > 0) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error(300, "删除失败");
    }

    //判断班级是否存在
    public boolean ifExistClass(String class_name) {
        if (classesDao.ifExistClass(class_name) != null) {
            return true;
        }
        return false;
    }


}
