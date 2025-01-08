package ro.tuc.ds2020.dtos;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for messages, with HATEOAS support.
 */
public class MessageDTO extends RepresentationModel<MessageDTO> {

    private UUID id;

    @NotNull
    private String sender; // Sender's unique identifier (e.g., user ID or "admin")

    @NotNull
    private String receiver; // Receiver's unique identifier (e.g., user ID or "admin")

    @NotNull
    private String content; // The actual message text

    @NotNull
    private boolean read; // Whether the message has been read

    @NotNull
    private LocalDateTime timestamp; // Timestamp of the message

    // Default constructor
    public MessageDTO() {
    }

    // All-fields constructor
    public MessageDTO(UUID id, String sender, String receiver, String content, boolean read, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.read = read;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return read == that.read &&
                Objects.equals(id, that.id) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(receiver, that.receiver) &&
                Objects.equals(content, that.content) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver, content, read, timestamp);
    }

    // Override toString for debugging purposes
    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", read=" + read +
                ", timestamp=" + timestamp +
                '}';
    }
}
