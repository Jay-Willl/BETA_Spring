package edu.sustech.github_vis.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commit {
    Integer repo_id;
    String commit_sha;
    String commit_author;
    Timestamp created_at;
}
