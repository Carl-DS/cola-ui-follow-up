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

/**
 * Created by carl.li on 2017/3/3.
 */
@RestController
@RequestMapping("/frame/url")
public class ColaUrlController {
    private static final Logger log = LoggerFactory.getLogger(ColaUrlController.class);

    @Autowired
    private ColaUrlService colaUrlService;
    @Autowired
    private ColaRoleMemberService colaRoleMemberService;

    @GetMapping("/menus")
    public List<ColaUrl> getUrls(@RequestParam(required = false) String companyId) {
        Subject currentUser = SecurityUtils.getSubject();
        String username = (String) currentUser.getPrincipal();
        if (username.equals("admin")) {
            return colaUrlService.getUrls("bstek", null);
        } else {
            // 获取当前登录用户所拥有的角色
            List<ColaRoleMember> roleIds = colaRoleMemberService.getRoleIdsByUsername(username);
            return colaUrlService.getRoleUrls("bstek", roleIds.get(0).getRoleId());
        }
    }

    @GetMapping("/roleurls")
    public List<ColaUrl> getRoleUrls(@RequestParam(required = false) String roleId) {
        return colaUrlService.getRoleUrls("bstek", roleId);
    }

    @PostMapping
    public void saveUrl(@RequestBody ColaUrl url) {
        colaUrlService.saveUrl(url);
    }

    @DeleteMapping("/{id}")
    public void deleteUrl(@PathVariable String id) {
        colaUrlService.deleteUrl(id);
    }

    @GetMapping("/resource/{roleId}")
    public List<ColaUrl> findUrlByRoleId(@PathVariable("roleId") String roleId,
                                         @RequestParam(required = false) String companyId,
                                         @RequestParam(required = false) String parentId) {
        return colaUrlService.findUrlByRoleId(roleId, companyId, parentId);
    }
}
