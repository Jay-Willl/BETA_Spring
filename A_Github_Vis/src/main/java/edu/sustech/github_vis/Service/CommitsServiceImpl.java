package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.Commit;
import edu.sustech.github_vis.Entity.CommitRecord;
import edu.sustech.github_vis.Mapper.CommitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommitsServiceImpl implements CommitsService {

    @Autowired
    CommitMapper commitMapper;

    @Override
    public List<CommitRecord> getSortedCommitsByRepo_id(Integer repo_id) {
        List<Commit> commits = commitMapper.selectCommitsByRepo_id(repo_id);
        List<CommitRecord> commitRecords = new ArrayList<>();
        HashMap<String, CommitRecord> map = new HashMap<>();
        for (Commit commit : commits) {
            String tempTime = commit.getCreated_at().toString().substring(0, 10);
            CommitRecord temp;
            if (map.get(tempTime) == null) {
                temp = new CommitRecord();
                temp.setNumber(1);
                temp.setTime(tempTime);
                map.put(tempTime, temp);
            } else {
                temp = new CommitRecord();
                Integer tempNum = map.get(tempTime).getNumber() + 1;
                temp.setNumber(tempNum);
                temp.setTime(tempTime);
                map.replace(tempTime, temp);
            }
        }
        Iterator<Map.Entry<String, CommitRecord>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, CommitRecord> entry = iterator.next();
            commitRecords.add(entry.getValue());
        }
        commitRecords = commitRecords.stream().filter(commitRecord -> commitRecord.getTime() != null).sorted(new Comparator<CommitRecord>() {
            @Override
            public int compare(CommitRecord o1, CommitRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        }).collect(Collectors.toList());
        return commitRecords;
    }

    @Override
    public List<CommitRecord> getSortedCommitsByContributorAndRepo_id(String name, Integer repo_id) {
        List<Commit> commits = commitMapper.selectCommitsByContributorAndRepo_id(name, repo_id);
        List<CommitRecord> commitRecords = new ArrayList<>();
        HashMap<String, CommitRecord> map = new HashMap<>();
        for (Commit commit : commits) {
            String tempTime = commit.getCreated_at().toString().substring(0, 10);
            CommitRecord temp;
            if (map.get(tempTime) == null) {
                temp = new CommitRecord();
                temp.setNumber(1);
                temp.setTime(tempTime);
                map.put(tempTime, temp);
            } else {
                temp = new CommitRecord();
                Integer tempNum = map.get(tempTime).getNumber() + 1;
                temp.setNumber(tempNum);
                temp.setTime(tempTime);
                map.replace(tempTime, temp);
            }
        }
        Iterator<Map.Entry<String, CommitRecord>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, CommitRecord> entry = iterator.next();
            commitRecords.add(entry.getValue());
        }
        commitRecords = commitRecords.stream().filter(commitRecord -> commitRecord.getTime() != null).sorted(new Comparator<CommitRecord>() {
            @Override
            public int compare(CommitRecord o1, CommitRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        }).collect(Collectors.toList());
        return commitRecords;
    }
}
