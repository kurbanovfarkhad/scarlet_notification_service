package scarlett.notification.org.application.usecase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.application.presentation.crud.model.UserPreferenceModel;
import scarlett.notification.org.application.presentation.crud.service.UserPreferenceService;
import scarlett.notification.org.application.usecase.sender.SendersChain;
import scarlett.notification.org.application.usecase.service.DeliveryDispatcher;
import scarlett.notification.org.application.usecase.service.ProcessingService;
import scarlett.notification.org.application.usecase.template.TemplateEngine;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcessingServiceImpl implements ProcessingService {
    private final UserPreferenceService userPreferenceService;
    private final TemplateEngine templateEngine;
    private final DeliveryDispatcher deliveryDispatcher;

    @Async
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<SendersChain> process(QueuePayload payload) {
        //вызвать преференсы
        UserPreferenceModel userPreferences = userPreferenceService.getUserPreferences(payload.getUserId());
        //вызвать темплейт энжин
        List<MessageInformation> messageInformation = templateEngine.generateMessage(
                userPreferences.getDefaultChannelType(), payload);
        //вызвать отправку сообщения
        List<SendersChain> chains = deliveryDispatcher.assembleChain(messageInformation);
        //отправить исполняться
        return chains;
    }
}
