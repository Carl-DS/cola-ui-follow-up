package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaRoleMember;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaRoleMemberDao extends HibernateDao<ColaRoleMember, Long> {
}