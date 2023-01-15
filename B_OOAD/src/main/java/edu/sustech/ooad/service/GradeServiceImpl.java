package edu.sustech.ooad.service;

import edu.sustech.ooad.entity.HomeworkGrade;
import edu.sustech.ooad.entity.QuizGrade;
import edu.sustech.ooad.entity.Submit;
import edu.sustech.ooad.entity.VideoGrade;
import edu.sustech.ooad.mapper.*;
import edu.sustech.ooad.service.itf.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    VideoGradeMapper videoGradeMapper;

    @Autowired
    HomeworkGradeMapper homeworkGradeMapper;

    @Autowired
    QuizGradeMapper quizGradeMapper;

    @Autowired
    ChapterMapper chapterMapper;
    
    @Autowired
    LessonMapper lessonMapper;

    @Autowired
    SubmitMapper submitMapper;

    @Override
    public Integer selectChapterGradeByChapterIndexCourseIdAndUserId(Integer chapter_index, Integer course_id, Integer user_id) {
        return videoGradeMapper.selectChapterGradeByChapterIndexCourseIdAndUserId(chapter_index, course_id, user_id);
    }

    @Override
    public Integer selectHomeworkGradeByCourseIdAndHomeworkIndex(Integer course_id, Integer homework_index) {
        return homeworkGradeMapper.selectScoreByCourseIdAndHomeworkIndex(course_id, homework_index);
    }

    @Override
    public Integer selectQuizGradeByCourseIdAndQuizIndex(Integer course_id, Integer quiz_index) {
        return quizGradeMapper.selectScoreByCourseIdAndQuizIndex(course_id, quiz_index);
    }

    @Override
    public List<VideoGrade> selectVideoGradesByCourseIdAndUserId(Integer course_id, Integer user_id) {
        return videoGradeMapper.selectVideoGradeByCourseIdAndUserId(course_id, user_id);
    }

    @Override
    public List<QuizGrade> selectQuizGradesByUserId(Integer user_id) {
        return quizGradeMapper.selectQuizGradeByUserId(user_id);
    }

    @Override
    public List<HomeworkGrade> selectHomeworkGradesByUserId(Integer user_id) {
        return homeworkGradeMapper.selectHomeworkGradeByUserId(user_id);
    }


    @Override
    public void insertHomeworkGrade(HomeworkGrade homeworkGrade) {
        homeworkGradeMapper.insertHomeworkGrade(homeworkGrade);
    }

    @Override
    public void insertVideoGrade(VideoGrade videoGrade) {
        videoGradeMapper.insertVideoGrade(videoGrade);
    }

    @Override
    public void insertQuizGrade(QuizGrade quizGrade) {
        quizGradeMapper.insertQuizGrade(quizGrade);
    }

    @Override
    public void insertSubmit(Submit submit) {
        submitMapper.insertSubmit(submit);
    }

    @Override
    public void updateHomeworkChecked(Integer student_id, Integer course_id, Integer submit_index) {
        submitMapper.updateSubmitChecked(student_id, course_id, submit_index);
    }
}
