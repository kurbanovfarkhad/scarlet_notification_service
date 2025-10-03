package scarlett.notification.org.main.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.persistence.entity.EventEntity;
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

    public List<MessageInformation> generateMessage(ChannelType preferredChannel, QueuePayload qpayload) {
        QueuePayload payload = new QueuePayload();
        //вытащить событие
        Optional<EventEntity> eventEntity = eventRepository.findByName(payload.getEventName());
        if (eventEntity.isPresent()) {
            EventEntity event = eventEntity.get();

            //вытащить шаблон
            return event.getTemplates()
                        .stream()
                        .map(el -> {
                            MessageInformation.MessageInformationBuilder messageBuilder = MessageInformation.builder();
                            ChannelType channelType =  el.getDefaultChannel();
                            if (Objects.nonNull(preferredChannel)
                                    && el.getAllowedChannel().contains(preferredChannel)) {
                                channelType = preferredChannel;
                            }
                            //куда отправлять
                            String address = switch (channelType) {
                                case SMS -> payload.getPhoneNumber();
                                case PUSH -> payload.getApplicationId();
                                case EMAIL -> payload.getEmail();
                            };
                            TemplateTranslationEntity templateTranslation = el.getTranslations()
                                                                              .stream()
                                                                              .filter(item -> item.getLocale()
                                                                                                  .equals(payload.getLocale()))
                                                                              .findAny()
                                                                              .orElseThrow();
                            messageBuilder.channelType(channelType);
                            messageBuilder.address(address);
                            // что отправлять
                            messageBuilder.body(templateExecutor.execute(payload.getProperties(), templateTranslation.getBody()));
                            messageBuilder.subject(templateExecutor.execute(payload.getProperties(), templateTranslation.getSubject()));

                            return messageBuilder.build();
                        })
                        .toList();
        } else {
            throw new IllegalArgumentException("Unknown channel type");
        }
    }
}
