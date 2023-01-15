package edu.sustech.ooad.service;

import edu.sustech.ooad.entity.Comment;
import edu.sustech.ooad.entity.Homework;
import edu.sustech.ooad.entity.Notice;
import edu.sustech.ooad.entity.Quiz;
import edu.sustech.ooad.mapper.CommentMapper;
import edu.sustech.ooad.mapper.HomeworkMapper;
import edu.sustech.ooad.mapper.NoticeMapper;
import edu.sustech.ooad.mapper.QuizMapper;
import edu.sustech.ooad.service.itf.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessoryServiceImpl implements AccessoryService {

    @Autowired
    HomeworkMapper homeworkMapper;

    @Autowired
    QuizMapper quizMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Homework> selectHomeworkByCourseId(Integer course_id) {
        return homeworkMapper.selectHomeworkByCourseId(course_id);
    }

    @Override
    public Homework selectHomeworkByCourseIdAndHomeworkIndex(Integer course_id, Integer homework_index) {
        return homeworkMapper.selectHomeworkByCourseIdAndHomeworkIndex(course_id, homework_index);
    }

    @Override
    public Quiz selectQuizByCourseIdAndQuizIndex(Integer course_id, Integer quiz_index) {
        return quizMapper.selectQuizByCourseIdAndQuizIndex(course_id, quiz_index);
    }


    @Override
    public List<Quiz> selectQuizByCourseId(Integer course_id) {
        return quizMapper.selectQuizByCourseId(course_id);
    }

    @Override
    public List<Notice> selectNoticeByCourseId(Integer course_id) {
        return noticeMapper.selectNoticesByCourseId(course_id);
    }

    @Override
    public List<Comment> selectCommentsByCourseId(Integer course_id) {
        return commentMapper.selectCommentsByCourseId(course_id);
    }

    @Override
    public void insertNotice(Notice notice) {
        noticeMapper.insertNotice(notice);
    }

    @Override
    public void insertQuiz(Quiz quiz) {
        quizMapper.insertQuiz(quiz);
    }

    @Override
    public void insertHomework(Homework homework) {
        homeworkMapper.insertHomework(homework);
    }

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public void updateNotice(Notice notice) {
        noticeMapper.updateNotice(notice);
    }

    @Override
    public void updateQuiz(Quiz quiz) {
        quizMapper.updateQuiz(quiz);
    }

    @Override
    public void updateHomework(Homework homework) {
        homeworkMapper.updateHomework(homework);
    }

    @Override
    public Integer getCommentNum(Integer course_id) {
        List<Comment> commentList = commentMapper.selectCommentsByCourseId(course_id);
        return commentList.size();
    }
}
