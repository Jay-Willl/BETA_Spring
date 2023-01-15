package edu.sustech.ooad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.ooad.auth.JwtAuth;
import edu.sustech.ooad.entity.Course;
import edu.sustech.ooad.entity.Token;
import edu.sustech.ooad.entity.User;
import edu.sustech.ooad.mapper.UserMapper;
import edu.sustech.ooad.service.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginServiceImpl loginServiceImpl;

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    AccessoryServiceImpl accessoryServiceImpl;

    @Autowired
    VodServiceImpl vodServiceImpl;

    @Autowired
    CdnServiceImpl cdnServiceImpl;

    @Autowired
    EnrollServiceImpl enrollServiceImpl;

    @Autowired
    GradeServiceImpl gradeServiceImpl;


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   token: "xxx"
     *                   }
     *                   1. admin: teacher name
     *                   2. token: jwt token
     * @return json string
     * success: return result json string
     * fail: 1. "*no access"
     */
    @PostMapping("/course")
    @ResponseBody
    public String getTeacherCourse(@RequestBody String requestStr) {
        // get needed info
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String name = jsonNode.get("username").asText();
        String token = jsonNode.get("token").asText();
        User user = userMapper.selectUserByUserNameAndRole(name, "admin");
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                List<Course> courseList = courseServiceImpl.selectUncheckedCourses();
                ObjectMapper mapper = new ObjectMapper();
                String res = null;
                try {
                    res = mapper.writeValueAsString(courseList);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return res;
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }



    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   token: "xxx"
     *                   }
     *                   1. admin: teacher name
     *                   2. token: jwt token
     * @return json string
     * success: return ok
     * fail: 1. "*no access"
     */
    @PostMapping("/manage/course")
    @ResponseBody
    public String updateCourse(@RequestBody String requestStr) {
        // get needed info
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String name = node.get("username").asText();
        String token = node.get("token").asText();
        Integer course = node.get("course").asInt();
        String status = node.get("status").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "admin");

        Course tempCourse = courseServiceImpl.selectCourseByCourseId(course);
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                courseServiceImpl.updateCourseStatus(status, tempCourse.getCourse_id());
                return "ok";
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }
}
