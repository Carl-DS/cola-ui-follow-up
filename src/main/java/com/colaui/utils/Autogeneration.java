package com.colaui.utils;

import org.apache.commons.lang.StringUtils;

/**
 * MVC 代码生成器
 * Created by Carl on 2017/3/23.
 */
public class Autogeneration {
    private static final String CONTROLLER_TEXT_ = "package com.colaui.system.controller;\n" +
            "\n" +
            "import com.colaui.example.model.{0};\n" +
            "import com.colaui.system.service.{0}Service;\n" +
            "import com.colaui.provider.Page;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.*;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@RestController\n" +
            "@RequestMapping(\"/frame/{1}\")\n" +
            "public class {0}Controller {\n" +
            "\t@Autowired\n" +
            "\tprivate {0}Service {1}Service;\n" +
            "\n" +
            "\t@RequestMapping(value = \"/\", method = RequestMethod.GET)\n" +
            "\tpublic Page<{0}> paging(@RequestParam int pageSize,\n" +
            "\t\t\t@RequestParam int pageNo, @RequestParam(required=false) String contain) {\n" +
            "\t\treturn {1}Service.getPage(pageSize, pageNo, contain);\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(value = \"/\", method = RequestMethod.POST, produces = \"application/json; charset=utf-8\")\n" +
            "\tpublic void save(@RequestBody {0} {1}) {\n" +
            "\t\t{1}Service.save({1});\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(value = \"/{id}/\", method = RequestMethod.DELETE)\n" +
            "\tpublic void delete(@PathVariable(\"id\") {2} id) {\n" +
            "\t\t{1}Service.delete(id);\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(value = \"/\", method = RequestMethod.PUT, produces = \"application/json; charset=utf-8\")\n" +
            "\tpublic void update(@RequestBody {0} {1}) {\n" +
            "\t\t{1}Service.update({1});\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(value = \"/{id}/\", method = RequestMethod.GET)\n" +
            "\tpublic {0} find(@PathVariable(\"id\") {2} id) {\n" +
            "\t\treturn {1}Service.find(id);\n" +
            "\t}\n" +
            "\n" +
            "\t@RequestMapping(value = \"/{from}/{limit}\", method = RequestMethod.GET)\n" +
            "\tpublic List<{0}> find(@PathVariable(\"from\") int from,\n" +
            "\t\t\t@PathVariable(\"limit\") int limit) {\n" +
            "\t\treturn {1}Service.find(from, limit);\n" +
            "\t}\n" +
            "}\n";
    private static final String SERVICE_TEXT_ = "package com.colaui.system.service;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "import com.colaui.example.model.{0};\n" +
            "import com.colaui.provider.Page;\n" +
            "public interface {0}Service {\n" +
            "\tPage<{0}> getPage(int pageSize,int pageNo,String contain);\n" +
            "\tvoid save({0} {1});\n" +
            "\tvoid delete({2} id);\n" +
            "\tvoid update({0} {1});\n" +
            "\t{0} find({2} id);\n" +
            "\tList<{0}> find(int from,int limit);\n" +
            "}\n";
    private static final String SERVICEIMPL_TEXT_ = "package com.colaui.system.service.impl;\n" +
            "\n" +
            "import com.colaui.system.dao.{0}Dao;\n" +
            "import com.colaui.example.model.{0};\n" +
            "import com.colaui.system.service.{0}Service;\n" +
            "import com.colaui.provider.Page;\n" +
            "import org.apache.commons.lang.StringUtils;\n" +
            "import org.hibernate.Criteria;\n" +
            "import org.hibernate.criterion.Criterion;\n" +
            "import org.hibernate.criterion.MatchMode;\n" +
            "import org.hibernate.criterion.Restrictions;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "import org.springframework.transaction.annotation.Transactional;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@Service(\"{1}Service\")\n" +
            "@Transactional\n" +
            "public class {0}ServiceImpl implements {0}Service {\n" +
            "\t@Autowired\n" +
            "\tprivate {0}Dao {1}Dao;\n" +
            "\n" +
            "\tpublic Page<{0}> getPage(int pageSize, int pageNo, String contain) {\n" +
            "\t\tCriteria criteria = {1}Dao.createCriteria();\n" +
            "\t\tif (StringUtils.isNotEmpty(contain)) {\n" +
            "\t\t\tCriterion lastRest= Restrictions.like(\"lastName\", contain, MatchMode.ANYWHERE);\n" +
            "\t\t\tCriterion firstRest= Restrictions.like(\"firstName\", contain, MatchMode.ANYWHERE);\n" +
            "\t\t\tcriteria.add(Restrictions.or(lastRest, firstRest));\n" +
            "\t\t}\n" +
            "\t\treturn {1}Dao.getPage(pageSize, pageNo, criteria);\n" +
            "\t}\n" +
            "\n" +
            "\tpublic void save({0} {1}) {\n" +
            "\t\t{1}Dao.save({1});\n" +
            "\t}\n" +
            "\n" +
            "\tpublic void delete({2} id) {\n" +
            "\t\t{1}Dao.delete(id);\n" +
            "\t}\n" +
            "\n" +
            "\tpublic void update({0} {1}) {\n" +
            "\t\t{1}Dao.update({1});\n" +
            "\t}\n" +
            "\n" +
            "\tpublic {0} find({2} id) {\n" +
            "\t\treturn {1}Dao.get(id);\n" +
            "\t}\n" +
            "\n" +
            "\tpublic List<{0}> find(int from, int limit) {\n" +
            "\t\treturn {1}Dao.find(from, limit);\n" +
            "\t}\n" +
            "\n" +
            "}\n";
    private static final String DAO_TEXT_ = "package com.colaui.system.dao;\n" +
            "\n" +
            "import org.springframework.stereotype.Repository;\n" +
            "\n" +
            "import com.colaui.example.model.{0};\n" +
            "import com.colaui.hibernate.HibernateDao;\n" +
            "\n" +
            "@Repository\n" +
            "public class {0}Dao extends HibernateDao<{0}, Long> {\n" +
            "}\n";


    private static final int PREFIX_INDEX_ = 4; // 需要截取前缀的位数
    private static final String MODEL_ = "ColaCompany"; // {0} 需要替换的model
    // {1} 移除前缀的小写model名作为属性名
    private static final String REMOVE_PREFIX_MODEL_ = StringUtils.lowerCase(MODEL_.substring(PREFIX_INDEX_));
    private static final String MODEL_INDEX_ = "long"; // {2} 需要替换的model主键

    /**
     * {0} 表示全文匹配model名
     * {1} 表示model名去掉前缀后的字符小写
     * {2} 表示model中主键类型
     * @param args
     */
    public static void main(String[] args) {
        getText(CONTROLLER_TEXT_, "CONTROLLER_TEXT_");
        getText(SERVICE_TEXT_, "SERVICE_TEXT_");
        getText(SERVICEIMPL_TEXT_, "SERVICEIMPL_TEXT_");
        getText(DAO_TEXT_, "DAO_TEXT_");
    }

    public static void getText(String text, String label) {
        String result = text.replace("{0}", MODEL_)
                .replace("{1}", REMOVE_PREFIX_MODEL_)
                .replace("{2}", MODEL_INDEX_);
        System.out.println("=================================================");
        System.out.print(label + " 替换后:\n" + result);
    }
}
