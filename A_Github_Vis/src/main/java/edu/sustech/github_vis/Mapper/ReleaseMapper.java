package edu.sustech.github_vis.Mapper;

import edu.sustech.github_vis.Entity.Release;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReleaseMapper {

    void insertRelease(Release release);

    List<Release> selectReleasesByRepo_id(Integer repo_id);
}
