package edu.sustech.ooad.service;

import edu.sustech.ooad.entity.Token;
import edu.sustech.ooad.entity.TokenUser;
import edu.sustech.ooad.entity.User;
import edu.sustech.ooad.mapper.TokenMapper;
import edu.sustech.ooad.mapper.TokenUserMapper;
import edu.sustech.ooad.mapper.UserMapper;
import edu.sustech.ooad.service.itf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenUserMapper tokenUserMapper;

    @Autowired
    TokenMapper tokenMapper;

    @Override
    public boolean registerUser(String user_name, String user_passwd, String user_email, String user_role) {
        User user = new User();
        user.setUser_name(user_name);
        user.setUser_passwd(user_passwd);
        user.setUser_email(user_email);
        user.setUser_role(user_role);
        try {
            userMapper.insertUser(user);
        } catch (DataIntegrityViolationException e) {
            return false;
        }
        return true;
    }

    @Override
    public Token getTokenIdByMap(Integer user_id) {
        TokenUser tokenUser = tokenUserMapper.selectMapByUserId(user_id);
        if (null == tokenUser) {
            return null;
        } else {
            Integer token_id = tokenUser.getToken_id();
            return tokenMapper.selectTokenByTokenId(token_id);
        }
    }

    @Override
    public void insertTokenUser(TokenUser tokenUser) {
        tokenUserMapper.insertTokenUser(tokenUser);
    }

    @Override
    public void updateTokenUser(TokenUser tokenUser) {
        tokenUserMapper.updateTokenUser(tokenUser);
    }

    @Override
    public void insertOrUpdate(TokenUser tokenUser) {

    }


}
