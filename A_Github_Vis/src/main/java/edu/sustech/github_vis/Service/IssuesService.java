package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.IssueRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IssuesService {
    List<IssueRecord> getCreatedRecordByRepo(Integer repo_id);

    List<IssueRecord> getFinishedRecordByRepo(Integer repo_id);

    List<IssueRecord> getDurationRecordByRepo(Integer repo_id);
}
