package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * used in UserController 6 - 1 to show complete information
 * about user basic info and enrollment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    User user;

    List<Course> courseList;
}
