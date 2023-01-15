package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.ContributorRecord;

import java.util.List;

public interface ContributorsService {

    List<ContributorRecord> getSortedTopNContributorsByRepo_id(Integer top, Integer repo_id);


}
