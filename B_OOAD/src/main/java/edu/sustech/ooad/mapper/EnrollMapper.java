package edu.sustech.ooad.mapper;

import edu.sustech.ooad.entity.Enroll;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnrollMapper {

    List<Integer> selectUsersByCourseId(Integer course_id);

    List<Integer> selectCoursesByUserId(Integer user_id);

    void insertEnroll(Enroll enroll);
}
