package ro.tuc.ds2020.dtos;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class MessageDetailsDTO {

    private UUID id;

    @NotNull
    @Size(min = 1, message = "Sender cannot be empty")
    private String sender;

    @NotNull
    @Size(min = 1, message = "Receiver cannot be empty")
    private String receiver;

    @NotNull
    @Size(min = 1, message = "Message content cannot be empty")
    private String content;
    @NotNull
    private boolean read;

    @NotNull
    private LocalDateTime timestamp;

    public MessageDetailsDTO() {

    }

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

    public MessageDetailsDTO(String sender, String receiver, String content, boolean read, LocalDateTime timestamp) {
        this(null, sender, receiver, content, read, timestamp);
    }

    public MessageDetailsDTO(UUID id, String sender, String receiver, String content, boolean read, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.read = read;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDetailsDTO that = (MessageDetailsDTO) o;
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

    @Override
    public String toString() {
        return "MessageDetailsDTO{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", read=" + read +
                ", timestamp=" + timestamp +
                '}';
    }
}
