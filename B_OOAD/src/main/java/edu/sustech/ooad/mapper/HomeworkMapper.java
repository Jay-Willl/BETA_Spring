package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Homework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeworkMapper {

    List<Homework> selectHomeworkByCourseId(Integer course_id);

    Homework selectHomeworkByCourseIdAndHomeworkIndex(Integer course_id, Integer homework_index);

    void insertHomework(Homework homework);

    void updateHomework(Homework homework);
}
