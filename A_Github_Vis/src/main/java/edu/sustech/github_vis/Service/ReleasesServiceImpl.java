package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.Release;
import edu.sustech.github_vis.Mapper.ReleaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleasesServiceImpl implements ReleasesService {

    @Autowired
    ReleaseMapper releaseMapper;

    @Override
    public List<Release> getReleases(Integer repo_id) {
        List<Release> releases = releaseMapper.selectReleasesByRepo_id(repo_id);
        List<Release> res = new ArrayList<>();
        for (Release release : releases) {
            Release temp = new Release();
            temp.setRepo_id(release.getRepo_id());
            temp.setRelease_body(release.getRelease_body());
            temp.setRelease_name(release.getRelease_name());
            temp.setRelease_tag_name(release.getRelease_tag_name());
            String created = trimTime(release.getCreated_at().toString()).substring(0, 10);
            System.out.println(created);
            temp.setCreated_at(created);
            temp.setPublished_at(release.getPublished_at());
            res.add(temp);
        }
        return res;
    }

    String trimTime(String time) {
        String temp = time.replace("T", " ");
        temp = temp.replace("Z", " ");
        temp = temp.trim();
        return temp;
    }
}
