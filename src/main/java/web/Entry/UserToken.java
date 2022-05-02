package web.Entry;

public class UserToken {

    private String user_id;

    private String username;
    private int status;
    private String class_name;

    public UserToken(String user_id, String username, int status, String class_name) {
        this.user_id = user_id;
        this.username = username;
        this.status = status;
        this.class_name = class_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}
