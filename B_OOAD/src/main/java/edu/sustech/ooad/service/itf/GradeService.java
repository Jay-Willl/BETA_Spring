package edu.sustech.ooad.service.itf;


import edu.sustech.ooad.entity.HomeworkGrade;
import edu.sustech.ooad.entity.QuizGrade;
import edu.sustech.ooad.entity.Submit;
import edu.sustech.ooad.entity.VideoGrade;

import java.util.List;

public interface GradeService {

    Integer selectChapterGradeByChapterIndexCourseIdAndUserId(Integer chapter_index, Integer course_id, Integer user_id);

    Integer selectHomeworkGradeByCourseIdAndHomeworkIndex(Integer course_id, Integer homework_index);

    Integer selectQuizGradeByCourseIdAndQuizIndex(Integer course_id, Integer quiz_index);

    List<VideoGrade> selectVideoGradesByCourseIdAndUserId(Integer course_id, Integer user_id);

    List<QuizGrade> selectQuizGradesByUserId(Integer user_id);

    List<HomeworkGrade> selectHomeworkGradesByUserId(Integer user_id);

    void insertHomeworkGrade(HomeworkGrade homeworkGrade);

    void insertVideoGrade(VideoGrade videoGrade);

    void insertQuizGrade(QuizGrade quizGrade);

    void insertSubmit(Submit submit);

    void updateHomeworkChecked(Integer student_id, Integer course_id, Integer submit_index);
}
