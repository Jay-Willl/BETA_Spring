package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {

    Token selectTokenByTokenId(Integer token_id);

    Token selectTokenByTokenContent(String token_content);

    void insertToken(String token_content, Long starttime);


}
