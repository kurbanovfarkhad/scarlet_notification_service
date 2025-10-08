package scarlett.notification.org.application.usecase.template;

import java.util.Map;

public interface TemplateExecutor {
    String execute(
            Map<String, String> properties,
            String template);
}
