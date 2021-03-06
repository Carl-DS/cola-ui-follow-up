package com.colaui.system.dao;

import com.colaui.system.model.ColaGroupMember;
import com.colaui.helper.hibernate.HibernateDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ColaGroupMemberDao extends HibernateDao<ColaGroupMember, Long> {

    public void deleteByUsername(String groupId, String username) {
        Map<String, Object> param = new HashMap<>();
        StringBuffer sql = new StringBuffer("delete from ColaGroupMember where 1=1");
        if (StringUtils.isNotEmpty(username)) {
            sql.append(" and username=:username");
            param.put("username", username);
        }
        if (StringUtils.isNotEmpty(groupId)) {
            sql.append(" and groupId=:groupId");
            param.put("groupId", groupId);
        }
        Query query = this.createQuery(sql.toString(), param);
        query.executeUpdate();
    }

    public void deleteByPositionId(String groupId, String positionId) {
        Map<String, Object> param = new HashMap<>();
        StringBuffer sql = new StringBuffer("delete from ColaGroupMember where 1=1");
        if (StringUtils.isNotEmpty(positionId) && StringUtils.isNotEmpty(groupId)) {
            sql.append(" and positionId=:positionId");
            param.put("positionId", positionId);
            sql.append(" and groupId=:groupId");
            param.put("groupId", groupId);
        }
        Query query = this.createQuery(sql.toString(), param);
        query.executeUpdate();
    }

    public void deleteByDeptId(String groupId, String deptId) {
        Map<String, Object> param = new HashMap<>();
        StringBuffer sql = new StringBuffer("delete from ColaGroupMember where 1=1");
        if (StringUtils.isNotEmpty(deptId) && StringUtils.isNotEmpty(groupId)) {
            sql.append(" and deptId=:deptId");
            param.put("deptId", deptId);
            sql.append(" and groupId=:groupId");
            param.put("groupId", groupId);
        }
        Query query = this.createQuery(sql.toString(), param);
        query.executeUpdate();
    }

    public ArrayList getUsernames(String groupId) {
        Criteria criteria = this.createCriteria();
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.add(Restrictions.isNotNull("username"));
        List<ColaGroupMember> groupList = this.find(criteria);
        ArrayList<String> usernames = new ArrayList<>();
        for (ColaGroupMember group : groupList) {
            usernames.add(group.getUsername());
        }
        return usernames;
    }

    public ArrayList getPositionIds(String groupId) {
        Criteria criteria = this.createCriteria();
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.add(Restrictions.isNotNull("positionId"));
        List<ColaGroupMember> groupList = this.find(criteria);
        ArrayList<String> positionIds = new ArrayList<>();
        for (ColaGroupMember group : groupList) {
            positionIds.add(group.getPositionId());
        }
        return positionIds;
    }

    public ArrayList getDeptIds(String groupId) {
        Criteria criteria = this.createCriteria();
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.add(Restrictions.isNotNull("deptId"));
        List<ColaGroupMember> groupList = this.find(criteria);
        ArrayList<String> deptIds = new ArrayList<>();
        for (ColaGroupMember group : groupList) {
            deptIds.add(group.getDeptId());
        }
        return deptIds;
    }

}