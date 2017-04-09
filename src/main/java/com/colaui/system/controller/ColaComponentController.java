package com.colaui.system.controller;

import com.colaui.system.service.ColaComponentService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("frame/component")
public class ColaComponentController {
    protected static final Logger logger = LoggerFactory.getLogger(ColaComponentController.class);


    private static String[] removeTagNames = "script,link".split(",");

    @RequestMapping(value = "/load", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    public String loadBody(@RequestParam String url, HttpServletRequest request) throws IOException {
        String html = "";
        if (!StringUtils.hasText(url)) {
            return html;
        }
        String basePath = request.getSession().getServletContext().getRealPath("//");
        String filePath = basePath + url;
        File file = new File(filePath);
        if (!file.exists()) {
            return html;
        }
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
                    Map<String, Boolean> auth = getComponentAuth(url, element.attr("id"));
                    element.attr("visible", auth.get("visible") + "");
                    element.attr("editable", auth.get("editable") + "");
                }
            }
            html = document.body().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    /**
     * /service/frame/component/auth?url=/frame/url/urls.html
     * url visible:false表示不可见，disabled为true表示只读
     *
     * @return [{"id":"SystemDropDown","visible":true,"disabled":true},{"id":"urlTree","visible":false}]
     * @throws IOException
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> loadAuth(@RequestParam(required = false) Map<String, Object> params,
                                              HttpServletRequest request) throws IOException {
        List<Map<String, Object>> ucObjAuths = new ArrayList<Map<String, Object>>();
        if (params == null) {
            return ucObjAuths;
        }
        String basePath = request.getSession().getServletContext().getRealPath("//");
        String url = (String) params.get("url");
        String path = basePath + url;
        List<String> ids = (List<String>) params.get("ids");
        Map<String, Object> ucAuth = null;
        List<String> ucIds = getUrlComponents(path);
        for (String ucId : ucIds) {
            if (ids != null && ids.size() > 0) { // 权限管理的页面有传递组件id
                ucAuth = new HashMap<String, Object>();
                if (ids.contains(ucId)) {
                    ucAuth.put("id", ucId);
                    ucAuth.putAll(checkComponentAuth(url, ucId));
                    ucObjAuths.add(ucAuth);
                }
            } else {
                ucAuth = new HashMap<String, Object>();
                ucAuth.put("id", ucId);
                ucAuth.putAll(checkComponentAuth(url, ucId));
                ucObjAuths.add(ucAuth);
            }
        }
        return ucObjAuths;
    }

    private List<String> getUrlComponents(String url) {
        List<String> ucIds = new ArrayList<String>();
        /*File file = new File(url);
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
        }*/
        return ucIds;
    }

    @Resource
    private ColaComponentService componentService;

    private Map<String, Boolean> getComponentAuth(String url, String componentId) {
        Map<String, Boolean> authMap = componentService.getComponentAuth(url, componentId);
        return authMap;
    }


    private Map<String, Boolean> checkComponentAuth(String url, String componentId) {
        //读或写均未授权时，默认组件无权限
        Map<String, Boolean> authMap = new HashMap<String, Boolean>();
        authMap = getComponentAuth(url, componentId);
        boolean editable = authMap.get("editable"), visible = authMap.get("visible");
        if (editable) {
            authMap.put("disabled", false);
        } else {
            authMap.put("disabled", true);
        }
        return authMap;
    }

    public String getRootPath() {
        String classPath = this.getClass().getClassLoader().getResource("/").getPath();
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("/", "\\");
        }
        //linux下
        if ("/".equals(File.separator)) {
            rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("\\", "/");
        }
        return rootPath;
    }
}
