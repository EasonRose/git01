package com.shsxt.xmjf.server.service;

import com.shsxt.xmjf.api.service.ISysPictureService;
import com.shsxt.xmjf.server.db.dao.SysPictureMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SysPictureServiceImpl implements ISysPictureService {
    @Resource
    private SysPictureMapper sysPictureMapper;
    @Override
    public List<Map<String, Object>> querySysPicturesByItemId(Integer itemId) {
        return sysPictureMapper.querySysPicturesByItemId(itemId);
    }
}
