package edu.sustech.github_vis.Mapper;

import edu.sustech.github_vis.Entity.Issue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IssuesMapper {
    void insertIssue(Issue issue);

    Integer selectIssue_idByIssue_number(Integer id);

    Integer selectIssue_idByIssue_numberRepo_id(Integer number, Integer repo_id);

    List<Issue> selectIssueByRepo_id(Integer repo_id);

}
