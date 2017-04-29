package com.colaui.system.controller;

import com.colaui.system.model.ColaRoleMember;
import com.colaui.system.model.ColaUrl;
import com.colaui.system.service.ColaRoleMemberService;
import com.colaui.system.service.ColaUrlService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by carl.li on 2017/3/3.
 */
@RestController
@RequestMapping("frame/url")
public class ColaUrlController {
    private static final Logger log = LoggerFactory.getLogger(ColaUrlController.class);

    @Autowired
    private ColaUrlService colaUrlService;
    @Autowired
    private ColaRoleMemberService colaRoleMemberService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public List<ColaUrl> getUrls(@RequestParam(required = false) Map<String, Object> params) {
        log.info("getUrls()===>", params);
        Subject currentUser = SecurityUtils.getSubject();
        // 获取当前登录用户所拥有的角色
        List<ColaRoleMember> roleIds = colaRoleMemberService.getRoleIdsByUsername((String)currentUser.getPrincipal());

        return colaUrlService.getRoleUrls("bstek", roleIds.get(0).getRoleId());
    }

    @RequestMapping(value = "/roleurls", method = RequestMethod.GET)
    public List<ColaUrl> getRoleUrls(@RequestParam(required = false) String roleId) {
        return colaUrlService.getRoleUrls("bstek",roleId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveUrl(@RequestBody ColaUrl url) {
        colaUrlService.saveUrl(url);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUrl(@PathVariable String id) {
        colaUrlService.deleteUrl(id);
    }

    @RequestMapping(value = "/resource/{roleId}", method = RequestMethod.GET)
    public List<ColaUrl> findUrlByRoleId(@PathVariable("roleId") String roleId,
                                         @RequestParam(required = false) String companyId,
                                         @RequestParam(required = false) String parentId) {
        return colaUrlService.findUrlByRoleId(roleId, companyId, parentId);
    }
}
