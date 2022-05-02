package web.Entry;

public class Classes {
    String class_id;
    String class_teacher;
    String class_name;

    public Classes(String class_id, String class_teacher, String class_name) {
        this.class_id = class_id;
        this.class_teacher = class_teacher;
        this.class_name = class_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_teacher() {
        return class_teacher;
    }

    public void setClass_teacher(String class_teacher) {
        this.class_teacher = class_teacher;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "class_id='" + class_id + '\'' +
                ", class_teacher='" + class_teacher + '\'' +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}
