package edu.sustech.ooad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterClusterDetail {

    Integer chapter_id;

    String chapter_title;

    Integer chapter_index;

    List<Lesson> lessonList;
}
