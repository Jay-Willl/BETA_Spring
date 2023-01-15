package edu.sustech.ooad.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    Integer course_id;

    String course_title;

    String course_description;

    String course_cover;

    Integer course_price;

    Integer course_score;

    Integer teacher_id;

    String course_status;

    String course_field;

    String course_tag;

    Integer course_bought;

    Integer course_comment_num;

}
