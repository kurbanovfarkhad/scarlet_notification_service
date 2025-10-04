package scarlett.notification.org.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.infra.model.UserPreferenceModel;
import scarlett.notification.org.infra.service.UserPreferenceService;
import scarlett.notification.org.main.sender.SendersChain;
import scarlett.notification.org.main.serialization.SchemaDeserializer;
import scarlett.notification.org.main.service.DeliveryDispatcher;
import scarlett.notification.org.main.service.ProcessingService;
import scarlett.notification.org.main.template.TemplateEngine;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcessingServiceImpl implements ProcessingService {
    private final SchemaDeserializer schemaDeserializer;
    private final UserPreferenceService userPreferenceService;
    private final TemplateEngine templateEngine;
    private final DeliveryDispatcher deliveryDispatcher;

    @Transactional(readOnly = true)
    public void process(byte[] payloadBytes){
        //вызвать сериализатор
        QueuePayload queuePayload = schemaDeserializer.deserializePayload(payloadBytes);
        //вызвать преференсы
        UserPreferenceModel userPreferences = userPreferenceService.getUserPreferences(queuePayload.getUserId());
        //вызвать темплейт энжин
        List<MessageInformation> messageInformation = templateEngine.generateMessage(userPreferences.getDefaultChannelType(), queuePayload);
        //вызвать отправку сообщения
        deliveryDispatcher.assembleChain(messageInformation).forEach(SendersChain::doChain);
    }
}
