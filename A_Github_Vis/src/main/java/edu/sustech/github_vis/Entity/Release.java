package edu.sustech.github_vis.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Release {
    Integer repo_id;
    String release_tag_name;
    String release_name;
    String created_at;
    Timestamp published_at;
    String release_body;
}
