package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.CommitRecord;
import edu.sustech.github_vis.Entity.Contributor;
import edu.sustech.github_vis.Entity.ContributorRecord;
import edu.sustech.github_vis.Mapper.ContributorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContributorsServiceImpl implements ContributorsService {

    @Autowired
    ContributorsMapper contributorsMapper;

    @Autowired
    CommitsServiceImpl commitsServiceImpl;


    @Override
    public List<ContributorRecord> getSortedTopNContributorsByRepo_id(Integer top, Integer repo_id) {
        List<ContributorRecord> recordList = new ArrayList<>();
        recordList = contributorsMapper.selectTopNContributorsByRepo_id(top, repo_id);
        for (ContributorRecord contributorRecord : recordList) {
            Contributor contributor = contributorsMapper.selectByContributor_nameAndRepo_id(contributorRecord.getName(), repo_id);
            contributorRecord.setAvatar_url(contributor.getContributor_avatar());
            contributorRecord.setNumber(contributorRecord.getNumber());
            List<CommitRecord> commitRecordList = commitsServiceImpl.getSortedCommitsByContributorAndRepo_id(contributorRecord.getName(), repo_id);
            contributorRecord.setRecordList(commitRecordList);
        }
        System.out.println(recordList);
        return recordList;
    }
}
