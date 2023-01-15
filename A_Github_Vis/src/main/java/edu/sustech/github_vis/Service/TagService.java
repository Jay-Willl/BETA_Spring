package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.TagRecord;

import java.util.List;

public interface TagService {

    List<TagRecord> getSortedTagsByRepo_id(Integer repo_id);

}
