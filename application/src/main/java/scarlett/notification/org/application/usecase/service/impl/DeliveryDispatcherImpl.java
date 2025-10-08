package scarlett.notification.org.application.usecase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.application.usecase.sender.ChannelFactory;
import scarlett.notification.org.application.usecase.sender.SendersChain;
import scarlett.notification.org.application.usecase.service.DeliveryDispatcher;
import scarlett.notification.org.common.model.MessageInformation;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class DeliveryDispatcherImpl implements DeliveryDispatcher {

    private final ChannelFactory factory;

    @Override
    public List<SendersChain> assembleChain(List<MessageInformation> messageInformation) {
        return messageInformation.stream().map(this::generateChainElement).toList();
    }

    private SendersChain generateChainElement(MessageInformation el) {
        SendersChain chain = new SendersChain();
        chain.setMessageInformation(el);
        chain.setSenderAdapter(factory.getSenderAdapter(el.getChannelType()));
        if (Objects.isNull(el.getFallback())) {
            return chain;
        }
        chain.setNext(generateChainElement(el.getFallback()));
        return chain;
    }
}
