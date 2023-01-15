package edu.sustech.ooad.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import edu.sustech.ooad.cdn.VodClient;
import edu.sustech.ooad.cdn.VodConstant;
import edu.sustech.ooad.service.itf.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Autowired
    VodClient vodClient;

    @Override
    public String uploadVideo(MultipartFile file, String title) {
        String fileName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(VodConstant.ACCESS_KEY_ID, VodConstant.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String video_id = null;
            if (response.isSuccess()) {
                video_id = response.getVideoId();
            } else {
                //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                video_id = response.getVideoId();
            }
            return video_id;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadVideoOrg(File file, String title) {
        String fileName = file.getName();
        try {
            InputStream inputStream = new FileInputStream(file);
            UploadStreamRequest request = new UploadStreamRequest(VodConstant.ACCESS_KEY_ID, VodConstant.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String video_id = null;
            if (response.isSuccess()) {
                video_id = response.getVideoId();
            } else {
                //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                video_id = response.getVideoId();
            }
            return video_id;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GetPlayInfoResponse getPlayInfo(String video_id) {
        try {
            DefaultAcsClient client = vodClient.initVodClient(VodConstant.ACCESS_KEY_ID, VodConstant.ACCESS_KEY_SECRET);
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId(video_id);
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreVideo(List<String> videoList) {

    }
}
