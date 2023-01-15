package edu.sustech.github_vis.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRecord {
    Integer count;
    String name;
    String description;
    String color;

}
