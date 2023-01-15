package edu.sustech.github_vis.Mapper;

import edu.sustech.github_vis.Entity.Contributor;
import edu.sustech.github_vis.Entity.ContributorRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContributorsMapper {
    void insertContributor(Contributor contributor);

    List<ContributorRecord> selectTopNContributorsByRepo_id(Integer top, Integer repo_id);

    Contributor selectByContributor_nameAndRepo_id(String name, Integer repo_id);
}
