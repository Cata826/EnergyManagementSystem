package ro.tuc.ds2020.dtos.builders;
import ro.tuc.ds2020.dtos.MessageDTO;
import ro.tuc.ds2020.dtos.MessageDetailsDTO;
import ro.tuc.ds2020.entities.Message;


public class MessageBuilder {

    private MessageBuilder() {

    }

    public static MessageDTO toMessageDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getSender(),
                message.getReceiver(),
                message.getContent(),
                message.isRead(),
                message.getTimestamp()
        );
    }


    public static MessageDetailsDTO toMessageDetailsDTO(Message message) {
        return new MessageDetailsDTO(
                message.getId(),
                message.getSender(),
                message.getReceiver(),
                message.getContent(),
                message.isRead(),
                message.getTimestamp()
        );
    }


    public static Message toEntity(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setSender(messageDTO.getSender());
        message.setReceiver(messageDTO.getReceiver());
        message.setContent(messageDTO.getContent());
        message.setRead(messageDTO.isRead());
        message.setTimestamp(messageDTO.getTimestamp());
        return message;
    }


    public static Message toEntity(MessageDetailsDTO messageDetailsDTO) {
        Message message = new Message();
        message.setId(messageDetailsDTO.getId());
        message.setSender(messageDetailsDTO.getSender());
        message.setReceiver(messageDetailsDTO.getReceiver());
        message.setContent(messageDetailsDTO.getContent());
        message.setRead(messageDetailsDTO.isRead());
        message.setTimestamp(messageDetailsDTO.getTimestamp());
        return message;
    }
}
