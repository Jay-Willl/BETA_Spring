package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Quiz;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuizMapper {

    List<Quiz> selectQuizByCourseId(Integer course_id);

    Quiz selectQuizByCourseIdAndQuizIndex(Integer course_id, Integer quiz_index);

    void insertQuiz(Quiz quiz);

    void updateQuiz(Quiz quiz);

}
