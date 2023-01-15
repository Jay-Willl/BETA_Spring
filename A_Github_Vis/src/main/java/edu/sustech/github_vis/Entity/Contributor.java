package edu.sustech.github_vis.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contributor {
    Integer repo_id;
    String contributor_name;
    String contributor_avatar;
    Integer contributor_contributions;

}
