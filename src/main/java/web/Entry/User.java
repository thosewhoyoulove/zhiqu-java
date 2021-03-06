package web.Entry;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
    String user_id;
    String username;
    String password;
    int status;
    String class_name;

    public User(String user_id, String username, String password, int status, String class_name) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}
