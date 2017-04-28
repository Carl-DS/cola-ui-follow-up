package com.colaui.system.service.impl;

import com.colaui.system.model.ColaMessageTemplate;
import com.colaui.helper.Page;
import com.colaui.system.dao.ColaMessageTemplateDao;
import com.colaui.system.service.ColaMessageTemplateService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaMessageTemplateServiceImpl implements ColaMessageTemplateService {
    @Autowired
    private ColaMessageTemplateDao messagetemplateDao;

    public Page<ColaMessageTemplate> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = messagetemplateDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return messagetemplateDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaMessageTemplate messagetemplate) {
        messagetemplateDao.save(messagetemplate);
    }

    public void delete(String id) {
        messagetemplateDao.delete(id);
    }

    public void update(ColaMessageTemplate messagetemplate) {
        messagetemplateDao.update(messagetemplate);
    }

    public ColaMessageTemplate find(String id) {
        return messagetemplateDao.get(id);
    }

    public List<ColaMessageTemplate> find(int from, int limit) {
        return messagetemplateDao.find(from, limit);
    }

}