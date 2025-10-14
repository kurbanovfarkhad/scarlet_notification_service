package scarlett.notification.org.application.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import scarlett.notification.org.application.usecase.template.TemplateEngineImpl;
import scarlett.notification.org.application.usecase.template.TemplateExecutorImpl;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.common.model.enums.SerializationType;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.entity.TemplateEntity;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;
import scarlett.notification.org.persistence.repository.EventRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class TemplateEngineTest {

    private final EventRepository eventRepository = Mockito.mock(EventRepository.class);

    private final TemplateEngineImpl templateEngine = new TemplateEngineImpl(eventRepository,
                                                                             new TemplateExecutorImpl());

    @Test
    void generateMessage() {
        // given
        EventEntity eventEntity = new EventEntity();
        eventEntity.setName("event");
        eventEntity.setSchemaType(SerializationType.AVRO);
        eventEntity.setDescription("description");
        TemplateEntity template = new TemplateEntity();
        template.setAllowedChannel(List.of(ChannelType.EMAIL, ChannelType.EMAIL, ChannelType.EMAIL));
        template.setDefaultChannel(ChannelType.EMAIL);
        template.setDeliveryAttempts(3);
        TemplateTranslationEntity translation = new TemplateTranslationEntity();
        translation.setName("translation");
        translation.setBody("translation");
        translation.setSubject("translation");
        translation.setLocale(LocaleEmbeddable.ru);
        template.setTranslations(List.of(translation));
        eventEntity.setTemplates(List.of(template));
        Mockito.when(eventRepository.findByName(any())).thenReturn(Optional.of(eventEntity));

        QueuePayload payload = new QueuePayload();
        payload.setLocale(LocaleEmbeddable.ru);
        payload.setEmail("email");
        payload.setPhoneNumber("123123123");
        payload.setApplicationId("asd");
        payload.setEventName("event");
        payload.setProperties(Map.of());
        // when
        List<MessageInformation> event = templateEngine.generateMessage(ChannelType.EMAIL, payload);

        // then
        Assertions.assertNotNull(event);
    }
}