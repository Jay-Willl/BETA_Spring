package edu.sustech.ooad.service.itf;

import edu.sustech.ooad.entity.Token;
import edu.sustech.ooad.entity.TokenUser;

public interface LoginService {

    boolean registerUser(String user_name, String user_passwd, String user_email, String user_role);

    Token getTokenIdByMap(Integer user_id);

    void insertTokenUser(TokenUser tokenUser);

    void updateTokenUser(TokenUser tokenUser);

    void insertOrUpdate(TokenUser tokenUser);

}
