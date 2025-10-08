package scarlett.notification.org.main.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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