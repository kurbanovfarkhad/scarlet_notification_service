package scarlett.notification.org.application.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scarlett.notification.org.application.usecase.template.TemplateExecutorImpl;

import java.util.Map;

class TemplateExecutorImplTest {

    @Test
    void execute() {
        TemplateExecutorImpl templateExecutor = new TemplateExecutorImpl();
        String result = templateExecutor.execute(Map.of("name", "Test"), """
                {{name}}
                """);
        Assertions.assertEquals("Test", result.trim());
    }
}