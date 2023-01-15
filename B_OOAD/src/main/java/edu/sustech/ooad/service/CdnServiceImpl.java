package edu.sustech.ooad.service;

import edu.sustech.ooad.entity.Pic;
import edu.sustech.ooad.entity.Submit;
import edu.sustech.ooad.mapper.PicMapper;
import edu.sustech.ooad.mapper.SubmitMapper;
import edu.sustech.ooad.service.itf.CdnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CdnServiceImpl implements CdnService {

    @Autowired
    PicMapper picMapper;

    @Autowired
    SubmitMapper submitMapper;

    @Override
    public void insertPic(Pic pic) {
        picMapper.insertPic(pic);
    }

    @Override
    public Pic selectPicById(Integer pic_id) {
        return picMapper.selectPicById(pic_id);
    }

    @Override
    public Pic selectPicByUrl(String pic_url) {
        return picMapper.selectPicByUrl(pic_url);
    }

    @Override
    public List<Submit> selectSubmitsByCourseIdAndSubmitIndex(Integer course_id, Integer submit_index) {
        return submitMapper.selectSubmitsByCourseIdAndSubmitIndex(course_id, submit_index);
    }

    @Override
    public Submit selectSubmitByStudentIdCourseIdAndSubmitIndex(Integer student_id, Integer course_id, Integer submit_index){
        return submitMapper.selectSubmitByStudentIdCourseIdAndSubmitIndex(student_id, course_id, submit_index);
    }

    @Override
    public List<Submit> selectSubmitsByUserId(Integer user_id) {
        return submitMapper.selectSubmitsByUserId(user_id);
    }

}
