package scarlett.notification.org.main.sender;

import lombok.Data;

@Data
public class IntegrationResult {
    private boolean success;
    private String error;
    private String response;
}
