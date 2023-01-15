package edu.sustech.ooad.service.itf;

import edu.sustech.ooad.entity.Course;
import edu.sustech.ooad.entity.Enroll;

import java.util.List;

public interface EnrollService {
    List<Integer> selectUserIdsByCourseId(Integer course_id);

    void sendEmails(List<String> emails, String content);

    List<Course> selectCoursesByUserId(Integer user_id);

    void insertEnroll(Enroll enroll);
}
