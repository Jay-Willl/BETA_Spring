package edu.sustech.ooad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.ooad.auth.JwtAuth;
import edu.sustech.ooad.entity.*;
import edu.sustech.ooad.mapper.UserMapper;
import edu.sustech.ooad.service.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO:
 *  1. course title 与 teacher_id 作为联合主键
 *  2. chapter index 与 course_id 作为联合主键
 *  3. lesson index 与 chapter_id 作为联合主键
 */

@RequestMapping("/teacher")
@Controller
public class TeacherController {

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
     *                   1. name: teacher name
     *                   2. token: jwt token
     * @return json string
     * success: return result json string
     * fail: 1. "*no access"
     */
    @PostMapping("/course/all")
    @ResponseBody
    public String getAllTeacherCourse(@RequestBody String requestStr) {
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
        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                List<Course> courseList = courseServiceImpl.selectCoursesByTeacherId(user.getUser_id());
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
     *                   1. name: teacher name
     *                   2. token: jwt token
     * @return json string
     * success: return result json string
     * fail: 1. "*no access"
     */
    @PostMapping("/course/pass")
    @ResponseBody
    public String getPassedTeacherCourse(@RequestBody String requestStr) {
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
        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                List<Course> courseList = courseServiceImpl.selectCoursesByTeacherIdOrder(user.getUser_id(), "course_title");
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
     *                   1. name: teacher name
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
        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                List<Course> courseList = courseServiceImpl.selectCoursesByTeacherIdOrder(user.getUser_id(), "course_title");
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
     * @param requestStr 1. username: teacher name
     *                   2. course: course name
     *                   3. token: jwt token
     * @return json string
     * [
     * {
     * "index": 1,
     * "title": "ch1-intro",
     * "score": 30
     * },
     * {
     * "index": 2,
     * "title": "ch2-xx",
     * "score": 40
     * }
     * ]
     * success: return result json string
     * fail: 1. "*no access"
     * @ c
     */
    @PostMapping("/chapter")
    @ResponseBody
    public String getTeacherChapter(@RequestBody String requestStr) {
        // get needed info
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String course = jsonNode.get("course").asText();
        String token = jsonNode.get("token").asText();
        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                List<Chapter> chapterList = courseServiceImpl.selectChaptersByCourseId(tempCourse.getCourse_id());
                ObjectMapper mapper = new ObjectMapper();
                String res = null;
                try {
                    res = mapper.writeValueAsString(chapterList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr 1. name: teacher name
     *                   2. course: course name
     *                   3. token: jwt token
     * @return json string
     * success: return result json string
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/lesson")
    @ResponseBody
    public String getTeacherLesson(@RequestBody String requestStr) {
        // get needed info
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String course = jsonNode.get("course").asText();
        String token = jsonNode.get("token").asText();
        User tempUser = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(tempUser.getUser_id(), course);
        Token tempToken = loginServiceImpl.getTokenIdByMap(tempUser.getUser_id());
        List<Chapter> chapterList = courseServiceImpl.selectChaptersByCourseId(tempCourse.getCourse_id());

        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                List<ChapterCluster> clusterList = new ArrayList<>();
                for (Chapter ch : chapterList) {
                    List<Lesson> lessonList = courseServiceImpl.selectLessonsByChapterId(ch.getChapter_id());
                    ChapterCluster cluster = new ChapterCluster();
                    cluster.setIndex(ch.getChapter_index());
                    cluster.setLessonList(lessonList);
                    clusterList.add(cluster);
                }
                ObjectMapper mapper = new ObjectMapper();
                try {
                    res = mapper.writeValueAsString(clusterList);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr 1. name: teacher name
     *                   2. course: course name
     *                   3. token: jwt token
     * @return json string
     * success: return result json string
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/homework")
    @ResponseBody
    public String getTeacherHomework(@RequestBody String requestStr) {
        // get needed info
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String course = jsonNode.get("course").asText();
        String token = jsonNode.get("token").asText();
        User tempUser = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(tempUser.getUser_id(), course);
        List<Homework> homeworkList = accessoryServiceImpl.selectHomeworkByCourseId(tempCourse.getCourse_id());
        Token tempToken = loginServiceImpl.getTokenIdByMap(tempUser.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                ObjectMapper mapper = new ObjectMapper();
                String res = null;
                try {
                    res = mapper.writeValueAsString(homeworkList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr 1. name: teacher name
     *                   2. course: course name
     *                   3. token: jwt token
     * @return json string
     * success: return result json string
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/quiz")
    @ResponseBody
    public String getTeacherQuiz(@RequestBody String requestStr) {
        // get needed info
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String course = jsonNode.get("course").asText();
        String token = jsonNode.get("token").asText();
        User tempUser = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(tempUser.getUser_id(), course);
        List<Quiz> quizList = accessoryServiceImpl.selectQuizByCourseId(tempCourse.getCourse_id());
        Token tempToken = loginServiceImpl.getTokenIdByMap(tempUser.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                ObjectMapper mapper = new ObjectMapper();
                String res = null;
                try {
                    res = mapper.writeValueAsString(quizList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr 1. name: teacher name
     *                   2. course: course name
     *                   3. token: jwt token
     * @return json string
     * success: return result json string
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/notice")
    @ResponseBody
    public String getTeacherNotice(@RequestBody String requestStr) {
        // get needed info
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String course = jsonNode.get("course").asText();
        String token = jsonNode.get("token").asText();
        User tempUser = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(tempUser.getUser_id(), course);
        Token tempToken = loginServiceImpl.getTokenIdByMap(tempUser.getUser_id());
        // process
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                List<Notice> noticeList = accessoryServiceImpl.selectNoticeByCourseId(tempCourse.getCourse_id());
                ObjectMapper mapper = new ObjectMapper();
                String res = null;
                try {
                    res = mapper.writeValueAsString(noticeList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   token: "xxx",
     *                   data: {
     *                   title : string,
     *                   cover : string,
     *                   teacher : string,
     *                   price : number,
     *                   description : string,
     *                   status : string  || checking || fail || success || delete,
     *                   field : string,
     *                   tag : string
     *                   }
     *                   }
     * @return string
     * success: ok
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/create/course")
    @ResponseBody
    public String getTeacherInsertCourse(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = null;
        JsonNode node2 = null;
        try {
            node1 = objectMapper.readTree(requestStr);
            node2 = node1.get("data");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = node1.get("username").asText();
        String token = node1.get("token").asText();

        String course_title = node2.get("title").asText();
        Integer course_cover = node2.get("cover").asInt();
        String course_teacher = node2.get("teacher").asText();
        Integer course_price = node2.get("price").asInt();
        String course_description = node2.get("description").asText();
        String course_status = node2.get("status").asText();
        String course_field = node2.get("field").asText();
        String course_tag = node2.get("tag").asText();

        User user = userMapper.selectUserByUserNameAndRole(course_teacher, "teacher");
        Course orgCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course_title);
        Course tempCourse = new Course();

        String cover_url = cdnServiceImpl.selectPicById(course_cover).getPic_url();
        tempCourse.setCourse_title(course_title);
        tempCourse.setCourse_cover(cover_url);
        tempCourse.setCourse_price(course_price);
        tempCourse.setCourse_description(course_description);
        tempCourse.setCourse_status(course_status);
        tempCourse.setTeacher_id(user.getUser_id());
        tempCourse.setCourse_field(course_field);
        tempCourse.setCourse_tag(course_tag);
        tempCourse.setCourse_bought(0);
        tempCourse.setCourse_comment_num(0);
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                if (orgCourse == null) {
                    courseServiceImpl.insertCourse(tempCourse);
                }
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   token: "xxxx",
     *                   data: {
     *                   title: "xxx",
     *                   cover: "xxx",
     *                   teacher : "xxx",
     *                   price : 20,
     *                   description : "xxx",
     *                   status : "xxx", //checking || fail || success || delete
     *                   }
     *                   }
     * @return string
     * success: ok
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/update/course")
    @ResponseBody
    public String getTeacherUpdateCourse(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = null;
        JsonNode node2 = null;
        try {
            node1 = objectMapper.readTree(requestStr);
            node2 = node1.get("data");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = node1.get("username").asText();
        String token = node1.get("token").asText();

        String course_title = node2.get("title").asText();
        Integer course_cover = node2.get("cover").asInt();
        String course_teacher = node2.get("teacher").asText();
        Integer course_price = node2.get("price").asInt();
        String course_description = node2.get("description").asText();
        String course_status = node2.get("status").asText();

        String cover_url = cdnServiceImpl.selectPicById(course_cover).getPic_url();

        User user = userMapper.selectUserByUserNameAndRole(course_teacher, "teacher");
        Course orgCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course_title);

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                if (orgCourse != null) {
                    courseServiceImpl.updateCourseOg(course_title, course_description, cover_url, course_price, orgCourse.getCourse_id());
                    return "ok";
                } else {
                    return "no such course";
                }
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr 1. name: teacher name
     *                   2. course: course name
     *                   3. token: jwt token
     * @return json string
     * success: ok
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/delete/course")
    @ResponseBody
    public String getTeacherDeleteCourse(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                courseServiceImpl.deleteCourseByCourseId(tempCourse.getCourse_id());
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course : string,
     *                   token: "xxx",
     *                   data: {
     *                   index : number,
     *                   title : string
     *                   }
     *                   }
     * @return string
     * success: ok
     * fail: 1. no access
     * 2. duplicate index
     * @ c
     */
    @PostMapping("/create/chapter")
    @ResponseBody
    public String getTeacherInsertChapter(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = null;
        JsonNode node2 = null;
        try {
            node1 = objectMapper.readTree(requestStr);
            node2 = node1.get("data");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // get needed info
        String name = node1.get("username").asText();
        String course = node1.get("course").asText();
        String token = node1.get("token").asText();
        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Integer chapter_index = node2.get("index").asInt();
        String chapter_title = node2.get("title").asText();
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        List<Chapter> chapterList = courseServiceImpl.selectChaptersByCourseId(tempCourse.getCourse_id());

        Chapter tempChapter = new Chapter();
        tempChapter.setChapter_index(chapter_index);
        tempChapter.setChapter_title(chapter_title);
        tempChapter.setCourse_id(tempCourse.getCourse_id());

        // process
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                // duplicate index
                for (Chapter chapter : chapterList) {
                    if (Objects.equals(chapter.getChapter_index(), chapter_index)) {
                        return "duplicate index";
                    }
                }
                courseServiceImpl.insertChapter(tempChapter);
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx",
     *                   chapter: "xxx"
     *                   }
     * @return string
     * success: ok
     * fail: 1. no access
     * @ c
     */
    @PostMapping("/delete/chapter")
    @ResponseBody
    public String getTeacherDeleteChapter(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();
        Integer index = node.get("chapter").asInt();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Chapter tempChapter = courseServiceImpl.selectChapterByCourseIdAndChapterIndex(tempCourse.getCourse_id(), index);

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                courseServiceImpl.deleteChapterByCourseIdAndChapterIndex(tempCourse.getCourse_id(), tempChapter.getChapter_index());
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }

    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   chapter: "2",
     *                   token: "xxx",
     *                   data: {
     *                   index : 1
     *                   title : "intro",
     *                   description : "intro",
     *                   id : "***",
     *                   score : 100
     *                   }
     *                   }
     * @return string
     * 1. no access
     * 2. duplicate index
     * @ c
     */
    @PostMapping("/create/lesson")
    @ResponseBody
    public String getTeacherInsertLesson(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = null;
        JsonNode node2 = null;
        try {
            node1 = objectMapper.readTree(requestStr);
            node2 = node1.get("data");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // get needed info
        String user_name = node1.get("username").asText();
        String course_name = node1.get("course").asText();
        Integer chapter_id = node1.get("chapter").asInt();
        String token = node1.get("token").asText();
        User user = userMapper.selectUserByUserNameAndRole(user_name, "teacher");
        Chapter tempChapter = courseServiceImpl.selectChapterByChapterId(chapter_id);
        List<Lesson> lessonList = courseServiceImpl.selectLessonsByChapterId(tempChapter.getChapter_id());

        Integer lesson_index = node2.get("index").asInt();
        String lesson_title = node2.get("title").asText();
        String lesson_description = node2.get("description").asText();
        Integer lesson_score = node2.get("score").asInt();
        String video_id = node2.get("id").asText();

        // process
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                // duplicate index
                for (Lesson tempLesson : lessonList) {
                    if (Objects.equals(tempLesson.getLesson_index(), lesson_index)) {
                        return "duplicate index";
                    }
                }
                courseServiceImpl.updateLessonAll(video_id, lesson_index, lesson_title, lesson_description, lesson_score, chapter_id);
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   chapter: "xxx",
     *                   lesson: "xxx",
     *                   token: "xxx",
     *                   chapter: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/delete/lesson")
    @ResponseBody
    public String getTeacherDeleteLesson(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        Integer chapter_index = node.get("chapter").asInt();
        Integer lesson_index = node.get("lesson").asInt();
        String token = node.get("token").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Chapter tempChapter = courseServiceImpl.selectChapterByCourseIdAndChapterIndex(tempCourse.getCourse_id(), chapter_index);
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                courseServiceImpl.deleteLessonByLessonIndex(tempChapter.getChapter_id(), lesson_index);
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   useEmail: "true",
     *                   token: "xxx",
     *                   data: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/create/notice")
    @ResponseBody
    public String getTeacherInsertNotice(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        boolean email = node.get("useEmail").asBoolean();
        String token = node.get("token").asText();
        String data = node.get("data").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Notice notice = new Notice();
        notice.setCourse_id(tempCourse.getCourse_id());
        notice.setUser_id(user.getUser_id());
        notice.setNotice_content(data);
        notice.setNotice_time(new Timestamp(new Date().getTime()));
        notice.setNotice_email(email);

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                accessoryServiceImpl.insertNotice(notice);
                if (email) {
                    List<Integer> idList = enrollServiceImpl.selectUserIdsByCourseId(tempCourse.getCourse_id());
                    List<String> emails = new ArrayList<>();
                    for (Integer id : idList) {
                        User temp = userMapper.selectUserByUserId(id);
                        emails.add(temp.getUser_email());
                    }
                    enrollServiceImpl.sendEmails(emails, data);
                }
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   useEmail: "true",
     *                   token: "xxx",
     *                   data: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/update/notice")
    @ResponseBody
    public String getTeacherUpdateNotice(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        boolean email = node.get("useEmail").asBoolean();
        String token = node.get("token").asText();
        String data = node.get("data").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Notice notice = new Notice();
        notice.setCourse_id(tempCourse.getCourse_id());
        notice.setUser_id(user.getUser_id());
        notice.setNotice_content(data);
        notice.setNotice_time(new Timestamp(new Date().getTime()));
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());

        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                accessoryServiceImpl.updateNotice(notice);
                if (email) {
                    List<Integer> idList = enrollServiceImpl.selectUserIdsByCourseId(tempCourse.getCourse_id());
                    List<String> emails = new ArrayList<>();
                    for (Integer id : idList) {
                        User temp = userMapper.selectUserByUserId(id);
                        emails.add(temp.getUser_email());
                    }
                    enrollServiceImpl.sendEmails(emails, data);
                }
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx",
     *                   data: "xxx",
     *                   index: 1
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/create/quiz")
    @ResponseBody
    public String getTeacherInsertQuiz(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();
        String data = node.get("data").asText();
        Integer index = node.get("index").asInt();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Quiz quiz = new Quiz();
        quiz.setCourse_id(tempCourse.getCourse_id());
        quiz.setQuiz_content(data);
        quiz.setQuiz_index(index);
        quiz.setQuiz_time(new Timestamp(new Date().getTime()));

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                accessoryServiceImpl.insertQuiz(quiz);
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }

    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx",
     *                   data: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/update/quiz")
    @ResponseBody
    public String getTeacherUpdateQuiz(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();
        String data = node.get("data").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Quiz quiz = new Quiz();
        quiz.setCourse_id(tempCourse.getCourse_id());
        quiz.setQuiz_content(data);

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());

        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                accessoryServiceImpl.updateQuiz(quiz);
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx",
     *                   data: "xxx",
     *                   index: 1
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/create/homework")
    @ResponseBody
    public String getTeacherInsertHomework(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();
        String data = node.get("data").asText();
        Integer index = node.get("index").asInt();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Homework homework = new Homework();
        homework.setHomework_content(data);
        homework.setCourse_id(tempCourse.getCourse_id());
        homework.setHomework_index(index);
        homework.setHomework_time(new Timestamp(System.currentTimeMillis()));

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                accessoryServiceImpl.insertHomework(homework);
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx",
     *                   data: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/update/homework")
    @ResponseBody
    public String getTeacherUpdateHomework(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();
        String data = node.get("data").asText();
        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Homework homework = new Homework();
        homework.setHomework_content(data);
        homework.setCourse_id(tempCourse.getCourse_id());
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                accessoryServiceImpl.updateHomework(homework);
                return "ok";
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx"
     *                   }
     * @return string
     * 1. no access
     */
    @PostMapping("/grade/chapter")
    @ResponseBody
    public String getChapterGrade(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        List<Chapter> chapterList = courseServiceImpl.selectChaptersByCourseId(tempCourse.getCourse_id());
        chapterList = chapterList.stream().sorted(new Comparator<Chapter>() {
            @Override
            public int compare(Chapter o1, Chapter o2) {
                return o1.getChapter_index() - o2.getChapter_index();
            }
        }).collect(Collectors.toList());
        List<Integer> userIdList = enrollServiceImpl.selectUserIdsByCourseId(tempCourse.getCourse_id());
        List<User> userList = new ArrayList<>();
        List<ReturnGrade> gradeList = new ArrayList<>();
        for (Integer i : userIdList) {
            userList.add(userMapper.selectUserByUserId(i));
        }
        // select from video_grade aggregate chapter
        for (User tempUser : userList) {
            List<ChapterGradeDetail> data = new ArrayList<>();
            for (Chapter tempChapter : chapterList) {
                if (gradeServiceImpl.selectChapterGradeByChapterIndexCourseIdAndUserId(
                        tempChapter.getChapter_index(), tempChapter.getCourse_id(), tempUser.getUser_id()) == null) {
                    data.add(new ChapterGradeDetail(tempChapter.getChapter_index(), 0));
                } else {
                    data.add(
                            new ChapterGradeDetail(tempChapter.getChapter_index(),
                                    gradeServiceImpl.selectChapterGradeByChapterIndexCourseIdAndUserId(
                                            tempChapter.getChapter_id(), tempChapter.getCourse_id(), tempUser.getUser_id()
                                    )
                            ));
                }
            }
            try {
                ReturnGrade returnGrade = new ReturnGrade();
                returnGrade.setName(tempUser.getUser_name());
                returnGrade.setGrade(objectMapper.writeValueAsString(data));
                gradeList.add(returnGrade);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(gradeList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }

    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/grade/homework")
    @ResponseBody
    public String getHomeworkGrade(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);

        List<Homework> homeworkList = accessoryServiceImpl.selectHomeworkByCourseId(tempCourse.getCourse_id());
        List<Integer> userIdList = enrollServiceImpl.selectUserIdsByCourseId(tempCourse.getCourse_id());
        List<User> userList = new ArrayList<>();
        List<ReturnGrade> gradeList = new ArrayList<>();
        for (Integer i : userIdList) {
            userList.add(userMapper.selectUserByUserId(i));
        }

        for (User tempUser : userList) {
            ReturnGrade returnGrade = new ReturnGrade();
            returnGrade.setName(tempUser.getUser_name());
            Map<Integer, Integer> res = new LinkedHashMap<>();
            for (Homework homework : homeworkList) {
                res.put(homework.getHomework_index(), gradeServiceImpl.selectHomeworkGradeByCourseIdAndHomeworkIndex(
                        tempCourse.getCourse_id(), homework.getHomework_index()
                ));
            }
            try {
                returnGrade.setGrade(objectMapper.writeValueAsString(res));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            gradeList.add(returnGrade);
        }

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(gradeList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   token: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/grade/quiz")
    @ResponseBody
    public String getQuizGrade(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        String token = node.get("token").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);

        List<Quiz> quizList = accessoryServiceImpl.selectQuizByCourseId(tempCourse.getCourse_id());
        List<Integer> userIdList = enrollServiceImpl.selectUserIdsByCourseId(tempCourse.getCourse_id());
        List<User> userList = new ArrayList<>();
        List<ReturnGrade> gradeList = new ArrayList<>();
        for (Integer i : userIdList) {
            userList.add(userMapper.selectUserByUserId(i));
        }

        for (User tempUser : userList) {
            ReturnGrade returnGrade = new ReturnGrade();
            returnGrade.setName(tempUser.getUser_name());
            Map<Integer, Integer> res = new LinkedHashMap<>();
            for (Quiz quiz : quizList) {
                res.put(quiz.getQuiz_index(), gradeServiceImpl.selectQuizGradeByCourseIdAndQuizIndex(
                        tempCourse.getCourse_id(), quiz.getQuiz_index()
                ));
            }
            try {
                returnGrade.setGrade(objectMapper.writeValueAsString(res));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            gradeList.add(returnGrade);
        }

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(gradeList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "no access";
            }
        } else {  // no access
            return "no access";
        }
    }


    /**
     * @param requestStr {
     *                   username: "xxx",
     *                   course: "xxx",
     *                   index: 1,
     *                   token: "xxx"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/check/homework")
    @ResponseBody
    public String getCheckHomework(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String name = node.get("username").asText();
        String course = node.get("course").asText();
        Integer index = node.get("index").asInt();
        String token = node.get("token").asText();

        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(user.getUser_id(), course);
        Homework tempHomework = accessoryServiceImpl.selectHomeworkByCourseIdAndHomeworkIndex(tempCourse.getCourse_id(), index);

        List<Submit> submitList = cdnServiceImpl.selectSubmitsByCourseIdAndSubmitIndex(tempCourse.getCourse_id(), index);

        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(submitList);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
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
     *                   course: "xxx",
     *                   index: 1,
     *                   token: "xxx",
     *                   name: "stu1",
     *                   token: "xxx",
     *                   score: 20(max100),
     *                   commit: "dsdasdsadas"
     *                   }
     * @return string
     * 1. no access
     * @ c
     */
    @PostMapping("/review/homework")
    @ResponseBody
    public String getReviewHomework(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String teacher_name = node.get("username").asText();
        String course = node.get("course").asText();
        Integer index = node.get("index").asInt();
        String student_name = node.get("name").asText();
        String token = node.get("token").asText();
        Integer score = node.get("score").asInt();
        String commit = node.get("commit").asText();


        User teacher = userMapper.selectUserByUserNameAndRole(teacher_name, "teacher");
        User student = userMapper.selectUserByUserNameAndRole(student_name, "student");
        Course tempCourse = courseServiceImpl.selectCourseByTeacherIdAndCourseName(teacher.getUser_id(), course);
        HomeworkGrade homeworkGrade = new HomeworkGrade();
        homeworkGrade.setCourse_id(tempCourse.getCourse_id());
        homeworkGrade.setHomework_index(index);
        homeworkGrade.setGrade_content(commit);
        homeworkGrade.setGrade_score(score);
        homeworkGrade.setGrade_time(new Timestamp(System.currentTimeMillis()));
        homeworkGrade.setUser_id(student.getUser_id());

        Token tempToken = loginServiceImpl.getTokenIdByMap(teacher.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                gradeServiceImpl.insertHomeworkGrade(homeworkGrade);
                gradeServiceImpl.updateHomeworkChecked(student.getUser_id(), tempCourse.getCourse_id(), index);
                return "ok";
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }
}
