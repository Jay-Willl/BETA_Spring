package edu.sustech.github_vis.Mapper;

import edu.sustech.github_vis.Entity.Commit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommitMapper {

    void insertCommit(Commit commit);

    List<Commit> selectCommitsByRepo_id(Integer repo_id);

    List<Commit> selectCommitsByContributorAndRepo_id(String name, Integer repo_id);
}
