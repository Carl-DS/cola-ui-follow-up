package com.colaui.system.service;

import com.colaui.example.model.ColaUrl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Carl on 2017/3/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:application-context.xml", "classpath*:rest-servlet.xml"})
public class ColaUrlServiceTest {
    @Autowired
    private ColaUrlService colaUrlService;

    @Test
    public void getUrls() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        int result = colaUrlService.getUrls(params).size();
        Assert.assertTrue("查询测试", result>0);
    }

    @Test
    public void saveUrl() throws Exception {
        ColaUrl url = new ColaUrl();
        url.setId("S001");
        url.setLabel("新菜单");
        url.setCompanyId("bstek");
        colaUrlService.saveUrl(url);
    }

    @Test
    public void deleteUrl() throws Exception {
        colaUrlService.deleteUrl("S001");
    }

}