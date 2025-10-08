package scarlett.notification.org.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.main.sender.ChannelFactory;
import scarlett.notification.org.main.sender.SendersChain;
import scarlett.notification.org.main.service.DeliveryDispatcher;

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
