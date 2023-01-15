package edu.sustech.github_vis.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.github_vis.Mapper.CommitMapper;
import edu.sustech.github_vis.Service.CommitsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommitsController {

    @Autowired
    CommitsServiceImpl commitsServiceImpl;

    @Autowired
    CommitMapper commitMapper;

    @RequestMapping("/commits/{repo_name}")
    public String getCreatedCommits(@PathVariable("repo_name") String repo_name) {
        Integer repo_id = mapRepo(repo_name);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(commitsServiceImpl.getSortedCommitsByRepo_id(repo_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/commits/{repo_name}/{author}")
    public String getCreatedCommitsByAuthor(@PathVariable("repo_name") String repo_name,
                                            @PathVariable("author") String author) {
        Integer repo_id = mapRepo(repo_name);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(commitsServiceImpl.getSortedCommitsByContributorAndRepo_id(author, repo_id));
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
