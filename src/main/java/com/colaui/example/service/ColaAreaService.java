package com.colaui.example.service;

import com.colaui.example.model.ColaArea;
import com.colaui.helper.Page;

import java.util.List;

/**
 * Created by carl.li on 2017/2/14.
 */
public interface ColaAreaService {
    Page<ColaArea> getAreas(int pageSize, int pageNo);
    List<ColaArea> recursionTree(String parentId);
}
