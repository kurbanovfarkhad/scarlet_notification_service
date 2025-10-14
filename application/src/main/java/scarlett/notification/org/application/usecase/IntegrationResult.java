package scarlett.notification.org.application.usecase;

import lombok.Data;

@Data
public class IntegrationResult {
    private boolean success;
    private String error;
    private String response;
}
