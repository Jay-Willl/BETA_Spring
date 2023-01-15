package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Pic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PicMapper {

    void insertPic(Pic pic);

    Pic selectPicById(Integer pic_id);

    Pic selectPicByUrl(String url);
}
