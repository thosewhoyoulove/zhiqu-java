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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public List<web.Entry.File> getFileList(){
        return fileDao.getFileList();
    }

    //获取单个文件
    @RequestMapping("/getSingleFile")
    public web.Entry.File getSingleFile(String file_id){
        return fileDao.getSingleFile(file_id);
    }

    //获取今日更新量
    @RequestMapping("/getTodayFileUpdateNumber")
    public int getTodayUpdateNumber(){
        List<web.Entry.File> fileList = fileDao.getFileList();
        int count = 0;
        for (web.Entry.File file : fileList) {
            if (IfTodayUtils.isToday(file.getUpload_time())){
                count++;
            }
        }
        return count;
    }
    //获取总文件数
    @RequestMapping("/getTotalFileNumber")
    public int getTotalFileNumber(){
        return fileDao.getFileList().size();
    }

    //搜索文件
    @RequestMapping("/searchFile")
    public web.Entry.File searchFile(String file_id){
        return getSingleFile(file_id);
    }

    //文件点赞
    @RequestMapping("/addFileLike")
    public boolean addFileLike(String file_id){
        web.Entry.File singleFile = getSingleFile(file_id);
        int like_count = singleFile.getLike_count();
        if(fileDao.addFileLike(file_id, like_count + 1) > 0){
            return true;
        }
        return false;
    }







    //上传文件
    @RequestMapping("/upload")
    public String upload(HttpSession session, @RequestParam("file") MultipartFile file) {
        System.out.println("准备上传");
        if (file.isEmpty()) {
            return "上传的文件不能为空";
        }

        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();

        //获取文件后缀名.jpg
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(lastIndexOf);

        //获取新文件名称 命名：时间戳+UUID+后缀
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + UUID.randomUUID().toString().substring(0, 4)
                + extension;

        //创建文件类 路径+文件名
        File dataFile = new File(absoluteImgPath + newFileName);
        //文件URL
        String fileUrl = sonImgPath + newFileName;
        //文件上传至指定路径
        try {
            file.transferTo(dataFile);
            //将文件加入数据库
            if (addFileToDB()){
                return "上传成功";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    public boolean addFileToDB() {
        return false;
    }


}
