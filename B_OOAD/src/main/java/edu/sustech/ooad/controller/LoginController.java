package edu.sustech.ooad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.ooad.auth.JwtAuth;
import edu.sustech.ooad.entity.LoginInfo;
import edu.sustech.ooad.entity.Token;
import edu.sustech.ooad.entity.TokenUser;
import edu.sustech.ooad.entity.User;
import edu.sustech.ooad.mapper.TokenMapper;
import edu.sustech.ooad.mapper.TokenUserMapper;
import edu.sustech.ooad.mapper.UserMapper;
import edu.sustech.ooad.service.CourseServiceImpl;
import edu.sustech.ooad.service.LoginServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenMapper tokenMapper;

    @Autowired
    TokenUserMapper tokenUserMapper;

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    LoginServiceImpl loginServiceImpl;


    /**
     * 教师端和管理端login
     * @param requestStr
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String passwd = jsonNode.get("password").asText();
        User user = userMapper.selectUserByUsername(name);
        if (user != null) {
            if (Objects.equals(user.getUser_passwd(), passwd)) {
                String token = JwtAuth.createToken(name, passwd);
                tokenMapper.insertToken(token, System.currentTimeMillis());
                Token tempToken = tokenMapper.selectTokenByTokenContent(token);
                TokenUser tempTokenUser = tokenUserMapper.selectMapByUserId(user.getUser_id());
                TokenUser tokenUser = new TokenUser();
                tokenUser.setToken_id(tempToken.getToken_id());
                tokenUser.setUser_id(user.getUser_id());
                if (null != tempTokenUser) {
                    loginServiceImpl.updateTokenUser(tokenUser);
                } else {
                    loginServiceImpl.insertTokenUser(tokenUser);
                }
                return token;
            } else {
                return "*wrong password";
            }
        } else {
            return "*no such user";
        }
    }


    /**
     * 学生端login
     * @param requestStr
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public String userLogin(@RequestBody String requestStr) {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String passwd = jsonNode.get("password").asText();
        User user = userMapper.selectUserByUserNameAndRole(name, "student");
        if (user != null) {
            if (Objects.equals(user.getUser_passwd(), passwd)) {
                String token = JwtAuth.createToken(name, passwd);
                tokenMapper.insertToken(token, System.currentTimeMillis());
                Token tempToken = tokenMapper.selectTokenByTokenContent(token);
                TokenUser tempTokenUser = tokenUserMapper.selectMapByUserId(user.getUser_id());
                TokenUser tokenUser = new TokenUser();
                tokenUser.setToken_id(tempToken.getToken_id());
                tokenUser.setUser_id(user.getUser_id());
                if (null != tempTokenUser) {
                    loginServiceImpl.updateTokenUser(tokenUser);
                } else {
                    loginServiceImpl.insertTokenUser(tokenUser);
                }
                LoginInfo loginInfo = new LoginInfo(
                        user.getUser_id(), user.getUser_name(), user.getUser_passwd(), user.getUser_gold(), user.getUser_email(),
                        user.getUser_role(), token
                );
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(loginInfo);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return res;
            } else {
                return "*wrong password";
            }
        } else {
            return "*no such user";
        }
    }

    /**
     * 共同注册, 返回string
     * @param requestStr
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public String registerUser(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String user_name = node.get("user_name").asText();
        String user_passwd = node.get("user_passwd").asText();
        String user_email = node.get("user_email").asText();
        String user_role = node.get("user_role").asText();
        if(loginServiceImpl.registerUser(user_name, user_passwd, user_email, user_role)) {
            return "ok";
        }else {
            return "fail";
        }
    }

    /**
     * @param requestStr
     * @return json string
     */
    @PostMapping("/teacher/information")
    @ResponseBody
    public String teacherInformation(@RequestBody String requestStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String name = jsonNode.get("username").asText();
        String token = jsonNode.get("token").asText();
        User user = userMapper.selectUserByUserNameAndRole(name, "teacher");
        Token tempToken = loginServiceImpl.getTokenIdByMap(user.getUser_id());
        if (tempToken != null) {  // have access
            Claims claims = JwtAuth.parseToken(token);
            if (System.currentTimeMillis() <= claims.getExpiration().getTime() + tempToken.getToken_starttime()) { // have access
                String res = null;
                try {
                    res = objectMapper.writeValueAsString(user);
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
