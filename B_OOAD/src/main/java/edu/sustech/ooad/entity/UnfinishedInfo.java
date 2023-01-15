package edu.sustech.ooad.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * course_name
 * course_id
 * index
 * content
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnfinishedInfo {

    Integer course_id;

    String course_name;

    Integer index;

    String content;

    String url;
}
