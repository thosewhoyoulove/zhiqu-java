package web.Entry;

public class Visit {
    String visit_id;
    String user_id;
    String visit_time;

    public Visit(String visit_id, String user_id, String visit_time) {
        this.visit_id = visit_id;
        this.user_id = user_id;
        this.visit_time = visit_time;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(String visit_time) {
        this.visit_time = visit_time;
    }

    @Override
    public String toString() {
        return "visit{" +
                "visit_id='" + visit_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", time='" + visit_time + '\'' +
                '}';
    }
}
