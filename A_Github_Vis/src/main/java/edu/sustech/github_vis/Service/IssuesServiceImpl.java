package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.Issue;
import edu.sustech.github_vis.Entity.IssueRecord;
import edu.sustech.github_vis.Mapper.IssuesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssuesServiceImpl implements IssuesService {

    @Autowired
    IssuesMapper issuesMapper;

    @Override
    public List<IssueRecord> getCreatedRecordByRepo(Integer repo_id) {
        List<Issue> issues = issuesMapper.selectIssueByRepo_id(repo_id);
        List<IssueRecord> issueRecords = new ArrayList<>();
        HashMap<String, IssueRecord> map = new HashMap<>();
        for (Issue issue : issues) {
            String tempTime = issue.getCreated_at().toString().substring(0, 10);
            IssueRecord temp;
            if (map.get(tempTime) == null) {
                temp = new IssueRecord();
                temp.setNumber(1);
                temp.setTime(tempTime);
                map.put(tempTime, temp);
            } else {
                temp = new IssueRecord();
                Integer tempNum = map.get(tempTime).getNumber() + 1;
                temp.setNumber(tempNum);
                temp.setTime(tempTime);
                map.replace(tempTime, temp);
            }
        }
        Iterator<Map.Entry<String, IssueRecord>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, IssueRecord> entry = iterator.next();
            issueRecords.add(entry.getValue());
        }
        issueRecords = issueRecords.stream().filter(issueRecord -> issueRecord.getTime() != null).sorted(new Comparator<IssueRecord>() {
            @Override
            public int compare(IssueRecord o1, IssueRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        }).collect(Collectors.toList());
        System.out.println(issueRecords);
        return issueRecords;
    }

    @Override
    public List<IssueRecord> getFinishedRecordByRepo(Integer repo_id) {
        List<Issue> issues = issuesMapper.selectIssueByRepo_id(repo_id);
        List<IssueRecord> issueRecords = new ArrayList<>();
        HashMap<String, IssueRecord> map = new HashMap<>();
        for (Issue issue : issues) {
            if (issue.getClosed_at() != null) {
                String tempTime = issue.getClosed_at().toString().substring(0, 10);
                IssueRecord temp;
                if (map.get(tempTime) == null) {
                    temp = new IssueRecord();
                    temp.setNumber(1);
                    temp.setTime(tempTime);
                    map.put(tempTime, temp);
                } else {
                    temp = new IssueRecord();
                    Integer tempNum = map.get(tempTime).getNumber() + 1;
                    temp.setNumber(tempNum);
                    temp.setTime(tempTime);
                    map.replace(tempTime, temp);
                }
            }
        }
        Iterator<Map.Entry<String, IssueRecord>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, IssueRecord> entry = iterator.next();
            issueRecords.add(entry.getValue());
        }
        issueRecords = issueRecords.stream().filter(issueRecord -> issueRecord.getTime() != null).sorted(new Comparator<IssueRecord>() {
            @Override
            public int compare(IssueRecord o1, IssueRecord o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        }).collect(Collectors.toList());
        System.out.println(issueRecords);
        return issueRecords;
    }

    @Override
    public List<IssueRecord> getDurationRecordByRepo(Integer repo_id) {
        List<Issue> issues = issuesMapper.selectIssueByRepo_id(repo_id);
        List<IssueRecord> issueRecords = new ArrayList<>();
        HashMap<Integer, IssueRecord> map = new HashMap<>();
        for (Issue issue : issues) {
            if (issue.getClosed_at() != null) {
                Integer duration = issue.getIssue_duration();
                IssueRecord temp;
                if (map.get(duration) == null) {
                    temp = new IssueRecord();
                    temp.setNumber(1);
                    temp.setDuration(duration);
                    map.put(duration, temp);
                } else {
                    temp = new IssueRecord();
                    Integer tempNum = map.get(duration).getNumber() + 1;
                    temp.setNumber(tempNum);
                    temp.setDuration(duration);
                    map.replace(duration, temp);
                }
            }
        }
        Iterator<Map.Entry<Integer, IssueRecord>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, IssueRecord> entry = iterator.next();
            issueRecords.add(entry.getValue());
        }
        issueRecords = issueRecords.stream().filter(issueRecord -> issueRecord.getDuration() != null).sorted(new Comparator<IssueRecord>() {
            @Override
            public int compare(IssueRecord o1, IssueRecord o2) {
                return o1.getDuration() - o2.getDuration();
            }
        }).collect(Collectors.toList());
        System.out.println(issueRecords);
        return issueRecords;
    }
}
