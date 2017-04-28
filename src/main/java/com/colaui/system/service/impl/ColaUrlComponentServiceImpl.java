package com.colaui.system.service.impl;

import com.colaui.system.model.ColaUrlComponent;
import com.colaui.helper.Page;
import com.colaui.system.dao.ColaComponentDao;
import com.colaui.system.dao.ColaUrlComponentDao;
import com.colaui.system.service.ColaUrlComponentService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ColaUrlComponentServiceImpl implements ColaUrlComponentService {
    @Autowired
    private ColaUrlComponentDao urlcomponentDao;
    @Autowired
    private ColaComponentDao componentDao;

    public Page<ColaUrlComponent> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = urlcomponentDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return urlcomponentDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaUrlComponent urlcomponent) {
        urlcomponentDao.save(urlcomponent);
    }

    public void delete(long id) {
        urlcomponentDao.delete(id);
    }

    public void update(ColaUrlComponent urlcomponent) {
        urlcomponentDao.update(urlcomponent);
    }

    public ColaUrlComponent find(long id) {
        return urlcomponentDao.get(id);
    }

    public List<ColaUrlComponent> find(int from, int limit) {
        return urlcomponentDao.find(from, limit);
    }

    @Override
    public void saveRoleUrlComponents(List<Map<String, Object>> urlComponents) {
        List<String> visibleUcIds = new ArrayList<>();
        List<String> editableUcIds = new ArrayList<>();
        String roleId = null;
        String urlId = null;
        for (int i=0; i<urlComponents.size(); i++) {
            Map<String, Object> ucMap = urlComponents.get(i);
            String componentId = (String) ucMap.get("componentId");
            roleId = (String) ucMap.get("roleId");
            urlId = (String) ucMap.get("urlId");
            boolean visible = ucMap.get("visible") != null ? Boolean.valueOf(ucMap.get("visible") + "") : false;
            boolean editable = ucMap.get("editable") != null ? Boolean.valueOf(ucMap.get("editable") + "") : false;
            if (visible) {
                visibleUcIds.add(componentId);
            }
            if (editable) {
                editableUcIds.add(componentId);
            }
        }
        urlcomponentDao.saveRoleUrlComponents(roleId, urlId, visibleUcIds, editableUcIds);
    }

}