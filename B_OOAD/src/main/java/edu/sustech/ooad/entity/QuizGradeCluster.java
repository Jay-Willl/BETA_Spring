package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizGradeCluster {

    Integer grade_id;

    Integer user_id;

    Integer course_id;

    String course_name;

    Integer quiz_index;

    Integer grade_score;

    Timestamp grade_time;


}
