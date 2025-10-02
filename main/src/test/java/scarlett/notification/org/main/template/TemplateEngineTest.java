package scarlett.notification.org.main.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.entity.TemplateEntity;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;
import scarlett.notification.org.persistence.repository.EventRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TemplateEngineTest {

    private EventRepository eventRepository = Mockito.mock(EventRepository.class);

    private final TemplateEngine templateEngine =  new TemplateEngine(eventRepository);

    @Test
    void generateMessage() {
        // given
        EventEntity eventEntity = new EventEntity();
        eventEntity.setName("event");
        eventEntity.setSchema("schema");
        eventEntity.setDescription("description");
        TemplateEntity template = new TemplateEntity();
        template.setAllowedChannel(List.of(ChannelType.EMAIL,ChannelType.EMAIL,ChannelType.EMAIL));
        template.setDefaultChannel(ChannelType.EMAIL);
        template.setDeliveryAttempts(3);
        TemplateTranslationEntity translation = new TemplateTranslationEntity();
        translation.setName("translation");
        translation.setBody("translation");
        translation.setSubject("translation");
        translation.setLocale(LocaleEmbeddable.ru);
        template.setTranslations(List.of(translation));
        eventEntity.setTemplates(List.of(template));
        Mockito.when(eventRepository.findByName("event")).thenReturn(Optional.of(eventEntity));

        QueuePayload payload = new QueuePayload();
        payload.setLocale(LocaleEmbeddable.ru);
        payload.setEmail("email");
        payload.setPhoneNumber("123123123");
        payload.setApplicationId("asd");
        payload.setEventName("event");
        payload.setAdditionalProperties(Map.of());
        // when
        List<MessageInformation> event = templateEngine.generateMessage(ChannelType.EMAIL, payload);

        // then
        Assertions.assertNotNull(event);
    }
}