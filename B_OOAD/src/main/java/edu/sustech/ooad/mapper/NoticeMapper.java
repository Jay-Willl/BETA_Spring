package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<Notice> selectNoticesByUserId(Integer user_id);

    List<Notice> selectNoticesByCourseId(Integer course_id);

    void insertNotice(Notice notice);

    void updateNotice(Notice notice);
}
