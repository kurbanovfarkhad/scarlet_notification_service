package scarlett.notification.org.application.usecase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.application.usecase.sender.SenderAdapter;
import scarlett.notification.org.application.usecase.sender.SendersChain;
import scarlett.notification.org.application.usecase.service.DeliveryDispatcher;
import scarlett.notification.org.common.model.MessageInformation;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class DeliveryDispatcherImpl implements DeliveryDispatcher {

    private final SenderAdapter adapter;

    @Override
    public List<SendersChain> assembleChain(List<MessageInformation> messageInformation) {
        return messageInformation.stream().map(this::generateChainElement).toList();
    }

    private SendersChain generateChainElement(MessageInformation message) {
        SendersChain chain = new SendersChain();
        chain.setMessageInformation(message);
        chain.setCurrent(adapter);
        if (Objects.isNull(message.getFallback())) {
            return chain;
        }
        chain.setNext(generateChainElement(message.getFallback()));
        return chain;
    }
}
