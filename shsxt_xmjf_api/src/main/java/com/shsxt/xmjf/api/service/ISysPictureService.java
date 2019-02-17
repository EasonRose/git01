package com.shsxt.xmjf.api.service;

import java.util.List;
import java.util.Map;

public interface ISysPictureService {
    public List<Map<String,Object>> querySysPicturesByItemId(Integer itemId);
}
