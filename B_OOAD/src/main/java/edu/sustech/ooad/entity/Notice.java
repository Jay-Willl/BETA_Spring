package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    Integer notice_id;

    Integer course_id;

    Integer user_id;

    String notice_content;

    Timestamp notice_time;

    Boolean notice_email;
}
