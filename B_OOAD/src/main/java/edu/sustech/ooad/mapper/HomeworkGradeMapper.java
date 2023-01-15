package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.HomeworkGrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeworkGradeMapper {

    void insertHomeworkGrade(HomeworkGrade homeworkGrade);

    List<HomeworkGrade> selectHomeworkGradeByUserId(Integer user_id);

    List<HomeworkGrade> selectHomeworkGradeByCourseId(Integer course_id);

    Integer selectScoreByCourseIdAndHomeworkIndex(Integer course_id, Integer homework_index);


}
