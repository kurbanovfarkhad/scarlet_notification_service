package scarlett.notification.org.application.usecase.sender;

import scarlett.notification.org.common.model.MessageInformation;

@FunctionalInterface
public interface SenderAdapter {
    IntegrationResult send(MessageInformation messageInformation);
}
