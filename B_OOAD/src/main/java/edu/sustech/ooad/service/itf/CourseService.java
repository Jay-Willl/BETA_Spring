package edu.sustech.ooad.service.itf;

import edu.sustech.ooad.entity.Chapter;
import edu.sustech.ooad.entity.Course;
import edu.sustech.ooad.entity.Lesson;

import java.util.List;

public interface CourseService {

    List<Course> selectCourses();

    List<Course> selectCoursesByTeacherId(Integer teacher_id);

    List<Course> selectCoursesByTeacherIdOrder(Integer teacher_id, String order);

    List<Course> selectUncheckedCourses();

    List<Chapter> selectChaptersByCourseId(Integer course_id);

    List<Lesson> selectLessonsByChapterId(Integer chapter_id);

    Course selectCourseByCourseId(Integer course_id);

    Course selectCourseByTeacherIdAndCourseName(Integer teacher_id, String course_name);

    Chapter selectChapterByChapterId(Integer chapter_id);

    Chapter selectChapterByCourseIdAndChapterName(Integer course_id, String chapter_name);

    Chapter selectChapterByCourseIdAndChapterIndex(Integer course_id, Integer chapter_index);

    Lesson selectLessonByLessonId(String lesson_id);

    void insertCourse(Course course);

    void insertChapter(Chapter chapter);

    void insertLesson(Lesson lesson);

    void insertLessonId(String lesson_id);

    void updateCourse(Course course);

    void updateCourseOg(String course_title, String course_description, String course_cover, Integer course_price, Integer course_id);

    void updateCourseBought(Integer course_id);

    void updateChapter(Chapter chapter);

    void updateLesson(Lesson lesson);

    void updateLessonAll(String lesson_id, Integer lesson_index, String lesson_title, String lesson_description, Integer lesson_score, Integer chapter_id);

    void updateLessonOg(String lesson_id, String lesson_title, String lesson_description, Integer lesson_score);

    void addLessonViewcountByLessonId(Integer lesson_id);

    void updateCourseStatus(String status, Integer course_id);

    void updateCourseScore(Integer course_score, Integer course_id);

    void updateLessonViewcount(String lesson_id);

    void updateCourseCommentNum(Integer course_id);

    void deleteCourseByCourseId(Integer course_id);

    void deleteChapterByCourseIdAndChapterIndex(Integer course_id, Integer chapter_index);

    void deleteLessonByLessonIndex(Integer chapter_id, Integer lesson_index);

    ////////

    List<Course> selectCoursesS();

    List<Course> selectCoursesByTeacherIdS(Integer teacher_id);

    List<Course> selectCoursesByTeacherIdOrderS(Integer teacher_id, String order);

    List<Course> selectCoursesInOrderS(String order);

    List<Course> selectCoursesLike(String text);


    List<Course> selectCoursesAlphaOrder();

    List<Course> selectCoursesBoughtOrder();
}
