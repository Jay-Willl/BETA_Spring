package edu.sustech.ooad.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    String lesson_id;

    Integer lesson_index;

    String lesson_title;

    String lesson_description;

    Integer lesson_viewcounts;

    Integer lesson_score;

    Integer chapter_id;

}
