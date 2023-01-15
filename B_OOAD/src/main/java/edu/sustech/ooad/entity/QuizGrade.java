package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizGrade {

    Integer grade_id;

    Integer user_id;

    Integer course_id;

    Integer quiz_index;

    Integer grade_score;

    Timestamp grade_time;
}
