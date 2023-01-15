package edu.sustech.ooad.service;

import edu.sustech.ooad.entity.Chapter;
import edu.sustech.ooad.entity.Course;
import edu.sustech.ooad.entity.Lesson;
import edu.sustech.ooad.mapper.ChapterMapper;
import edu.sustech.ooad.mapper.CourseMapper;
import edu.sustech.ooad.mapper.LessonMapper;
import edu.sustech.ooad.service.itf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    LessonMapper lessonMapper;


    // select course / chapter / lesson
    @Override
    public List<Course> selectCourses(){
        return courseMapper.selectCourses();
    }

    @Override
    public List<Course> selectCoursesByTeacherId(Integer teacher_id) {
        return courseMapper.selectCoursesByTeacherId(teacher_id);
    }

    @Override
    public Course selectCourseByCourseId(Integer course_id) {
        return courseMapper.selectCourseByCourseId(course_id);
    }

    // only return success courses, can order by title, price
    @Override
    public List<Course> selectCoursesByTeacherIdOrder(Integer teacher_id, String order) {
        return courseMapper.selectCoursesByTeacherIdOrder(teacher_id, order);
    }

    @Override
    public List<Course> selectUncheckedCourses() {
        return courseMapper.selectUncheckedCourses();
    }

    @Override
    public List<Chapter> selectChaptersByCourseId(Integer course_id) {
        return chapterMapper.selectChaptersByCourseId(course_id);
    }

    @Override
    public List<Lesson> selectLessonsByChapterId(Integer chapter_id) {
        return lessonMapper.selectLessonsByChapterId(chapter_id);
    }

    @Override
    public Course selectCourseByTeacherIdAndCourseName(Integer teacher_id, String course_name) {
        return courseMapper.selectCourseByTeacherIdAndCourseName(teacher_id, course_name);
    }


    @Override
    public Chapter selectChapterByChapterId(Integer chapter_id) {
        return chapterMapper.selectChapterByChapterId(chapter_id);
    }

    @Override
    public Chapter selectChapterByCourseIdAndChapterName(Integer course_id, String chapter_name) {
        return chapterMapper.selectChapterByCourseIdAndChapterName(course_id, chapter_name);
    }

    @Override
    public Chapter selectChapterByCourseIdAndChapterIndex(Integer course_id, Integer chapter_index) {
        return chapterMapper.selectChapterByCourseIdAndChapterIndex(course_id, chapter_index);
    }


    @Override
    public Lesson selectLessonByLessonId(String lesson_id) {
        return lessonMapper.selectLessonByLessonId(lesson_id);
    }



    // insert
    @Override
    public void insertCourse(Course course) {
        courseMapper.insertCourse(course);
    }

    @Override
    public void insertChapter(Chapter chapter) {
        chapterMapper.insertChapter(chapter);
    }

    @Override
    public void insertLesson(Lesson lesson) {
        lessonMapper.insertLesson(lesson);
    }

    @Override
    public void insertLessonId(String lesson_id) {
        lessonMapper.insertLessonId(lesson_id);
    }


    // update
    // update course title / price / description / price / status
    @Override
    public void updateCourse(Course course) {
        courseMapper.updateCourse(course);
    }

    @Override
    public void updateCourseOg(String course_title, String course_description, String course_cover, Integer course_price, Integer course_id) {
        courseMapper.updateCourseOg(course_title, course_description, course_cover, course_price, course_id);
    }

    @Override
    public void updateCourseBought(Integer course_id) {
        courseMapper.updateCourseBought(course_id);
    }

    // update chapter title
    @Override
    public void updateChapter(Chapter chapter) {
        chapterMapper.updateChapter(chapter);
    }

    // update lesson title / description / score
    @Override
    public void updateLesson(Lesson lesson) {
        lessonMapper.updateLesson(lesson);
    }

    @Override
    public void updateLessonAll(String lesson_id, Integer lesson_index, String lesson_title, String lesson_description, Integer lesson_score, Integer chapter_id) {
        lessonMapper.updateLessonAll(lesson_id, lesson_index, lesson_title, lesson_description, lesson_score, chapter_id);
    }

    @Override
    public void updateLessonOg(String lesson_id, String lesson_title, String lesson_description, Integer lesson_score) {
        lessonMapper.updateLessonOg(lesson_id, lesson_title, lesson_description, lesson_score);
    }

    @Override
    public void updateLessonViewcount(String lesson_id) {
        courseMapper.updateLessonViewcount(lesson_id);
    }


    @Override
    public void updateCourseCommentNum(Integer course_id) {
        courseMapper.updateCourseCommentNum(course_id);
    }

    @Override
    public void addLessonViewcountByLessonId(Integer lesson_id) {
        lessonMapper.addLessonViewcountByLessonId(lesson_id);
    }

    @Override
    public void updateCourseStatus(String status, Integer course_id) {
        courseMapper.updateCourseStatus(status, course_id);
    }



    @Override
    public void updateCourseScore(Integer course_score, Integer course_id) {
        courseMapper.updateCourseScore(course_score, course_id);
    }

    // delete
    @Override
    public void deleteCourseByCourseId(Integer course_id) {
        courseMapper.updateCourseStatus("delete", course_id);
    }

    @Override
    public void deleteChapterByCourseIdAndChapterIndex(Integer course_id, Integer chapter_index) {
        chapterMapper.deleteChapterByCourseIdAndChapterIndex(course_id, chapter_index);
    }

    @Override
    public void deleteLessonByLessonIndex(Integer chapter_id, Integer lesson_index) {

    }



    @Override
    public List<Course> selectCoursesS() {
        return courseMapper.selectCoursesS();
    }

    @Override
    public List<Course> selectCoursesByTeacherIdS(Integer teacher_id) {
        return courseMapper.selectCoursesByTeacherIdS(teacher_id);
    }

    @Override
    public List<Course> selectCoursesByTeacherIdOrderS(Integer teacher_id, String order) {
        return courseMapper.selectCoursesByTeacherIdOrderS(teacher_id, order);
    }

    @Override
    public List<Course> selectCoursesInOrderS(String order) {
        return courseMapper.selectCoursesInOrderS(order);
    }

    @Override
    public List<Course> selectCoursesLike(String text) {
        return courseMapper.selectCoursesLike(text);
    }


    @Override
    public List<Course> selectCoursesAlphaOrder() {
        return courseMapper.selectCoursesAlphaOrder();
    }


    @Override
    public List<Course> selectCoursesBoughtOrder() {
        return courseMapper.selectCoursesBoughtOrder();
    }

}
