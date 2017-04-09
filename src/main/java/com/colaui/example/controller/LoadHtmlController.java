package com.colaui.example.controller;

import com.colaui.example.dao.ColaAuthDao;
import com.colaui.example.model.ColaAuth;
import com.colaui.example.service.ColaAuthService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cola")
public class LoadHtmlController {

    private static String[] removeTagNames = "script,link".split(",");

    @Autowired
    private ColaAuthService colaAuthService;
    @Autowired
    private ColaAuthDao colaAuthDao;

    @RequestMapping(value = "/load/html/body", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    public @ResponseBody String loadBody(HttpServletRequest request) {
        String filePath = "/Users/Carl/workspace/IdeaProjects/cola-ui-follow-up/src/main/webapp/frame/security/auth1.html";
        File file = new File(filePath);
        Document document;
        try {
            document = Jsoup.parse(file, "utf-8");
            for (String tagName : removeTagNames) {
                Elements scripts = document.select(tagName);
                for (Element script : scripts) {
                    script.remove();
                }
            }
            return document.body().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * /service/frame/component/auth?url=/frame/url/urls.html
     * url visible:false表示不可见，disabled为true表示只读
     *
     * @return [{"id":"SystemDropDown","visible":true,"disabled":true},{"id":"urlTree","visible":false}]
     * @throws IOException
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public List<Map<String, Object>> loadAuth(@RequestParam(required = false) Map<String, Object> param,
                                              HttpServletRequest request) throws IOException {
        String url = (String) param.get("url");
        String user = (String) param.get("user");

        List<Map<String, Object>> ucObjAuths = new ArrayList<Map<String, Object>>();
        String basePath = request.getSession().getServletContext().getRealPath("//");
        String path = basePath + url;
        List<String> ucIds = getUrlComponents(path);

        Map<String, Object> ucAuth = null;
        Map<String, Boolean> authMap = null;
        Criteria criteria = colaAuthDao.createCriteria();
        criteria.add(Restrictions.eq("user", user));
        List<ColaAuth> auths = criteria.list();

        for (String ucId : ucIds) {
            ucAuth = new HashMap<String, Object>();
            authMap = new HashMap<String, Boolean>();
            ucAuth.put("id", ucId);
            for(ColaAuth colaAuth:auths) {
                if (ucId.equals(colaAuth.getId())) {
                    authMap.put("disabled", colaAuth.getDisabled());
                    authMap.put("editable", colaAuth.getEditable());
                    authMap.put("visible", colaAuth.getVisible());
                }
            }
            ucAuth.putAll(authMap);
            ucObjAuths.add(ucAuth);
        }
        return ucObjAuths;
    }

    private List<String> getUrlComponents(String url) {
        List<String> ucIds = new ArrayList<String>();
        File file = new File(url);
        if (!file.exists()) {
            return ucIds;
        }
        Document document;
        try {
            document = Jsoup.parse(file, "utf-8");
            Element body = document.body();
            for (Element element : body.getAllElements()) {
                if (element.hasAttr("id")) {
                    ucIds.add(element.attr("id"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ucIds;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveAuth(@RequestBody ArrayList<Map<String,Object>> colaAuths) throws IOException {
        ColaAuth auth = null;
        for (Map<String, Object> colaAuth:colaAuths) {
            auth = new ColaAuth();
            auth.setId((String) colaAuth.get("id"));
            //auth.setDisabled((Boolean) colaAuth.get("disabled"));
            auth.setEditable((Boolean) colaAuth.get("editable"));
            //auth.setUser((String) colaAuth.get("user"));
            auth.setVisible((Boolean) colaAuth.get("visible"));
            colaAuthService.save(auth);
        }
    }

}
