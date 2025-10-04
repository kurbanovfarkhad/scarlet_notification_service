package scarlett.notification.org.main.sender;

import lombok.Data;
import scarlett.notification.org.common.model.MessageInformation;

@Data
public class SendersChain {
    private SendersChain next;
    private SenderAdapter senderAdapter;
    private MessageInformation messageInformation;

    public SendersChain next(){
        return next;
    };

    public void doChain(){
        // отправить
        senderAdapter.send(this.messageInformation);
        // при ошибке вызвать next
        this.next().doChain();
    }
}
