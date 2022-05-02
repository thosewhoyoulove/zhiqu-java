package web.Controller;

import com.alibaba.fastjson.JSON;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import web.Dao.FileDao;
import web.Entry.Result;
import web.Utils.IfTodayUtils;
import web.Utils.ResultUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public Result getSpecificFileList(@RequestParam("file_subject") String file_subject, @RequestParam("level") int level, @RequestParam("file_type") String file_type) {
        List<web.Entry.File> fileList = fileDao.getSpecificFileList(file_subject, level, file_type);
        if (fileList.size() == 0){
            return ResultUtils.error(300, "文件列表为空");
        }
        return ResultUtils.data(fileList, "科目文件列表");
    }

    //获取单个文件
    @RequestMapping("/getSingleFile")
    public web.Entry.File getSingleFile(String file_id) {
        return fileDao.getSingleFile(file_id);
    }

    //获取今日更新量
    @RequestMapping("/getTodayFileUpdateNumber")
    public Result getTodayUpdateNumber() {
        List<web.Entry.File> fileList = fileDao.getFileList();
        int count = 0;
        for (web.Entry.File file : fileList) {
            if (IfTodayUtils.isToday(file.getUpload_time())) {
                count++;
            }
        }
        return ResultUtils.data(count, "今日更新量");
    }

    //获取总文件数
    @RequestMapping("/getTotalFileNumber")
    public Result getTotalFileNumber() {
        return ResultUtils.data(fileDao.getFileList().size(), "文件总数");
    }

    //搜索文件
    @RequestMapping("/searchFile")
    public Result searchFile(@RequestParam("key") String key) {
        List<web.Entry.File> res = new ArrayList<>();
        List<web.Entry.File> fileList = getFileList();
        for (web.Entry.File file : fileList) {
            //文件关键字匹配
            if (strMatching(file.getFile_topic(), key)) {
                res.add(file);
            }
        }
        if (res.size() == 0){
            return ResultUtils.error(300, "无对应文件");
        }
        return ResultUtils.data(res, "成功搜索文件");
    }

    //文件点赞+1
    @RequestMapping("/addFileLike")
    public Result addFileLike(@RequestParam("file_id") String file_id) {
        web.Entry.File singleFile = getSingleFile(file_id);
        if (singleFile != null){
            int like_count = singleFile.getLike_count();
            if (fileDao.addFileLike(file_id, like_count + 1) > 0) {
                return ResultUtils.success("操作成功");
            }
        }

        return ResultUtils.error(300, "操作失败");
    }

    //文件下载量+1
    @RequestMapping("/addFileDownload")
    public Result addFileDownload(@RequestParam("file_id") String file_id) {
        web.Entry.File singleFile = getSingleFile(file_id);
        if (singleFile != null){
            int download_count = singleFile.getLike_count();
            if (fileDao.addFileDownload(file_id, download_count + 1) > 0) {
                return ResultUtils.success("操作成功");
            }
        }

        return ResultUtils.error(300, "操作失败");
    }

    //文件点击量+1
    @RequestMapping("/addFileClick")
    public Result addFileClick(@RequestParam("file_id") String file_id) {
        web.Entry.File singleFile = getSingleFile(file_id);
        if (singleFile != null){
            int click_count = singleFile.getLike_count();
            if (fileDao.addFileClick(file_id, click_count + 1) > 0) {
                return ResultUtils.success("操作成功");
            }
        }

        return ResultUtils.error(300, "操作失败");
    }


    //上传文件
    @RequestMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("file_topic") String file_topic, @RequestParam("file_type") String file_type,
                         @RequestParam("ifCan_download") Boolean ifCan_download, @RequestParam("file_subject") String file_subject, @RequestParam("level") int level) {
        System.out.println("准备上传");
        if (file.isEmpty()) {
            return ResultUtils.error(400, "上传文件为空");
        }
        System.out.println(file_topic);
        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名.jpg
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(lastIndexOf);
        //时间戳
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String upload_time = new SimpleDateFormat("yyyy年MM月dd日HH:mm").format(new Date());
        //创建文件类 路径+文件名
        File dataFile = new File(absoluteImgPath + originalFilename);
        //文件URL
        String fileUrl = absoluteImgPath + originalFilename;
        web.Entry.File file_obj = new web.Entry.File("file_" + timestamp, file_topic, originalFilename, fileUrl, file_type, upload_time,
                0, 0, 0, ifCan_download, file_subject, level);

        //文件上传至指定路径
        try {
            //写入服务器
            file.transferTo(dataFile);
            //将文件加入数据库
            if (addFile(file_obj)) {
                return ResultUtils.success("文件上传成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.error(300, "文件上传失败");
    }


    //文件下载或预览
    @RequestMapping("download")
    public void download(@RequestParam("openStyle") String openStyle, @RequestParam("file_id") String file_id, HttpServletResponse response) throws IOException {
        //获取打开方式 attachment下载 inline在线打开
        openStyle = openStyle == null ? "attachment" : openStyle;
        //获取文件信息
        web.Entry.File file = fileDao.getFileByFileID(file_id);
        //点击下载链接更新下载次数
        if ("attachment".equals(openStyle)) {
            addFileDownload(file_id);
        }
        //根据文件信息中文件名字 和 文件存储路径获取文件输入流
        //String realpath = ResourceUtils.getURL("classpath:").getPath() + "/static" + file.getFile_url();
        //获取文件输入流 "C:\\Users\\Minsc"
        FileInputStream is = new FileInputStream(new File(absoluteImgPath, file.getFile_name()));
        //附件下载
        response.setHeader("content-disposition", openStyle + ";fileName=" + URLEncoder.encode(file.getFile_name(), "UTF-8"));
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    //文件删除
    @RequestMapping("delete")
    public Result delete(@RequestParam("file_id") String file_id) throws FileNotFoundException {

        //根据id查询信息
        web.Entry.File specificFile = getFileByFileID(file_id);
        //删除文件与数据库中记录
        //String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + specificFile.getFile_url();
        File file = new File(absoluteImgPath, specificFile.getFile_name());
        System.out.println(file.exists());
        if (file.exists() && deleteDBFile(file_id)) { //判断文件是否存在以及数据库是否删除数据
            file.delete();//立即删除
            return ResultUtils.success("删除成功");
        }

        return ResultUtils.error(300, "删除失败");
    }

    //删除数据库中一条文件记录
    @RequestMapping("deleteDBFile")
    public boolean deleteDBFile(String file_id) {
        if (fileDao.deleteDBFile(file_id)) {
            return true;
        }
        return false;
    }
    //通过id获取文件
    @RequestMapping("/getFileByFileID")
    public web.Entry.File getFileByFileID(String file_id){
        return fileDao.getFileByFileID(file_id);
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
