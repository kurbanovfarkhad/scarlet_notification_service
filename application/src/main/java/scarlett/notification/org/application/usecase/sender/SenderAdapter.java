package scarlett.notification.org.application.usecase.sender;

import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.common.model.MessageInformation;

public interface SenderAdapter {
    IntegrationResult send(MessageInformation messageInformation);
}
