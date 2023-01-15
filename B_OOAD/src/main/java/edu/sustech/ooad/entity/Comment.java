package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    Integer comment_id;

    Integer user_id;

    Integer course_id;

    String comment_content;

    Timestamp comment_time;
}
