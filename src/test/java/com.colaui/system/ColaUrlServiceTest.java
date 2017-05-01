package com.colaui.system;

import com.colaui.system.model.ColaUrl;
import com.colaui.system.service.ColaUrlService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Carl on 2017/3/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context.xml", "classpath*:spring-mvc-rest.xml"})
public class ColaUrlServiceTest {
    @Autowired
    private ColaUrlService colaUrlService;

    @Before
    public void setUp() throws Exception {
        //ColaUrl url = new ColaUrl();
        //url.setId("S001");
        //url.setLabel("新菜单");
        //url.setCompanyId("bstek");
        //colaUrlService.saveUrl(url);
        System.out.println("使用SpringMVC与Junit4结合,单元测试:setUp before");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("使用SpringMVC与Junit4结合,单元测试:setUp after");
    }

    //@Test
    public void getUrls() throws Exception {
        int result = colaUrlService.getUrls("bstek", null).size();
        System.out.println("ColaUrlServiceTest.getUrls:" + result);
    }

    //@Test
    public void saveUrl() throws Exception {
        ColaUrl url = new ColaUrl();
        url.setId("S001");
        url.setLabel("新菜单");
        url.setCompanyId("bstek");
        colaUrlService.saveUrl(url);
    }

    //@Test
    public void deleteUrl() throws Exception {
        colaUrlService.deleteUrl("S001");
    }

}