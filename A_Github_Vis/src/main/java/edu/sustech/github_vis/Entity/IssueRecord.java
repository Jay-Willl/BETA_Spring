package edu.sustech.github_vis.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueRecord {
    Integer number;
    String time;
    Integer duration;
}
