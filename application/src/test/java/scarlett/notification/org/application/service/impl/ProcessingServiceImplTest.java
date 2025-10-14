package scarlett.notification.org.application.service.impl;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import scarlett.notification.org.application.presentation.crud.mapper.UserPreferenceMapper;
import scarlett.notification.org.application.presentation.crud.service.UserPreferenceService;
import scarlett.notification.org.application.presentation.crud.service.impl.UserPreferenceServiceImpl;
import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.application.usecase.sender.SenderAdapter;
import scarlett.notification.org.application.usecase.sender.impl.ChannelFactoryImpl;
import scarlett.notification.org.application.usecase.sender.impl.ProxySenderAdapter;
import scarlett.notification.org.application.usecase.sender.impl.PushSenderAdapterImpl;
import scarlett.notification.org.application.usecase.sender.impl.SmsSenderAdapterImpl;
import scarlett.notification.org.application.usecase.service.DeliveryDispatcher;
import scarlett.notification.org.application.usecase.service.impl.DeliveryDispatcherImpl;
import scarlett.notification.org.application.usecase.service.impl.ProcessingServiceImpl;
import scarlett.notification.org.application.usecase.template.TemplateEngine;
import scarlett.notification.org.application.usecase.template.TemplateEngineImpl;
import scarlett.notification.org.application.usecase.template.TemplateExecutorImpl;
import scarlett.notification.org.common.BeanUtils;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.common.model.enums.Priority;
import scarlett.notification.org.integration.serialization.avro.AvroBaseSchemaMapper;
import scarlett.notification.org.integration.serialization.avro.AvroSchemaSerializerImpl;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.entity.TemplateEntity;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;
import scarlett.notification.org.persistence.entity.UserPreferenceEntity;
import scarlett.notification.org.persistence.repository.EventRepository;
import scarlett.notification.org.persistence.repository.UserPreferenceRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

class ProcessingServiceImplTest {

    private final SenderAdapter smsSender = Mockito.mock(SmsSenderAdapterImpl.class);
    private final SenderAdapter pushSender = Mockito.mock(PushSenderAdapterImpl.class);
    private final ProxySenderAdapter proxy = Mockito.mock(ProxySenderAdapter.class);
    private final UserPreferenceRepository userPreferenceRepository = Mockito.mock(UserPreferenceRepository.class);
    private final EventRepository eventRepository = Mockito.mock(EventRepository.class);
    private final UserPreferenceService userPreferenceService = new UserPreferenceServiceImpl(userPreferenceRepository,
                                                                                              new BeanUtils(),
                                                                                              Mappers.getMapper(
                                                                                                      UserPreferenceMapper.class));
    private final TemplateEngine templateEngine = new TemplateEngineImpl(eventRepository, new TemplateExecutorImpl());
    private final DeliveryDispatcher deliveryDispatcher = new DeliveryDispatcherImpl(
            new ChannelFactoryImpl(Map.of("SMS", smsSender, "PUSH", pushSender)));

    private final ProcessingServiceImpl service = new ProcessingServiceImpl(userPreferenceService,
                                                                            templateEngine, deliveryDispatcher);
    private final AvroSchemaSerializerImpl schemaSerializer = new AvroSchemaSerializerImpl(
            Mappers.getMapper(AvroBaseSchemaMapper.class));
    private UUID userId;

    @Test
    public void test() {
        //given
        QueuePayload queuePayload = cretePayload();
        //  eventEntity
        EventEntity eventEntity = new EventEntity();
        eventEntity.setName("event");
        eventEntity.setDescription("description");
        TemplateEntity template = new TemplateEntity();
        template.setAllowedChannel(List.of(ChannelType.EMAIL, ChannelType.EMAIL, ChannelType.EMAIL));
        template.setDefaultChannel(ChannelType.PUSH);
        template.setDeliveryAttempts(3);
        template.setPriority(Priority.HIGH);
        TemplateTranslationEntity translation = new TemplateTranslationEntity();
        translation.setName("translation");
        translation.setBody("{{message}}");
        translation.setSubject("{{subject}}");
        translation.setLocale(LocaleEmbeddable.ru);
        template.setTranslations(List.of(translation));
        eventEntity.setTemplates(List.of(template));
        Mockito.when(eventRepository.findByName(any())).thenReturn(Optional.of(eventEntity));
        UserPreferenceEntity userPreference = new UserPreferenceEntity();
        userPreference.setUserId(userId);
        userPreference.setDefaultChannelType(ChannelType.PUSH);
        Mockito.when(userPreferenceRepository.findByUserId(any())).thenReturn(Optional.of(userPreference));
        IntegrationResult succeed = new IntegrationResult();
        succeed.setSuccess(true);
        Mockito.when(smsSender.send(any())).thenReturn(succeed);
        IntegrationResult failed = new IntegrationResult();
        failed.setSuccess(false);
        Mockito.when(pushSender.send(any())).thenReturn(failed);
        //when
        service.process(queuePayload);
        //then
        Mockito.verify(eventRepository, Mockito.times(1)).findByName(any());

    }

    private QueuePayload cretePayload() {
        QueuePayload payload = new QueuePayload();
        payload.setProperties(Map.of("message", "Hello World", "subject", "Title"));
        payload.setApplicationId("applicationId");
        payload.setEmail("email");
        payload.setEventName("eventName");
        payload.setPhoneNumber("phoneNumber");
        userId = UUID.randomUUID();
        payload.setUserId(userId);
        payload.setLocale(LocaleEmbeddable.ru);
        return payload;
    }


}