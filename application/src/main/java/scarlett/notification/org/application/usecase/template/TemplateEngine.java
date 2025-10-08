package scarlett.notification.org.application.usecase.template;

import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.ChannelType;

import java.util.List;

public interface TemplateEngine {
    List<MessageInformation> generateMessage(
            ChannelType preferredChannel,
            QueuePayload payload);
}
