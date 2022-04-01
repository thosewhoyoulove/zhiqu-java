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

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class FileController {
    @Autowired
    FileDao fileDao;
    @Value("${absoluteImgPath}")
    String absoluteImgPath;
    @Value("${sonImgPath}")
    String sonImgPath;

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
