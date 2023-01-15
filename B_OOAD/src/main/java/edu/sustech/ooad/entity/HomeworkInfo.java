package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * this class is used for UserController 8 - 2
 * to represent return records
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkInfo {

    Integer course_id;

    String course_name;

    Integer homework_index;

    Integer homework_score;

    String homework_content;

    String homework_comment;

    Integer homework_checked;

    String url;

}
