package ro.tuc.ds2020.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.MessageDTO;
import ro.tuc.ds2020.dtos.MessageDetailsDTO;
import ro.tuc.ds2020.dtos.TypingNotificationDTO;
import ro.tuc.ds2020.services.MessageService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }


    @GetMapping("/conversation")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @RequestParam String sender,
            @RequestParam String receiver) {

        if (sender.isEmpty() || receiver.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<MessageDTO> messages = messageService.getMessagesBetween(sender, receiver);
        return ResponseEntity.ok(messages);
    }


    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendRestMessage(@RequestBody MessageDetailsDTO messageDetailsDTO) {
//        MessageDTO savedMessage = messageService.saveMessage(messageDetailsDTO);
        MessageDTO savedMessage = messageService.processMessage(messageDetailsDTO);

        messagingTemplate.convertAndSendToUser(
                messageDetailsDTO.getReceiver(),
                "/queue/messages",
                savedMessage
        );

        return ResponseEntity.ok(savedMessage);
    }



//    @MessageMapping("/send")
//    public void sendMessage(MessageDetailsDTO messageDetailsDTO) {
//        MessageDTO savedMessage = messageService.saveMessage(messageDetailsDTO);
//        // Send the message to the receiver's private WebSocket queue
//        messagingTemplate.convertAndSendToUser(
//                messageDetailsDTO.getReceiver(),
//                "/queue/messages",
//                savedMessage
//        );
//    }
    @MessageMapping("/send")
    public void sendMessage(MessageDetailsDTO messageDetailsDTO) {
        MessageDTO savedMessage = messageService.saveMessage(messageDetailsDTO);

        messagingTemplate.convertAndSendToUser(
                messageDetailsDTO.getReceiver(),
                "/queue/messages",
                savedMessage
        );

        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
    }


    @MessageMapping("/typing")
    public void handleTypingNotification(TypingNotificationDTO typingMessage) {
        messagingTemplate.convertAndSend(
                "/topic/typing",
                typingMessage
        );
    }

    @PatchMapping("/messages/{id}/read")
    public ResponseEntity<Void> markMessageAsRead(@PathVariable UUID id) {
        messageService.markAsRead(id);
        messagingTemplate.convertAndSend("/topic/message-read", id); // Notifică expeditorul
        return ResponseEntity.noContent().build();
    }






}
