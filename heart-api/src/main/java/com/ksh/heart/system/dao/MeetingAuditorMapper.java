package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ksh.heart.system.entity.meeting.Auditor;
import org.springframework.stereotype.Repository;

/**
 * 审核记录
 */
@Repository
public interface MeetingAuditorMapper extends BaseMapper<Auditor> {

}
