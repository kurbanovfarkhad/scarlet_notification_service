package scarlett.notification.org.application.usecase.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Component
public class TemplateExecutorImpl implements TemplateExecutor {
    @Override
    public String execute(
            Map<String, String> properties,
            String template) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile(new StringReader(template), "template");

        StringWriter writer = new StringWriter();
        m.execute(writer, properties);
        return writer.toString();
    }
}
