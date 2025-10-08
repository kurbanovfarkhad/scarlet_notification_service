package scarlett.notification.org.main.sender;

import lombok.Data;
import scarlett.notification.org.common.model.MessageInformation;

import java.util.Objects;

@Data
public class SendersChain implements Runnable, Comparable<SendersChain> {
    private SendersChain next;
    private SenderAdapter senderAdapter;
    private MessageInformation messageInformation;

    public SendersChain next() {
        return next;
    }

    public void doChain() {
        // отправить
        IntegrationResult result = senderAdapter.send(this.messageInformation);
        if (Objects.nonNull(result) && !result.isSuccess()) {
            this.next().doChain();
            if (messageInformation.decreaseAttempts() < 0) {
                throw new RuntimeException("Failed to send message");
            }
        }

    }

    @Override
    public int compareTo(SendersChain o) {
        return Integer.compare(this.messageInformation.getPriority().getPriority(),
                               o.getMessageInformation().getPriority().getPriority());
    }

    @Override
    public void run() {
        this.doChain();
    }
}
