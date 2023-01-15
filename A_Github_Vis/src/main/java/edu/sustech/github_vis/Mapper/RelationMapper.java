package edu.sustech.github_vis.Mapper;

import edu.sustech.github_vis.Entity.Relation;
import edu.sustech.github_vis.Entity.TagRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationMapper {
    void insertRelation(Relation relation);

    List<TagRecord> selectTagRecordsByRepo_id(Integer repo_id);
}
