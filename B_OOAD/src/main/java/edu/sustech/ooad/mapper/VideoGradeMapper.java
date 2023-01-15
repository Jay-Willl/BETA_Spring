package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.VideoGrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoGradeMapper {


    void insertVideoGrade(VideoGrade videoGrade);

    List<VideoGrade> selectVideoGradeByCourseIdAndUserId(Integer course_id, Integer user_id);

    Integer selectChapterGradeByChapterIndexCourseIdAndUserId(Integer chapter_index, Integer course_id, Integer user_id);


}
