package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.CommitRecord;

import java.util.List;

public interface CommitsService {

    List<CommitRecord> getSortedCommitsByRepo_id(Integer repo_id);

    List<CommitRecord> getSortedCommitsByContributorAndRepo_id(String name, Integer repo_id);
}
