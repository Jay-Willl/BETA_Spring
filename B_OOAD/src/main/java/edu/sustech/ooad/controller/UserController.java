package edu.sustech.ooad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.ooad.auth.JwtAuth;
import edu.sustech.ooad.entity.*;
import edu.sustech.ooad.mapper.SubmitMapper;
import edu.sustech.ooad.mapper.UserMapper;
import edu.sustech.ooad.service.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SubmitMapper submitMapper;

    @Autowired
    LoginServiceImpl loginServiceImpl;

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    EnrollServiceImpl enrollServiceImpl;

    @Autowired
    GradeServiceImpl gradeServiceImpl;

    @Autowired
    VodServiceImpl vodServiceImpl;

    @Autowired
    CdnServiceImpl cdnServiceImpl;

    @Autowired
    AccessoryServiceImpl accessoryServiceImpl;



    /**
     * 2 - 1
     * 随机选择5门课程用于首页展示
     * @return json string
     */
    @GetMapping("/banner")
    @ResponseBody
    public String getBanner() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Course> res = new ArrayList<>();
        List<Course> courseList = courseServiceImpl.selectCoursesS();
        Collections.shuffle(courseList);
        if (courseList.size() <= 5) {
            res.addAll(courseList);
        } else {
            for (int i = 0; i < 5; i++) {
                res.add(courseList.get(i));
            }
        }
        String result = null;
        try {
            result = objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 2 - 2
     * 随机选择8门课程用于首页展示
     * get all courses
     * @return json string
     */
    @GetMapping("/welcomePage")
    @ResponseBody
    public String getCourses() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Course> res = new ArrayList<>();
        List<Course> courseList = courseServiceImpl.selectCoursesS();
        Collections.shuffle(courseList);
        if (courseList.size() <= 8) {
            res.addAll(courseList);
        } else {
            for (int i = 0; i < 8; i++) {
                res.add(courseList.get(i));
            }
        }
        String result = null;
        try {
            result = objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 3 - 1
     * 获取所有课程
     * valid strategy: 1. (alphabetical)
     *                 2. (sold)
     * @param strategy
     * @return
     */
    @GetMapping("/course/{page}/{strategy}")
    @ResponseBody
    public String getCoursesByPage(
            @PathVariable("page") Integer page,
            @PathVariable("strategy") String strategy){
        ObjectMapper objectMapper = new ObjectMapper();
        final Integer PAGE_SIZE = 8;
        String res = null;
        List<Course> courses = null;
        if (Objects.equals(strategy, "alphabetical")) {
            courses = courseServiceImpl.selectCoursesAlphaOrder();
        } else if (Objects.equals(strategy, "sold")) {
            courses = courseServiceImpl.selectCoursesBoughtOrder();
        }
        int index = 0;
        List<Course> courseList = new ArrayList<>();
        for (Course course : courses) {
            index ++;
            if (index > (page - 1) * 8 && index <= page * 8) {
                courseList.add(course);
            }
        }
        try {
            res = objectMapper.writeValueAsString(courseList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    /**
     * 3 - 2
     * 搜索所有课程
     * valid strategy: 1. (alphabetical)
     *                 2. (sold)
     * @param text
     * @return
     */
    @GetMapping("/course/search/{page}/{text}")
    @ResponseBody
    public String searchCoursesByPage(
            @PathVariable("page") Integer page,
            @PathVariable("text") String text){
        ObjectMapper objectMapper = new ObjectMapper();
        String target = "%" + text + "%";
        List<Course> courses = courseServiceImpl.selectCoursesLike(target);
        final Integer PAGE_SIZE = 8;
        String res = null;
        int index = 0;
        List<Course> courseList = new ArrayList<>();
        for (Course course : courses) {
            index ++;
            if (index > (page - 1) * 8 && index <= page * 8) {
                courseList.add(course);
            }
        }
        try {
            res = objectMapper.writeValueAsString(courseList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return res;
    }





    /**
     * 4 - 1
     * 获取指定课程所有信息
     * @param course_id
     * @return json string
     */
    @GetMapping("/course/{course_id}")
    @ResponseBody
    public String getCourseDetail(@PathVariable("course_id") Integer course_id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Course tempCourse = courseServiceImpl.selectCourseByCourseId(course_id);

        List<Chapter> chapterList = courseServiceImpl.selectChaptersByCourseId(course_id);
        List<ChapterClusterDetail> list = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            List<Lesson> lessonList = courseServiceImpl.selectLessonsByChapterId(chapter.getChapter_id());
            ChapterClusterDetail chapterClusterDetail = new ChapterClusterDetail();
            chapterClusterDetail.setChapter_id(chapter.getChapter_id());
            chapterClusterDetail.setChapter_index(chapter.getChapter_index());
            chapterClusterDetail.setChapter_title(chapter.getChapter_title());
            chapterClusterDetail.setLessonList(lessonList);
            list.add(chapterClusterDetail);
        }
        String res = null;
        try {
            String chapter_detail = objectMapper.writeValueAsString(list);
            CourseClusterDetail courseClusterDetail = new CourseClusterDetail();
            courseClusterDetail.setCourse_id(tempCourse.getCourse_id());
            courseClusterDetail.setCourse_title(tempCourse.getCourse_title());
            courseClusterDetail.setCourse_cover(tempCourse.getCourse_cover());
            courseClusterDetail.setCourse_price(tempCourse.getCourse_price());
            courseClusterDetail.setCourse_description(tempCourse.getCourse_description());
            courseClusterDetail.setCourse_score(0);
            courseClusterDetail.setTeacher_id(tempCourse.getTeacher_id());
            courseClusterDetail.setTeacher_name(userMapper.selectUserByUserId(tempCourse.getTeacher_id()).getUser_name());
            courseClusterDetail.setChapter_detail(chapter_detail);
            courseClusterDetail.setCourse_bought(tempCourse.getCourse_bought());
            courseClusterDetail.setCourse_comment_num(tempCourse.getCourse_comment_num());
            res = objectMapper.writeValueAsString(courseClusterDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return res;
    }


    /**
     * 4 - 2
     * 获取指定课程的所有评论
     * @param course_id
     * @return json string
     */
    @GetMapping("/comment/{course_id}")
    @ResponseBody
    public String getCourseComment(@PathVariable("course_id") Integer course_id) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Comment> commentList = accessoryServiceImpl.selectCommentsByCourseId(course_id);
        String res = null;
        try {
            res = objectMapper.writeValueAsString(commentList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    /**
     * 4 - 3
     * 提交课程评论
     * @param requestStr
     * @param token
     * @return 1. "ok"
     *         2. "*no access"
     */
    @PostMapping("/comment/add")
    @ResponseBody
    public String addCourseComment(@RequestBody String requestStr,
                                   @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = null;
        try {
            node1 = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Integer user_id = node1.get("user_id").asInt();
        Integer course_id = node1.get("course_id").asInt();
        String comment_content = node1.get("comment_content").asText();

        Comment comment = new Comment();
        comment.setUser_id(user_id);
        comment.setCourse_id(course_id);
        comment.setComment_content(comment_content);
        comment.setComment_time(new Timestamp(System.currentTimeMillis()));

        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                accessoryServiceImpl.insertComment(comment);
                courseServiceImpl.updateCourseCommentNum(course_id);
                return "ok";
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }


    /**
     * 4 - 4
     * 购买指定课程
     * @param course_id
     * @param user_id
     * @param token
     * @return 1. "ok"
     *         2. "*no access"
     */
    @GetMapping("/buy/{course_id}/{user_id}")
    @ResponseBody
    public String buyCourse(@PathVariable("course_id") Integer course_id,
                            @PathVariable("user_id") Integer user_id,
                            @RequestHeader("token") String token){
        Course tempCourse = courseServiceImpl.selectCourseByCourseId(course_id);
        User tempUser = userMapper.selectUserByUserId(user_id);
        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                userMapper.updateUserGold(tempCourse.getCourse_price(), tempUser.getUser_id());
                courseServiceImpl.updateCourseBought(tempCourse.getCourse_id());
                Enroll enroll = new Enroll();
                enroll.setCourse_id(tempCourse.getCourse_id());
                enroll.setUser_id(tempUser.getUser_id());
                enroll.setEnroll_gold(tempCourse.getCourse_price());
                enroll.setEnroll_time(new Timestamp(System.currentTimeMillis()));
                enroll.setTeacher_id(tempCourse.getTeacher_id());
                enrollServiceImpl.insertEnroll(enroll);
                return "ok";
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }


    /**
     * 4 - 5
     * 检查课程是否购买
     * @param course_id
     * @param user_id
     * @param token
     * @return
     */
    @GetMapping("/check/{course_id}/{user_id}")
    @ResponseBody
    public String isBought(@PathVariable("course_id") Integer course_id,
                           @PathVariable("user_id") Integer user_id,
                           @RequestHeader("token") String token) {
        List<Course> courseList = enrollServiceImpl.selectCoursesByUserId(user_id);
        for (Course course : courseList) {
            if (Objects.equals(course_id, course.getCourse_id())) {
                return "ok";
            }
        }
        return "fail";
    }


    /**
     * 5 - 1
     * 获取视频播放所有信息
     * @param lesson_id
     * @return json string
     */
    @GetMapping("/video/{lesson_id}")
    @ResponseBody
    public String getVideo(@PathVariable("lesson_id") String lesson_id) {
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(vodServiceImpl.getPlayInfo(lesson_id));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    /**
     * 5 - 2
     * 获取用户的course的完成进度
     * @param course_id
     * @param user_id
     * @return json array
     */
    @GetMapping("/course/progress/{course_id}/{user_id}")
    @ResponseBody
    public String getLessonProgress(@PathVariable("course_id") Integer course_id,
                                    @PathVariable("user_id") Integer user_id,
                                    @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<LessonIndexes> indexesList = new ArrayList<>();
        List<VideoGrade> videoGradeList = gradeServiceImpl.selectVideoGradesByCourseIdAndUserId(course_id, user_id);
        for (VideoGrade videoGrade : videoGradeList) {
            indexesList.add(new LessonIndexes(videoGrade.getChapter_index(), videoGrade.getLesson_index()));
        }
        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(indexesList);
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
     * 5 - 3
     * 提交VideoGrade记录
     * @param lesson_id
     * @param user_id
     * @param token
     * @return 1. ok
     *         2. *no access
     */
    @GetMapping("/lesson/update/{lesson_id}/{user_id}")
    @ResponseBody
    public String userUpdateLesson(@PathVariable("lesson_id") String lesson_id,
                                   @PathVariable("user_id") Integer user_id,
                                   @RequestHeader("token") String token) {
        Lesson tempLesson = courseServiceImpl.selectLessonByLessonId(lesson_id);
        Chapter tempChapter = courseServiceImpl.selectChapterByChapterId(tempLesson.getChapter_id());
        Course tempCourse = courseServiceImpl.selectCourseByCourseId(tempChapter.getCourse_id());
        VideoGrade videoGrade = new VideoGrade();
        videoGrade.setUser_id(user_id);
        videoGrade.setCourse_id(tempCourse.getCourse_id());
        videoGrade.setChapter_index(tempChapter.getChapter_index());
        videoGrade.setLesson_index(tempLesson.getLesson_index());
        videoGrade.setGrade_score(tempLesson.getLesson_score());
        videoGrade.setGrade_time(new Timestamp(System.currentTimeMillis()));
        courseServiceImpl.updateLessonViewcount(lesson_id);

        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                gradeServiceImpl.insertVideoGrade(videoGrade);
                return "ok";
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }


    /**
     * 6 - 1
     * 获取指定学生基本信息
     * @param user_id
     * @param token
     * @return json string
     */
    @GetMapping("/info/{user_id}")
    @ResponseBody
    public String getUserInfo(@PathVariable("user_id") Integer user_id,
                              @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        User tempUser = userMapper.selectUserByUserId(user_id);
        List<Course> courseList = enrollServiceImpl.selectCoursesByUserId(user_id);
        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUser(tempUser);
                userInfo.setCourseList(courseList);
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(userInfo);
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
     * 7 - 0
     * 获取指定课程的所有quiz
     * @param course_id
     * @return json string
     */
    @GetMapping("/quiz/{course_id}")
    @ResponseBody
    public String getCourseQuiz(@PathVariable("course_id") Integer course_id) {
        List<Quiz> quizList = accessoryServiceImpl.selectQuizByCourseId(course_id);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(quizList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return res;
    }




    /**
     * 7 - 1
     * 获取学生的未完成的quiz
     * @param user_id
     * @param token
     * @return json string
     */
    @GetMapping("/quiz/undo/{user_id}")
    @ResponseBody
    public String userUndoQuiz(@PathVariable("user_id") Integer user_id,
                               @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        User tempUser = userMapper.selectUserByUserId(user_id);
        List<Course> courseList = enrollServiceImpl.selectCoursesByUserId(user_id);
        List<Quiz> allQuizList = new ArrayList<>();
        List<QuizGrade> finishQuizList = gradeServiceImpl.selectQuizGradesByUserId(user_id);
        for (Course course : courseList) {
            List<Quiz> quizInCourse = new ArrayList<>();
            allQuizList.addAll(accessoryServiceImpl.selectQuizByCourseId(course.getCourse_id()));
        }

        List<UnfinishedInfo> infoList = new ArrayList<>();
        HashSet<Tuple> set = new HashSet<>();
        for (QuizGrade quiz1 : finishQuizList) {
            set.add(new Tuple(quiz1.getCourse_id(), quiz1.getQuiz_index()));
        }
        for (Quiz quiz2 : allQuizList) {
            if (set.contains(new Tuple(quiz2.getCourse_id(), quiz2.getQuiz_index()))) {
                continue;
            } else {
                UnfinishedInfo unfinishedInfo = new UnfinishedInfo();
                Course tempCourse = courseServiceImpl.selectCourseByCourseId(quiz2.getCourse_id());
                unfinishedInfo.setCourse_name(tempCourse.getCourse_title());
                unfinishedInfo.setIndex(quiz2.getQuiz_index());
                Quiz tempQuiz = accessoryServiceImpl.selectQuizByCourseIdAndQuizIndex(quiz2.getCourse_id(), quiz2.getQuiz_index());
                unfinishedInfo.setContent(tempQuiz.getQuiz_content());
                unfinishedInfo.setCourse_id(tempCourse.getCourse_id());
                infoList.add(unfinishedInfo);
            }
        }
        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(infoList);
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
     * 7 - 2
     * 获取学生的已完成的quiz
     * @param user_id
     * @param token
     * @return json string
     */
    @GetMapping("/quiz/all/{user_id}")
    @ResponseBody
    public String userAllQuiz(@PathVariable("user_id") Integer user_id,
                               @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        User tempUser = userMapper.selectUserByUserId(user_id);
        List<QuizGrade> quizGradeList = gradeServiceImpl.selectQuizGradesByUserId(user_id);
        List<QuizGradeCluster> gradeList = new ArrayList<>();
        for (QuizGrade quizGrade : quizGradeList) {
            gradeList.add(new QuizGradeCluster(
                    null,
                    quizGrade.getUser_id(),
                    quizGrade.getCourse_id(),
                    courseServiceImpl.selectCourseByCourseId(quizGrade.getCourse_id()).getCourse_title(),
                    quizGrade.getQuiz_index(),
                    quizGrade.getGrade_score(),
                    quizGrade.getGrade_time()
            ));
        }
        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(gradeList);
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
     * 7 - 3
     * 提交quiz
     * @param requestStr
     * @param token
     * @return
     */
    @PostMapping("/quiz/update")
    @ResponseBody
    public String updateQuizGrade(@RequestBody String requestStr,
                                  @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Integer user_id = node.get("user_id").asInt();
        Integer quiz_index = node.get("quiz_index").asInt();
        Integer course_id = node.get("course_id").asInt();
        Integer quiz_score = node.get("quiz_score").asInt();

        QuizGrade quizGrade = new QuizGrade();
        quizGrade.setUser_id(user_id);
        quizGrade.setCourse_id(course_id);
        quizGrade.setQuiz_index(quiz_index);
        quizGrade.setGrade_score(quiz_score);
        quizGrade.setGrade_time(new Timestamp(System.currentTimeMillis()));

        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                gradeServiceImpl.insertQuizGrade(quizGrade);
                return "ok";
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }


    /**
     * 8 - 0
     * 获取学生未提交的作业
     * @param user_id
     * @param token
     * @return
     */
    @GetMapping("/homework/undo/{user_id}")
    @ResponseBody
    public String userUndoHomework(@PathVariable("user_id") Integer user_id,
                                   @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Course> courseList = enrollServiceImpl.selectCoursesByUserId(user_id);
        List<Homework> allList = new ArrayList<>();
        for (Course course : courseList) {
            allList.addAll(accessoryServiceImpl.selectHomeworkByCourseId(course.getCourse_id()));
        }
        List<Submit> finishList = cdnServiceImpl.selectSubmitsByUserId(user_id);
        List<UnfinishedInfo> infoList = new ArrayList<>();

        HashSet<Tuple> set = new HashSet<>();
        for (Submit homework1 : finishList) {
            set.add(new Tuple(homework1.getCourse_id(), homework1.getSubmit_index()));
        }

        for (Homework homework2 : allList) {
            if (set.contains(new Tuple(homework2.getCourse_id(), homework2.getHomework_index()))){
                continue;
            } else {
                UnfinishedInfo unfinishedInfo = new UnfinishedInfo();
                unfinishedInfo.setCourse_id(homework2.getCourse_id());
                Course tempCourse = courseServiceImpl.selectCourseByCourseId(homework2.getCourse_id());
                unfinishedInfo.setCourse_name(tempCourse.getCourse_title());
                unfinishedInfo.setIndex(homework2.getHomework_index());
                Homework tempHomework = accessoryServiceImpl.selectHomeworkByCourseIdAndHomeworkIndex(
                        homework2.getCourse_id(), homework2.getHomework_index()
                );
                unfinishedInfo.setContent(tempHomework.getHomework_content());
                infoList.add(unfinishedInfo);
            }
        }
        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(infoList);
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
     * 8 - 1
     * 获取学生没有成绩的作业
     * @param user_id
     * @param token
     * @return
     */
    @GetMapping("/homework/ungraded/{user_id}")
    @ResponseBody
    public String userUngradedHomework(@PathVariable("user_id") Integer user_id,
                                       @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Submit> submitList = submitMapper.selectSubmitsByUserIdUnchecked(user_id);
        List<UnfinishHomework> list = new ArrayList<>();
        for (Submit submit : submitList) {
            UnfinishHomework homework = new UnfinishHomework(
                    null,
                    submit.getPic_url(),
                    submit.getUser_id(),
                    submit.getUser_name(),
                    submit.getCourse_id(),
                    submit.getSubmit_type(),
                    submit.getSubmit_index(),
                    submit.getSubmit_time(),
                    submit.getSubmit_checked(),
                    courseServiceImpl.selectCourseByCourseId(submit.getCourse_id()).getCourse_title()
            );
            list.add(homework);
        }
        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(list);
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
     * 8 - 2
     * 获取学生已完成的作业
     * @param user_id
     * @param token
     * @return
     */
    @GetMapping("/homework/all/{user_id}")
    @ResponseBody
    public String userAllHomework(@PathVariable("user_id") Integer user_id,
                                  @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<HomeworkGrade> gradeList = gradeServiceImpl.selectHomeworkGradesByUserId(user_id);
        List<HomeworkInfo> infoList = new ArrayList<>();
        for (HomeworkGrade homeworkGrade : gradeList) {
            HomeworkInfo homeworkInfo = new HomeworkInfo();
            homeworkInfo.setCourse_id(homeworkGrade.getCourse_id());
            Course tempCourse = courseServiceImpl.selectCourseByCourseId(homeworkGrade.getCourse_id());
            homeworkInfo.setCourse_name(tempCourse.getCourse_title());
            homeworkInfo.setHomework_index(homeworkGrade.getHomework_index());
            homeworkInfo.setHomework_comment(homeworkGrade.getGrade_content());
            Homework homework = accessoryServiceImpl.selectHomeworkByCourseIdAndHomeworkIndex(
                    tempCourse.getCourse_id(), homeworkGrade.getHomework_index()
            );
            homeworkInfo.setHomework_content(homework.getHomework_content());
            homeworkInfo.setHomework_score(homeworkGrade.getGrade_score());
            homeworkInfo.setHomework_checked(
                    cdnServiceImpl.selectSubmitByStudentIdCourseIdAndSubmitIndex(user_id, homeworkGrade.getCourse_id(), homeworkGrade.getHomework_index()).getSubmit_checked()
            );
            Submit submit = cdnServiceImpl.selectSubmitByStudentIdCourseIdAndSubmitIndex(
                    user_id,
                    tempCourse.getCourse_id(),
                    homework.getHomework_index()
            );
            homeworkInfo.setUrl(submit.getPic_url());
            infoList.add(homeworkInfo);
        }

        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(infoList);
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
     * 8 - 3
     * 提交作业
     * @param requestStr
     * @return 1. "ok"
     *         2. "*no access"
     */
    @PostMapping("/homework/update")
    @ResponseBody
    public String uploadHomework(@RequestBody String requestStr,
                                 @RequestHeader("token") String token){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Integer user_id = node.get("user_id").asInt();
        Integer homework_index = node.get("homework_index").asInt();
        Integer course_id = node.get("course_id").asInt();
        String pdf_url = node.get("pdf_url").asText();

        User tempUser = userMapper.selectUserByUserId(user_id);

        Submit submit = new Submit();
        submit.setPic_url(pdf_url);
        submit.setUser_id(tempUser.getUser_id());
        submit.setUser_name(tempUser.getUser_name());
        submit.setCourse_id(course_id);
        submit.setSubmit_checked(0);
        submit.setSubmit_index(homework_index);
        submit.setSubmit_type("homework");
        submit.setSubmit_time(new Timestamp(System.currentTimeMillis()));

        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                gradeServiceImpl.insertSubmit(submit);
                return "ok";
            } else {
                return "*no access";
            }
        } else {  // no access
            return "*no access";
        }
    }


    /**
     * 9 - 1
     * 根据用户id获取所有通知
     * @param user_id
     * @param token
     * @return json string
     */
    @GetMapping("/notice/{user_id}")
    @ResponseBody
    public String getNotice(@PathVariable("user_id") Integer user_id,
                            @RequestHeader("token") String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Course> courseList = enrollServiceImpl.selectCoursesByUserId(user_id);
        List<Notice> noticeArrayList = new ArrayList<>();
        List<NoticeCluster> noticeList = new ArrayList<>();
        for (Course course : courseList) {
            noticeArrayList.addAll(accessoryServiceImpl.selectNoticeByCourseId(course.getCourse_id()));
        }
        for (Notice notice : noticeArrayList) {
            noticeList.add(new NoticeCluster(
                    notice.getNotice_id(),
                    notice.getCourse_id(),
                    courseServiceImpl.selectCourseByCourseId(notice.getCourse_id()).getCourse_title(),
                    notice.getUser_id(),
                    notice.getNotice_content(),
                    notice.getNotice_time(),
                    notice.getNotice_email()
            ));
        }

        Token tempToken = loginServiceImpl.getTokenIdByMap(user_id);
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) {
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(noticeList);
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

}

