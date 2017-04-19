package com.colaui.example.controller;

import com.colaui.example.model.ColaAuth;
import com.colaui.example.service.ColaAuthService;
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

    @RequestMapping(value = "/load/html/body", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    public @ResponseBody String loadBody(@RequestParam String user, HttpServletRequest request) {
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
            for (Element element : document.body().getAllElements()) {
                if (element.hasAttr("id")) {
                    Map<String, Boolean> auth = getComponentAuth(user,element.attr("id"));
                    if (null != auth && auth.size() > 0) {
                        element.attr("visible", auth.get("visible") + "");
                        element.attr("editable", auth.get("editable") + "");
                    }
                }
            }
            return document.body().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Boolean> getComponentAuth(String user, String id) {
        Map<String, Boolean> authMap = null;
        List<ColaAuth> auths = colaAuthService.getAuths(user);
        String authIdStr = null;
        for (ColaAuth auth : auths) {
            authIdStr = user + id;
            if (auth.getId().equals(authIdStr)) {
                authMap = new HashMap<String, Boolean>();
                authMap.put("editable", auth.getEditable());
                authMap.put("visible", auth.getVisible());
            }
        }
        return authMap;
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
        String ucIdStr = null;
        List<ColaAuth> auths = colaAuthService.getAuths(user);

        for (String ucId : ucIds) {
            ucAuth = new HashMap<String, Object>();
            authMap = new HashMap<String, Boolean>();
            ucAuth.put("id", ucId);
            ucIdStr = user + ucId;
            for(ColaAuth colaAuth:auths) {
                if (ucIdStr.equals(colaAuth.getId())) {
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
        List<String> ucIds = new ArrayList<>();
        //File file = new File(url);
        //if (!file.exists()) {
        //    return ucIds;
        //}
        //Document document;
        //try {
        //    document = Jsoup.parse(file, "utf-8");
        //    Element body = document.body();
        //    for (Element element : body.getAllElements()) {
        //        if (element.hasAttr("id")) {
        //            ucIds.add(element.attr("id"));
        //        }
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        return ucIds;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveAuth(@RequestBody ArrayList<Map<String,Object>> colaAuths) throws IOException {
        ColaAuth auth = null;
        boolean editable = false;
        for (Map<String, Object> colaAuth:colaAuths) {
            auth = new ColaAuth();
            editable = (Boolean) colaAuth.get("editable");
            auth.setId((String) colaAuth.get("id"));
            if (editable) {
                auth.setDisabled(false);
            } else {
                auth.setDisabled(true);
            }
            auth.setEditable(editable);
            auth.setUser((String) colaAuth.get("user"));
            auth.setVisible((Boolean) colaAuth.get("visible"));
            colaAuthService.save(auth);
        }
    }

}
