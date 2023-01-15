package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.Release;

import java.util.List;

public interface ReleasesService {

    List<Release> getReleases(Integer repo_id);

}
