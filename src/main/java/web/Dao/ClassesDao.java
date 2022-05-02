package web.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import web.Entry.Classes;

import java.util.List;


@Repository
public interface ClassesDao {

    public List<Classes> getAllClass();
    public int addClass(Classes classes);
    public Classes ifExistClass(String class_name);
    public Classes getClassByClassName(@Param("class_name") String class_name);
    public List<Classes> getClassByTeacherName(@Param("class_teacher") String class_teacher);
    public int updateClassInfo(Classes classes);
    public int deleteClass(@Param("class_id") String class_id);

}
