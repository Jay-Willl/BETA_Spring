package edu.sustech.ooad.service;

import edu.sustech.ooad.email.EmailClient;
import edu.sustech.ooad.entity.Course;
import edu.sustech.ooad.entity.Enroll;
import edu.sustech.ooad.mapper.CourseMapper;
import edu.sustech.ooad.mapper.EnrollMapper;
import edu.sustech.ooad.mapper.UserMapper;
import edu.sustech.ooad.service.itf.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollServiceImpl implements EnrollService {

    @Autowired
    EnrollMapper enrollMapper;

    @Autowired
    EmailClient emailClient;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Integer> selectUserIdsByCourseId(Integer course_id) {
        return enrollMapper.selectUsersByCourseId(course_id);
    }


    @Override
    public List<Course> selectCoursesByUserId(Integer user_id) {
        List<Course> res = new ArrayList<>();
        List<Integer> courseIdList = enrollMapper.selectCoursesByUserId(user_id);
        for (Integer i : courseIdList) {
            res.add(courseMapper.selectCourseByCourseId(i));
        }
        return res;
    }

    @Override
    public void insertEnroll(Enroll enroll) {
        enrollMapper.insertEnroll(enroll);
    }


    @Override
    public void sendEmails(List<String> emails, String content) {
        for (String email : emails) {
            try {
                emailClient.sendEmail(email, content);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
