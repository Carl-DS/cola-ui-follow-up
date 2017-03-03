package com.colaui.example.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("cola")
public class LoadHtmlController implements ServletContextAware {

    private static String[] removeTagNames = "script,link".split(",");

    private ServletContext context;

    public void setServletContext(ServletContext servletContext) {
        this.context = servletContext;
    }

    @RequestMapping(value = "/load/html/body", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    public @ResponseBody String loadBody() {
        String filePath = context.getRealPath("cases" + File.separator + "case.html");

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

}
