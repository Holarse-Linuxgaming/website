package de.holarse.web.renderer.attachments.engine;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateConfigurationFactory;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import java.io.File;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FreemarkerTemplateRenderEngine implements TemplateRenderEngine {
    
    @Autowired
    private ServletContext context;
    
    @Override
    public String execute(final String templateFile, final Map<String, Object> properties) throws Exception {
        Configuration cfg = new Configuration(new Version(2,3,28));
        WebappTemplateLoader loader = new WebappTemplateLoader(context, "/WEB-INF/views/");
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.GERMAN);
        
        StringWriter sw = new StringWriter();
        
        final Template template = cfg.getTemplate(templateFile);
        template.process(properties, sw);
        
        return sw.toString();
    }
    
}
