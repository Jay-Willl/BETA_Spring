package edu.sustech.ooad.service.itf;

import edu.sustech.ooad.entity.Comment;
import edu.sustech.ooad.entity.Homework;
import edu.sustech.ooad.entity.Notice;
import edu.sustech.ooad.entity.Quiz;

import java.util.List;

public interface AccessoryService {

    List<Homework> selectHomeworkByCourseId(Integer course_id);

    Homework selectHomeworkByCourseIdAndHomeworkIndex(Integer course_id, Integer homework_index);

    Quiz selectQuizByCourseIdAndQuizIndex(Integer course_id, Integer quiz_index);

    List<Quiz> selectQuizByCourseId(Integer course_id);

    List<Notice> selectNoticeByCourseId(Integer course_id);

    List<Comment> selectCommentsByCourseId(Integer course_id);

    void insertNotice(Notice notice);

    void insertQuiz(Quiz quiz);

    void insertHomework(Homework homework);

    void insertComment(Comment comment);

    void updateNotice(Notice notice);

    void updateQuiz(Quiz quiz);

    void updateHomework(Homework homework);

    Integer getCommentNum(Integer course_id);
}
