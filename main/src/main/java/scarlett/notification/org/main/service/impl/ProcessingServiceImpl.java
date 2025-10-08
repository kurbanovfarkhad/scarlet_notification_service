package scarlett.notification.org.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.infra.model.UserPreferenceModel;
import scarlett.notification.org.infra.service.UserPreferenceService;
import scarlett.notification.org.main.sender.SendersChain;
import scarlett.notification.org.main.service.DeliveryDispatcher;
import scarlett.notification.org.main.service.ProcessingService;
import scarlett.notification.org.main.template.TemplateEngine;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcessingServiceImpl implements ProcessingService {
    private final UserPreferenceService userPreferenceService;
    private final TemplateEngine templateEngine;
    private final DeliveryDispatcher deliveryDispatcher;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void process(QueuePayload payload) {
        //TODO create notification
        // subject заполнятся позже
        // body заполнится позже
        //
        // TODO deliveryAttempt заполним на этапе sender.send()

        //вызвать преференсы
        UserPreferenceModel userPreferences = userPreferenceService.getUserPreferences(payload.getUserId());
        //вызвать темплейт энжин
        List<MessageInformation> messageInformation = templateEngine.generateMessage(
                userPreferences.getDefaultChannelType(), payload);
        //вызвать отправку сообщения
        List<SendersChain> chains = deliveryDispatcher.assembleChain(messageInformation);
        //отправить исполняться
        chains.forEach(SendersChain::doChain);
    }
}
