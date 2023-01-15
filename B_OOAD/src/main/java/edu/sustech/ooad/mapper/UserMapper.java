package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User selectUserByUsername(String user_name);

    List<User> selectAllUsers();

    User selectUserByUserId(Integer user_id);

    User selectUserByUserNameAndRole(String user_name, String user_role);

    Integer selectUserGoldByUserNameAndRole(String user_name, String user_role);

    String selectUserEmailByUserNameAndRole(String user_name, String user_role);

    void insertUser(User user);

    void updateUserGold(Integer user_gold, Integer user_id);

    void rechargeUserGold(Integer user_gold, Integer user_id);

}
