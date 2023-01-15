package edu.sustech.github_vis.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.github_vis.Entity.IssueRecord;
import edu.sustech.github_vis.Mapper.IssuesMapper;
import edu.sustech.github_vis.Service.IssuesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssuesController {

    @Autowired
    IssuesServiceImpl issuesServiceImpl;

    @Autowired
    IssuesMapper issuesMapper;

    @GetMapping("/issues/created/{repo_name}")
    public String getIssuesByCreatedTime(@PathVariable("repo_name") String repo_name) {
        Integer repo_id = mapRepo(repo_name);
        List<IssueRecord> recordList = issuesServiceImpl.getCreatedRecordByRepo(repo_id);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(recordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/issues/finished/{repo_name}")
    public String getIssuesByFinishedTime(@PathVariable("repo_name") String repo_name) {
        Integer repo_id = mapRepo(repo_name);
        List<IssueRecord> recordList = issuesServiceImpl.getFinishedRecordByRepo(repo_id);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(recordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/issues/duration/{repo_name}")
    public String getIssuesByDuration(@PathVariable("repo_name") String repo_name) {
        Integer repo_id = mapRepo(repo_name);
        List<IssueRecord> recordList = issuesServiceImpl.getDurationRecordByRepo(repo_id);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(recordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    Integer mapRepo(String path) {
        if (path.contains("vscode")) {
            return 1;
        }
        if (path.contains("monaco-editor")) {
            return 2;
        }
        if (path.contains("TypeScript")) {
            return 3;
        }
        return -1;
    }

}
