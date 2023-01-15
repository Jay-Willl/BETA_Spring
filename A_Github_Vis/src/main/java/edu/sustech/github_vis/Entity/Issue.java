package edu.sustech.github_vis.Entity;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    Integer issue_number;  // #169391
    Integer repo_id;
    Timestamp created_at; // "2022-12-16T11:42:55Z"
    Timestamp closed_at; // null
    String issue_state; // "open"
    Integer issue_duration; // in day

    @Override
    public String toString() {
        return "Issue{" +
                "issue_number=" + issue_number +
                ", repo_id=" + repo_id +
                ", created_at=" + created_at +
                ", closed_at=" + closed_at +
                ", issue_state='" + issue_state + '\'' +
                ", duration=" + issue_duration +
                '}';
    }
}
