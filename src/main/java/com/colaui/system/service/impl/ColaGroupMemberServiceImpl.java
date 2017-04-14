package com.colaui.system.service.impl;

import com.colaui.example.model.ColaGroupMember;
import com.colaui.provider.Page;
import com.colaui.system.dao.ColaGroupMemberDao;
import com.colaui.system.service.ColaGroupMemberService;
import com.colaui.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColaGroupMemberServiceImpl implements ColaGroupMemberService {
    @Autowired
    private ColaGroupMemberDao groupmemberDao;

    public Page<ColaGroupMember> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = groupmemberDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion lastRest= Restrictions.like("lastName", contain, MatchMode.ANYWHERE);
            Criterion firstRest= Restrictions.like("firstName", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(lastRest, firstRest));
        }
        return groupmemberDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(String groupId, ArrayList<String> groupUserIds) {
        ColaGroupMember groupmember = null;
        for (String groupUserId : groupUserIds) {
            groupmember = new ColaGroupMember();
            groupmember.setId(CommonUtils.uuid());
            groupmember.setGroupId(groupId);
            groupmember.setUsername(groupUserId);
            groupmemberDao.save(groupmember);
        }
    }

    @Override
    public void deleteByUsername(String groupId, String username) {
        groupmemberDao.deleteByUsername(groupId, username);
    }

    public void delete(long id) {
        groupmemberDao.delete(id);
    }

    public void update(ColaGroupMember groupmember) {
        groupmemberDao.update(groupmember);
    }

    public ColaGroupMember find(long id) {
        return groupmemberDao.get(id);
    }

    public List<ColaGroupMember> find(int from, int limit) {
        return groupmemberDao.find(from, limit);
    }

}