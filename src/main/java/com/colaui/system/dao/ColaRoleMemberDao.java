package com.colaui.system.dao;

import com.colaui.system.model.ColaRoleMember;
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
public class ColaRoleMemberDao extends HibernateDao<ColaRoleMember, String> {

    public void deleteByUsername(String roleId, String username) {
        extractDeleteById(roleId, username, "username");
    }

    public void deleteByPositionId(String roleId, String positionId) {
        extractDeleteById(roleId, positionId, "positionId");
    }

    public void deleteByDeptId(String roleId, String deptId) {
        extractDeleteById(roleId, deptId, "deptId");
    }

    public void deleteByGroupId(String roleId, String groupId) {
        extractDeleteById(roleId, groupId, "groupId");
    }

    public ArrayList getUsernames(String roleId) {
        return extractGetIds(roleId, "username");
    }

    public ArrayList getPositionIds(String roleId) {
        return extractGetIds(roleId, "positionId");
    }

    public ArrayList getDeptIds(String roleId) {
        return extractGetIds(roleId, "deptId");
    }

    public ArrayList getGroupIds(String roleId) {
        return extractGetIds(roleId, "groupId");
    }

    public void extractDeleteById(String roleId, String id, String type) {
        Map<String, Object> param = new HashMap<>();
        StringBuffer sql = new StringBuffer("delete from ColaRoleMember where 1=1");
        if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(id)) {
            sql.append(" and roleId=:roleId");
            param.put("roleId", roleId);
            if (type.equals("username")) {
                sql.append(" and username=:username");
                param.put("username", id);
            } else if (type.equals("positionId")) {
                sql.append(" and positionId=:positionId");
                param.put("positionId", id);
            } else if (type.equals("deptId")) {
                sql.append(" and deptId=:deptId");
                param.put("deptId", id);
            } else if (type.equals("groupId")) {
                sql.append(" and groupId=:groupId");
                param.put("groupId", id);
            }

        }
        Query query = this.createQuery(sql.toString(), param);
        query.executeUpdate();
    }

    public ArrayList extractGetIds(String roleId, String type) {
        Criteria criteria = this.createCriteria();
        criteria.add(Restrictions.eq("roleId", roleId));
        criteria.add(Restrictions.isNotNull(type));
        List<ColaRoleMember> rms = this.find(criteria);
        ArrayList<String> returnIds = new ArrayList<>();
        for (ColaRoleMember rm : rms) {
            if (type.equals("username")) {
                returnIds.add(rm.getUsername());
            } else if (type.equals("positionId")) {
                returnIds.add(rm.getPositionId());
            } else if (type.equals("deptId")) {
                returnIds.add(rm.getDeptId());
            } else if (type.equals("groupId")) {
                returnIds.add(rm.getGroupId());
            }
        }
        return returnIds;
    }
}