package edu.sustech.ooad.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import edu.sustech.ooad.cdn.OssConstant;
import edu.sustech.ooad.entity.Pic;
import edu.sustech.ooad.service.CdnServiceImpl;
import edu.sustech.ooad.service.CourseServiceImpl;
import edu.sustech.ooad.service.VodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Controller
public class UploadController {

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    VodServiceImpl vodServiceImpl;

    @Autowired
    CdnServiceImpl cdnServiceImpl;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/upload/file")
    @ResponseBody
    public Integer createCover(@RequestPart MultipartFile file) {
        String endpoint = OssConstant.END_POINT;
        String keyId = OssConstant.ACCESS_KEY_ID;
        String keySecret = OssConstant.ACCESS_KEY_SECRET;
        String bucketName = OssConstant.BUCKET_NAME;
        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);

            InputStream inputStream = file.getInputStream();

            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileName = uuid + fileName;
            String basePath = new Timestamp(new Date(System.currentTimeMillis()).getTime()).toString().substring(0, 10);
            fileName = basePath + "/" + fileName;
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();

            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            Pic pic = new Pic();
            pic.setPic_url(url);
            cdnServiceImpl.insertPic(pic);
            Pic tempPic = cdnServiceImpl.selectPicByUrl(url);

            return tempPic.getPic_id();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/obtain/{pic_id}")
    @ResponseBody
    public String obtainFileUrl(@PathVariable("pic_id") Integer pic_id) {
        return cdnServiceImpl.selectPicById(pic_id).getPic_url();
    }


    @RequestMapping("/teacher/create/video")
    @ResponseBody
    public String createVideo(@RequestPart MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String title = file.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
        String video_id = vodServiceImpl.uploadVideo(file, title);

        courseServiceImpl.insertLessonId(video_id);
        return video_id;
    }

}
