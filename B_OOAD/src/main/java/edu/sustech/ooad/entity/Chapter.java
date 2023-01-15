package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {

    Integer chapter_id;

    Integer chapter_index;

    String chapter_title;

    Integer course_id;
}
