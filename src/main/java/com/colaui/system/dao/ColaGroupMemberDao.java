package com.colaui.system.dao;

import org.springframework.stereotype.Repository;

import com.colaui.example.model.ColaGroupMember;
import com.colaui.hibernate.HibernateDao;

@Repository
public class ColaGroupMemberDao extends HibernateDao<ColaGroupMember, Long> {
}