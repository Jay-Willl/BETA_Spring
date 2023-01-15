package edu.sustech.ooad.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * used for UserController 5 - 2 to record specific lesson
 * with lesson_index and chapter_index
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonIndexes {

    Integer chapter_index;

    Integer lesson_index;

}
