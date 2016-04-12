package com.colaui.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cola")
public class LoadHtmlController {

	private static String[] removeTagNames = "script,link".split(",");

	@RequestMapping(value = "/load-body", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	public @ResponseBody
	String loadBody() {
		// if (filePath == null) {
		String filePath = "/Users/alex/Git/cola-frame/public/case1.html";
		// }
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
			String html = document.body().html();
			System.out.println(html);
			return html;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public Map<String, String> load(
			@RequestParam(required = false) String filePath) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ia", "dorado");
		return map;
	}
}
