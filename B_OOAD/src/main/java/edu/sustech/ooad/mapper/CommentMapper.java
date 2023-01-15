package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByCourseId(Integer course_id);

    void insertComment(Comment comment);


}
