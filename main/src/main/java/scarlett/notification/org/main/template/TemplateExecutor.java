package scarlett.notification.org.main.template;

import java.util.Map;

public interface TemplateExecutor {
    String execute(Map<String, String> properties, String template);
}
