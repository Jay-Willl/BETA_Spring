package edu.sustech.ooad.service.itf;

import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file, String title);

    String uploadVideoOrg(File file, String title);

    GetPlayInfoResponse getPlayInfo(String video_id);

    void removeMoreVideo(List<String> videoList);
}
