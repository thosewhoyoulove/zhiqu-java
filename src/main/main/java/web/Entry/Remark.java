package web.Entry;

public class Remark {
    String remark_id;
    String user_id;
    String file_id;
    String remark_content;
    String remark_time;

    public Remark(String remark_id, String user_id, String file_id, String remark_content, String remark_time) {
        this.remark_id = remark_id;
        this.user_id = user_id;
        this.file_id = file_id;
        this.remark_content = remark_content;
        this.remark_time = remark_time;
    }

    public String getRemark_id() {
        return remark_id;
    }

    public void setRemark_id(String remark_id) {
        this.remark_id = remark_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getRemark_content() {
        return remark_content;
    }

    public void setRemark_content(String remark_content) {
        this.remark_content = remark_content;
    }

    public String getRemark_time() {
        return remark_time;
    }

    public void setRemark_time(String remark_time) {
        this.remark_time = remark_time;
    }

    @Override
    public String toString() {
        return "Remark{" +
                "remark_id='" + remark_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", file_id='" + file_id + '\'' +
                ", remark_content='" + remark_content + '\'' +
                ", remark_time='" + remark_time + '\'' +
                '}';
    }
}
