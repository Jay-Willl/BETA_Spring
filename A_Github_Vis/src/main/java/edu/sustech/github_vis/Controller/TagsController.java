package edu.sustech.github_vis.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sustech.github_vis.Service.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagsController {

    @Autowired
    TagServiceImpl tagServiceImpl;

    @GetMapping("/tags/{repo_name}")
    public String getTags(@PathVariable("repo_name") String repo_name) {
        Integer repo_id = mapRepo(repo_name);
        String res = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            res = objectMapper.writeValueAsString(tagServiceImpl.getSortedTagsByRepo_id(repo_id));
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
