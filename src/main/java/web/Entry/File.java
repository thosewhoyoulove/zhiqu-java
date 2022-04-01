package web.Entry;

public class File {
    String file_id;
    String file_topic;
    String file_name;
    String file_url;
    String file_type;
    String upload_time;
    int like_count;
    int download_count;
    int click_count;
    boolean ifCan_download;

    public File(String file_id, String file_topic, String file_name, String file_url, String file_type, String upload_time, int like_count, int download_count, int click_count, boolean ifCan_download) {
        this.file_id = file_id;
        this.file_topic = file_topic;
        this.file_name = file_name;
        this.file_url = file_url;
        this.file_type = file_type;
        this.upload_time = upload_time;
        this.like_count = like_count;
        this.download_count = download_count;
        this.click_count = click_count;
        this.ifCan_download = ifCan_download;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_topic() {
        return file_topic;
    }

    public void setFile_topic(String file_topic) {
        this.file_topic = file_topic;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public int getClick_count() {
        return click_count;
    }

    public void setClick_count(int click_count) {
        this.click_count = click_count;
    }

    public boolean isIfCan_download() {
        return ifCan_download;
    }

    public void setIfCan_download(boolean ifCan_download) {
        this.ifCan_download = ifCan_download;
    }

    @Override
    public String toString() {
        return "File{" +
                "file_id='" + file_id + '\'' +
                ", file_topic='" + file_topic + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_url='" + file_url + '\'' +
                ", file_type='" + file_type + '\'' +
                ", upload_time='" + upload_time + '\'' +
                ", like_count=" + like_count +
                ", download_count=" + download_count +
                ", click_count=" + click_count +
                ", ifCan_download=" + ifCan_download +
                '}';
    }
}
