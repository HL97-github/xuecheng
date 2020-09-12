package com.xuecheng.test.freemarker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2020/9/12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class testGenerateHtml {

    //基于模板生成静态化文件
    @Test
    public void testCreateHtml() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        String classPath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classPath + "/templates/"));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //加载模板
        Template template = configuration.getTemplate("test1.ftl");
        //设置数据模型
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name","zhangsan");
        //静态化内容
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        InputStream inputStream = IOUtils.toInputStream(content);
        //输出文件
        FileOutputStream outputStream=new FileOutputStream(new File("d:/test1.html"));
        //输入流对接输出流
        IOUtils.copy(inputStream,outputStream);
    }

    @Test
    public void testCreateHtmlByString() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //获取模板内容
        //模板内容，这里测试时使用简单的字符串作为模板
        String templateString="" +
                "<html>\n" +
                "    <head></head>\n" +
                "    <body>\n" +
                "    名称：${name}\n" +
                "    </body>\n" +
                "</html>";
        //加载模板
        //模板加载器
        StringTemplateLoader templateLoader=new StringTemplateLoader();
        templateLoader.putTemplate("template",templateString);
        configuration.setTemplateLoader(templateLoader);
        Template template = configuration.getTemplate("template", "utf-8");
        //设置数据模型
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name","zhangsan");
        //静态化内容
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        InputStream inputStream = IOUtils.toInputStream(content);
        //输出文件
        FileOutputStream outputStream=new FileOutputStream(new File("d:/test1.html"));
        //输入流对接输出流
        IOUtils.copy(inputStream,outputStream);
    }
}
