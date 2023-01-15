package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.QuizGrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuizGradeMapper {

    void insertQuizGrade(QuizGrade quizGrade);

    List<QuizGrade> selectQuizGradeByUserId(Integer user_id);

    List<QuizGrade> selectQuizGradeByCourseId(Integer course_id);

    Integer selectScoreByCourseIdAndQuizIndex(Integer course_id, Integer quiz_index);

}
