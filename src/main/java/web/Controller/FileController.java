package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.Dao.FileDao;
import web.Dao.UserDao;
import web.Utils.IfTodayUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
public class FileController {
    @Autowired
    FileDao fileDao;
    @Value("${absoluteImgPath}")
    String absoluteImgPath;
    @Value("${sonImgPath}")
    String sonImgPath;


    //获取文件列表
    @RequestMapping("/getFileList")
    public List<web.Entry.File> getFileList() {
        return fileDao.getFileList();
    }

    //获取对应科目文件列表
    @RequestMapping("/getSpecificFileList")
    public List<web.Entry.File> getSpecificFileList(@RequestParam("file_subject") String file_subject, @RequestParam("level") int level) {
        return fileDao.getSpecificFileList(file_subject, level);
    }

    //获取单个文件
    @RequestMapping("/getSingleFile")
    public web.Entry.File getSingleFile(String file_id) {
        return fileDao.getSingleFile(file_id);
    }

    //获取今日更新量
    @RequestMapping("/getTodayFileUpdateNumber")
    public int getTodayUpdateNumber() {
        List<web.Entry.File> fileList = fileDao.getFileList();
        int count = 0;
        for (web.Entry.File file : fileList) {
            if (IfTodayUtils.isToday(file.getUpload_time())) {
                count++;
            }
        }
        return count;
    }

    //获取总文件数
    @RequestMapping("/getTotalFileNumber")
    public int getTotalFileNumber() {
        return fileDao.getFileList().size();
    }

    //搜索文件
    @RequestMapping("/searchFile")
    public List<web.Entry.File> searchFile(@RequestParam("key") String key) {
        List<web.Entry.File> res = new ArrayList<>();
        List<web.Entry.File> fileList = getFileList();
        for (web.Entry.File file : fileList) {
            //文件关键字匹配
            if (strMatching(file.getFile_topic(), key)) {
                res.add(file);
            }
        }
        return res;
    }

    //文件点赞+1
    @RequestMapping("/addFileLike")
    public boolean addFileLike(@RequestParam("file_id") String file_id) {
        web.Entry.File singleFile = getSingleFile(file_id);
        int like_count = singleFile.getLike_count();
        if (fileDao.addFileLike(file_id, like_count + 1) > 0) {
            return true;
        }
        return false;
    }

    //文件下载量+1
    @RequestMapping("/addFileDownload")
    public boolean addFileDownload(@RequestParam("file_id") String file_id) {
        web.Entry.File singleFile = getSingleFile(file_id);
        int download_count = singleFile.getLike_count();
        if (fileDao.addFileDownload(file_id, download_count + 1) > 0) {
            return true;
        }
        return false;
    }

    //文件点击量+1
    @RequestMapping("/addFileClick")
    public boolean addFileClick(@RequestParam("file_id") String file_id) {
        web.Entry.File singleFile = getSingleFile(file_id);
        int click_count = singleFile.getLike_count();
        if (fileDao.addFileClick(file_id, click_count + 1) > 0) {
            return true;
        }
        return false;
    }


    //上传文件
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("file_topic") String file_topic, @RequestParam("file_type") String file_type,
                         @RequestParam("ifCan_download") Boolean ifCan_download, @RequestParam("file_subject") String file_subject, @RequestParam("level") int level) {
        System.out.println("准备上传");
        if (file.isEmpty()) {
            return "上传的文件不能为空";
        }

        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();

        //获取文件后缀名.jpg
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(lastIndexOf);
        //时间戳
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String upload_time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        //获取新文件名称 命名：时间戳+UUID+后缀
        String newFileName = timestamp + UUID.randomUUID().toString().substring(0, 4) + extension;

        //创建文件类 路径+文件名
        File dataFile = new File(absoluteImgPath + newFileName);
        //文件URL
        String fileUrl = sonImgPath + newFileName;
        web.Entry.File file_obj = new web.Entry.File("file_" + timestamp, file_topic, newFileName, fileUrl, file_type, upload_time,
                0, 0, 0, ifCan_download, file_subject, level);
        //文件上传至指定路径
        try {
            //写入服务器
            file.transferTo(dataFile);
            //将文件加入数据库
            if (addFile(file_obj)) {
                return "上传成功";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    //文件添加到数据库
    @RequestMapping("/addFile")
    public boolean addFile(web.Entry.File file_obj) {
        if (fileDao.addFile(file_obj) > 0) {
            return true;
        }
        return false;
    }

    //字符串关键字匹配
    public boolean strMatching(String str, String key) {
        StringBuilder regexp = new StringBuilder();
        regexp.append("(?=.*").append(key).append(")");
        Pattern pattern = Pattern.compile(regexp.toString());
        return pattern.matcher(str).find();

    }


}
