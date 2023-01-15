package edu.sustech.github_vis.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributorRecord {
    String name;
    Integer ranks;
    String avatar_url;
    String number;
    List<CommitRecord> recordList;
}
