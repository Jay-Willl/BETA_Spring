package edu.sustech.github_vis.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
    Integer repo_id;
    Integer issue_number;
    String label_name;
    String label_color;
    String label_description;
}
