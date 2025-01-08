package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.builders.MessageBuilder;
import ro.tuc.ds2020.dtos.MessageDetailsDTO;
import ro.tuc.ds2020.dtos.MessageDTO;
import ro.tuc.ds2020.entities.Message;
import ro.tuc.ds2020.repositories.MessageRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public MessageDTO saveMessage(MessageDetailsDTO messageDetailsDTO) {
        Message message = MessageBuilder.toEntity(messageDetailsDTO);
        Message savedMessage = messageRepository.save(message);
        return MessageBuilder.toMessageDTO(savedMessage);
    }
    public void markAsRead(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setRead(true);
        messageRepository.save(message);
    }

    public MessageDTO processMessage(MessageDetailsDTO messageDetailsDTO) {
        Message message = MessageBuilder.toEntity(messageDetailsDTO);
    //    Message savedMessage = messageRepository.save(message);
        return MessageBuilder.toMessageDTO(message);
    }


    public List<MessageDTO> getMessagesBetween(String participant1, String participant2) {
        List<Message> messages = messageRepository.findAllBySenderAndReceiverOrReceiverAndSender(
                participant1, participant2, participant2, participant1);
        return messages.stream()
                .map(MessageBuilder::toMessageDTO)
                .collect(Collectors.toList());
    }


    public List<MessageDTO> getConversation(String sender, String receiver) {
        List<Message> messages = messageRepository.findConversation(sender, receiver);
        return messages.stream()
                .map(MessageBuilder::toMessageDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(MessageBuilder::toMessageDTO)
                .collect(Collectors.toList());
    }

    public MessageDTO getMessageById(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return MessageBuilder.toMessageDTO(message);
    }

    public MessageDTO updateMessage(UUID id, MessageDetailsDTO messageDetailsDTO) {
        Message existingMessage = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        existingMessage.setSender(messageDetailsDTO.getSender());
        existingMessage.setReceiver(messageDetailsDTO.getReceiver());
        existingMessage.setContent(messageDetailsDTO.getContent());
        existingMessage.setRead(messageDetailsDTO.isRead());
        existingMessage.setTimestamp(messageDetailsDTO.getTimestamp());

        Message updatedMessage = messageRepository.save(existingMessage);
        return MessageBuilder.toMessageDTO(updatedMessage);
    }


    public void deleteMessage(UUID id) {
        if (!messageRepository.existsById(id)) {
            throw new RuntimeException("Message not found");
        }
        messageRepository.deleteById(id);
    }
}
