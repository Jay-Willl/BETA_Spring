package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.TokenUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenUserMapper {

    TokenUser selectMapByUserId(Integer user_id);


    void insertTokenUser(TokenUser tokenUser);

    void updateTokenUser(TokenUser tokenUser);
}
