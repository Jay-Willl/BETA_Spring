package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homework {

    Integer homework_id;

    String homework_content;

    Integer course_id;

    Integer homework_index;

    Timestamp homework_time;

}
