package scarlett.notification.org.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.main.sender.ChannelFactory;
import scarlett.notification.org.main.sender.SendersChain;
import scarlett.notification.org.main.service.DeliveryDispatcher;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DeliveryDispatcherImpl implements DeliveryDispatcher {

    private final ChannelFactory factory;

    @Override
    public List<SendersChain> assembleChain(List<MessageInformation> messageInformation) {
        return messageInformation.stream().map(el ->{
            return generateChainElement(el);
        }).toList();
    }

    private SendersChain generateChainElement(MessageInformation el) {
        SendersChain chain = new SendersChain();
        chain.setNext(generateChainElement(el.getFallback()));
        chain.setMessageInformation(el);
        chain.setSenderAdapter(factory.getSenderAdapter(el.getChannelType()));
        return chain;
    }
}
