package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enroll {

    Integer enroll_id;

    Integer user_id;

    Integer teacher_id;

    Integer course_id;

    Integer enroll_gold;

    Timestamp enroll_time;

}
