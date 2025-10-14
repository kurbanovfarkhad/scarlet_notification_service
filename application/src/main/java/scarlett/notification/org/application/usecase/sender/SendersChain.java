package scarlett.notification.org.application.usecase.sender;

import lombok.Data;
import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.common.model.MessageInformation;

import java.util.Objects;

@Data
public class SendersChain implements Comparable<SendersChain> {
    private SendersChain next;
    private ChannelFactory current;
    private MessageInformation messageInformation;

    public SendersChain next() {
        return next;
    }

    public void doChain() {
        // отправить
        IntegrationResult result = current.getSenderAdapter(messageInformation.getChannelType())
                                          .send(this.messageInformation);
        if (Objects.nonNull(result) && !result.isSuccess()) {
            this.next().doChain();
        }
    }

    @Override
    public int compareTo(SendersChain o) {
        return Integer.compare(this.messageInformation.getPriority().getPriority(),
                               o.getMessageInformation().getPriority().getPriority());
    }
}
