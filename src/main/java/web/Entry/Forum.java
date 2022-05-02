package web.Entry;

public class Forum {
    String forum_id;
    String user_id;
    String forum_topic;
    String forum_time;
    int forum_visitCount;
    int forum_replyCount;
    String forum_tag;

    public Forum(String forum_id, String user_id, String forum_topic, String forum_time, int forum_visitCount, int forum_replyCount, String forum_tag) {
        this.forum_id = forum_id;
        this.user_id = user_id;
        this.forum_topic = forum_topic;
        this.forum_time = forum_time;
        this.forum_visitCount = forum_visitCount;
        this.forum_replyCount = forum_replyCount;
        this.forum_tag = forum_tag;
    }

    public String getForum_id() {
        return forum_id;
    }

    public void setForum_id(String forum_id) {
        this.forum_id = forum_id;
    }

    public String getForum_topic() {
        return forum_topic;
    }

    public void setForum_topic(String forum_topic) {
        this.forum_topic = forum_topic;
    }

    public String getForum_time() {
        return forum_time;
    }

    public void setForum_time(String forum_time) {
        this.forum_time = forum_time;
    }

    public int getForum_visitCount() {
        return forum_visitCount;
    }

    public void setForum_visitCount(int forum_visitCount) {
        this.forum_visitCount = forum_visitCount;
    }

    public int getForum_replyCount() {
        return forum_replyCount;
    }

    public void setForum_replyCount(int forum_replyCount) {
        this.forum_replyCount = forum_replyCount;
    }

    public String getForum_tag() {
        return forum_tag;
    }

    public void setForum_tag(String forum_tag) {
        this.forum_tag = forum_tag;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "forum_id='" + forum_id + '\'' +
                ", forum_topic='" + forum_topic + '\'' +
                ", forum_time='" + forum_time + '\'' +
                ", forum_visitCount=" + forum_visitCount +
                ", forum_replyCount=" + forum_replyCount +
                ", forum_tag='" + forum_tag + '\'' +
                '}';
    }
}
