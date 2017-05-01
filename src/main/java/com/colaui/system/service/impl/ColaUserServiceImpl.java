package com.colaui.system.service.impl;

import com.colaui.system.model.ColaUser;
import com.colaui.helper.Page;
import com.colaui.system.dao.ColaGroupMemberDao;
import com.colaui.system.dao.ColaRoleMemberDao;
import com.colaui.system.dao.ColaUserDao;
import com.colaui.system.service.ColaUserService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
@Service
public class ColaUserServiceImpl implements ColaUserService {
    @Autowired
    private ColaUserDao colaUserDao;
    @Autowired
    private ColaGroupMemberDao groupMemberDao;
    @Autowired
    private ColaRoleMemberDao roleMemberDao;

    public Page<ColaUser> getPage(int pageSize, int pageNo, String contain) {
        Criteria criteria = colaUserDao.createCriteria();
        if (StringUtils.isNotEmpty(contain)) {
            Criterion username = Restrictions.like("username", contain, MatchMode.ANYWHERE);
            Criterion cname = Restrictions.like("cname", contain, MatchMode.ANYWHERE);
            Criterion ename = Restrictions.like("ename", contain, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(username, cname, ename));
        }
        return colaUserDao.getPage(pageSize, pageNo, criteria);
    }

    public void save(ColaUser colaUser) {
        colaUserDao.save(colaUser);
    }

    public void delete(String id) {
        colaUserDao.delete(id);
    }

    public void update(ColaUser colaUser) {
        colaUserDao.update(colaUser);
    }

    public ColaUser find(String id) {
        return colaUserDao.get(id);
    }

    public List<ColaUser> find(int from, int limit) {
        return colaUserDao.find(from, limit);
    }

    public boolean check(String username) {
        Criteria criteria = colaUserDao.createCriteria();
        criteria.add(Restrictions.eq("username", username));
        int result = colaUserDao.find(criteria).size();
        return result < 1;
    }

    public Page<ColaUser> groupUsers(int pageSize, int pageNo, String groupId) {
        return extractGetUsers(pageSize, pageNo, groupId, "group");
    }

    public Page<ColaUser> roleUsers(int pageSize, int pageNo, String roleId) {
        return extractGetUsers(pageSize, pageNo, roleId, "role");
    }

    private Page<ColaUser> extractGetUsers(int pageSize, int pageNo, String id, String type) {
        Criteria criteria = colaUserDao.createCriteria();
        ArrayList usernames = null;
        if (StringUtils.isNotEmpty(id)) {
            if (type.equals("group")) {
                usernames = groupMemberDao.getUsernames(id);
            } else if (type.equals("role")) {
                usernames = roleMemberDao.getUsernames(id);
            }
            if (usernames.size() > 0) {
                criteria.add(Restrictions.in("username", usernames));
                return colaUserDao.getPage(pageSize, pageNo, criteria);
            }
        }
        return null;
    }

}
