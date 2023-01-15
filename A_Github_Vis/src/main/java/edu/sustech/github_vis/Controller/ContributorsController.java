package edu.sustech.github_vis.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.github_vis.Entity.ContributorRecord;
import edu.sustech.github_vis.Mapper.ContributorsMapper;
import edu.sustech.github_vis.Service.ContributorsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContributorsController {

    @Autowired
    ContributorsServiceImpl contributorsServiceImpl;

    @Autowired
    ContributorsMapper contributorsMapper;

    @GetMapping("/contributors/{repo_name}/{topn}")
    public String getContributors(@PathVariable("repo_name") String repo_name,
                                  @PathVariable("topn") Integer topn) {

        Integer repo_id = mapRepo(repo_name);
        List<ContributorRecord> recordList = contributorsServiceImpl.getSortedTopNContributorsByRepo_id(topn, repo_id);
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
