package edu.sustech.github_vis.Service;

import edu.sustech.github_vis.Entity.TagRecord;
import edu.sustech.github_vis.Mapper.RelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    RelationMapper relationMapper;

    @Override
    public List<TagRecord> getSortedTagsByRepo_id(Integer repo_id) {
        return relationMapper.selectTagRecordsByRepo_id(repo_id);
    }
}
