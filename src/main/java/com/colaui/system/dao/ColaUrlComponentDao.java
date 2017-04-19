package com.colaui.system.dao;

import com.colaui.example.model.AuthorityType;
import com.colaui.example.model.ColaComponent;
import com.colaui.example.model.ColaUrlComponent;
import com.colaui.hibernate.HibernateDao;
import com.colaui.utils.CommonUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
@Repository
public class ColaUrlComponentDao extends HibernateDao<ColaUrlComponent, Long> {

    public void saveRoleUrlComponents(String roleId, String urlId, List<String> visibleUcIds, List<String> editableUcIds) {
        // 先删除角色页面组件
        deleteRoleUrlsComponents(roleId, Arrays.asList(urlId));
        ColaUrlComponent uc = null;
        for (String u : visibleUcIds) {
            uc = new ColaUrlComponent();
            uc.setId(CommonUtils.uuid());
            uc.setRoleId(roleId);
            uc.setUrlId(urlId);
            uc.setAuthorityType(AuthorityType.read);
            ColaComponent cc = new ColaComponent();
            cc.setComponentId(u);
            cc.setId(uc.getId());
            uc.setComponent(cc);
            this.getSession().save(uc);
        }
        for (String u : editableUcIds) {
            uc = new ColaUrlComponent();
            uc.setId(CommonUtils.uuid());
            uc.setRoleId(roleId);
            uc.setUrlId(urlId);
            uc.setAuthorityType(AuthorityType.write);
            ColaComponent cc = new ColaComponent();
            cc.setComponentId(u);
            cc.setId(uc.getId());
            uc.setComponent(cc);
            this.getSession().save(uc);
        }
    }

    public void deleteRoleUrlsComponents (String roleId, List < String > urls){
        if (StringUtils.hasText(roleId) && urls != null && urls.size() > 0) {
            Criteria c = this.getSession().createCriteria(ColaUrlComponent.class);
            c.add(Restrictions.eq("roleId", roleId));
            c.add(Restrictions.in("urlId", urls));
            List<ColaUrlComponent> list = c.list();
            for (ColaUrlComponent uc : list) {
                // 删除COLA_COMPONENT
                this.getSession().delete(uc.getComponent());
                // 删除COLA_URL_COMPONENT
                this.getSession().delete(uc);
            }
        }
    }
}
