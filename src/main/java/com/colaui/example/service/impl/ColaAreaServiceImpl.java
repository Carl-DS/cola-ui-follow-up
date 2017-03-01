package com.colaui.example.service.impl;

import com.colaui.example.dao.ColaAreaDao;
import com.colaui.example.model.ColaArea;
import com.colaui.example.service.ColaAreaService;
import com.colaui.provider.Page;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by carl.li on 2017/2/14.
 */
@Service
@Transactional
public class ColaAreaServiceImpl implements ColaAreaService {

    @Autowired
    private ColaAreaDao colaAreaDao;

    public Page<ColaArea> getAreas(int pageSize, int pageNo) {
        return colaAreaDao.getPage(pageSize, pageNo);
    }

    public List<ColaArea> recursionTree(String parentId) {
        Criteria criteria = colaAreaDao.createCriteria();
        if (StringUtils.hasText(parentId)) {
            criteria.add(Restrictions.eq("parentId", parentId));
        }
        return colaAreaDao.find(criteria);
    }
}
