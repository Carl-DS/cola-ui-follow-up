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

    public void saveGroupUser(String groupId, ArrayList<String> groupUserIds) {
        ColaGroupMember groupmember = null;
        for (String groupUserId : groupUserIds) {
            groupmember = new ColaGroupMember();
            groupmember.setId(CommonUtils.uuid());
            groupmember.setGroupId(groupId);
            groupmember.setUsername(groupUserId);
            groupmemberDao.save(groupmember);
        }
    }

    public void saveGroupPosition(String groupId, ArrayList<String> groupPositionIds) {
        ColaGroupMember groupmember = null;
        for (String groupPositionId : groupPositionIds) {
            groupmember = new ColaGroupMember();
            groupmember.setId(CommonUtils.uuid());
            groupmember.setGroupId(groupId);
            groupmember.setPositionId(groupPositionId);
            groupmemberDao.save(groupmember);
        }
    }

    @Override
    public void saveGroupDept(String groupId, ArrayList<String> groupDeptIds) {
        ColaGroupMember groupmember = null;
        for (String groupDeptId : groupDeptIds) {
            groupmember = new ColaGroupMember();
            groupmember.setId(CommonUtils.uuid());
            groupmember.setGroupId(groupId);
            groupmember.setDeptId(groupDeptId);
            groupmemberDao.save(groupmember);
        }
    }

    @Override
    public void deleteByUsername(String groupId, String username) {
        groupmemberDao.deleteByUsername(groupId, username);
    }

    @Override
    public void deleteByPositionId(String groupId, String positionId) {
        groupmemberDao.deleteByPositionId(groupId, positionId);
    }

    @Override
    public void deleteByDeptId(String groupId, String deptId) {
        groupmemberDao.deleteByDeptId(groupId, deptId);
    }

    @Override
    public List<ColaGroupMember> checkSameUser(String groupId, String username) {
        Criteria criteria = groupmemberDao.createCriteria();
        if (StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(username)) {
            Criterion lastRest= Restrictions.eq("groupId", groupId);
            Criterion firstRest= Restrictions.eq("username", username);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return groupmemberDao.find(criteria);
    }

    @Override
    public List<ColaGroupMember> checkSamePosition(String groupId, String positionId) {
        Criteria criteria = groupmemberDao.createCriteria();
        if (StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(positionId)) {
            Criterion lastRest= Restrictions.eq("groupId", groupId);
            Criterion firstRest= Restrictions.eq("positionId", positionId);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return groupmemberDao.find(criteria);
    }

    @Override
    public List<ColaGroupMember> checkSameDept(String groupId, String deptId) {
        Criteria criteria = groupmemberDao.createCriteria();
        if (StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(deptId)) {
            Criterion lastRest= Restrictions.eq("groupId", groupId);
            Criterion firstRest= Restrictions.eq("deptId", deptId);
            criteria.add(Restrictions.and(lastRest, firstRest));
        }
        return groupmemberDao.find(criteria);
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