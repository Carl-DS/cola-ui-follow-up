package colaui;

import com.colaui.example.model.ColaArea;
import com.colaui.example.service.ColaAreaService;
import com.colaui.provider.Page;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by carl.li on 2017/3/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:application-context.xml", "classpath*:rest-servlet.xml"})
public class ColaAreaControllerTest {

    @Autowired
    private ColaAreaService colaAreaService;

    @Before
    public void setUp() throws Exception {
        System.out.println("使用SpringMVC与Junit4结合,单元测试:setUp before");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("使用SpringMVC与Junit4结合,单元测试:setUp after");
    }

    @Test
    public void getAreas() throws Exception {
        Page<ColaArea> colaAreas = colaAreaService.getAreas(5, 1);
        System.out.println("getAreas(): "+colaAreas);
    }

    @Test
    public void recursionTree() throws Exception {
        List<ColaArea> colaAreas = colaAreaService.recursionTree("1");
        Assert.assertEquals(34,colaAreas.size());
        System.out.print("recursionTree(): "+colaAreas.size());
    }

}