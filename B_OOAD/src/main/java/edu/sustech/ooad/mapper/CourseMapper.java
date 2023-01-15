package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    List<Course> selectCoursesS();

    List<Course> selectCoursesByTeacherIdS(Integer teacher_id);

    List<Course> selectCoursesByTeacherIdOrderS(Integer teacher_id, String order);

    List<Course> selectCoursesInOrderS(String order);

    List<Course> selectCoursesLike(String text);

    List<Course> selectCourses();

    List<Course> selectCoursesByTeacherId(Integer teacher_id);

    List<Course> selectCoursesByTeacherIdOrder(Integer teacher_id, String order);

    List<Course> selectUncheckedCourses();

    Course selectCourseByCourseId(Integer course_id);

    Course selectCourseByTeacherIdAndCourseName(Integer teacher_id, String course_name);

    void insertCourse(Course course);

    void updateCourse(Course course);

    void updateCourseOg(String course_title, String course_description, String course_cover, Integer course_price, Integer course_id);

    void deleteCourse(Integer teacher_id, String course_title);

    void updateCourseStatus(String status, Integer course_id);

    void updateCourseScore(Integer course_score, Integer course_id);

    void updateCourseBought(Integer course_id);

    void updateLessonViewcount(String lesson_id);

    void updateCourseCommentNum(Integer course_id);


    List<Course> selectCoursesAlphaOrder();

    List<Course> selectCoursesBoughtOrder();
}
