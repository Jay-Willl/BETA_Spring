package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkGrade {

    Integer grade_id;

    Integer user_id;

    Integer course_id;

    Integer homework_index;

    Integer grade_score;

    Timestamp grade_time;

    String grade_content;
}
