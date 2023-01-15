package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    Integer quiz_id;

    String quiz_content;

    Integer course_id;

    Integer quiz_index;

    Timestamp quiz_time;
}
