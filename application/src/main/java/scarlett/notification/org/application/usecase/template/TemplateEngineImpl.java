package scarlett.notification.org.application.usecase.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.Priority;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.entity.TemplateEntity;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;
import scarlett.notification.org.persistence.repository.EventRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TemplateEngineImpl implements TemplateEngine {
    private final EventRepository eventRepository;
    private final TemplateExecutor templateExecutor;

    public List<MessageInformation> generateMessage(
            ChannelType preferredChannel,
            QueuePayload payload) {
        //вытащить событие
        Optional<EventEntity> eventEntity = eventRepository.findByName(payload.getEventName());
        if (eventEntity.isPresent()) {
            EventEntity event = eventEntity.get();
            //вытащить шаблон
            return event.getTemplates()
                        .stream()
                        .map(template -> generateMessageInformation(preferredChannel, payload, template))
                        .toList();
        } else {
            throw new IllegalArgumentException("Unknown channel type");
        }
    }

    private MessageInformation generateMessageInformation(
            ChannelType preferredChannel,
            QueuePayload payload,
            TemplateEntity template) {
        TemplateTranslationEntity templateTranslation = template.getTranslations()
                                                                .stream()
                                                                .filter(item -> item.getLocale()
                                                                                    .equals(payload.getLocale()))
                                                                .findAny()
                                                                .orElseThrow();
        // меняем, если возможно, канал доставки
        ChannelType channelType = template.getDefaultChannel();
        if (Objects.nonNull(preferredChannel)
                && template.getAllowedChannel().contains(preferredChannel)) {
            channelType = preferredChannel;
        }
        // fallback стратегия
        ChannelType fallbackChannel = channelType.getFallbackChannel();
        MessageInformation fallback = null;
        if (Objects.nonNull(fallbackChannel) && template.getPriority().equals(Priority.HIGH)) {
            fallback = assembleMessage(template.getDeliveryAttempts(), fallbackChannel, payload,
                                       templateTranslation, null);
        }
        return assembleMessage(template.getDeliveryAttempts(), channelType, payload, templateTranslation, fallback);
    }

    private MessageInformation assembleMessage(
            int maxAttempt,
            ChannelType channelType,
            QueuePayload payload,
            TemplateTranslationEntity templateTranslation,
            MessageInformation fallback) {
        MessageInformation.MessageInformationBuilder messageBuilder = MessageInformation.builder();
        // что отправлять
        messageBuilder.body(templateExecutor.execute(payload.getProperties(), templateTranslation.getBody()));
        messageBuilder.subject(templateExecutor.execute(payload.getProperties(), templateTranslation.getSubject()));
        messageBuilder.channelType(channelType);
        messageBuilder.eventId(payload.getEventId());
        messageBuilder.maxAttempt(maxAttempt);
        messageBuilder.address(switch (channelType) {
            case SMS -> payload.getPhoneNumber();
            case PUSH -> payload.getApplicationId();
            case EMAIL -> payload.getEmail();
        });
        messageBuilder.fallback(fallback);

        return messageBuilder.build();
    }
}
