package ro.tuc.ds2020.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Message;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {


    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender = :participant1 AND m.receiver = :participant2) " +
            "OR (m.sender = :participant2 AND m.receiver = :participant1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findMessagesBetweenUsers(@Param("participant1") String participant1,
                                           @Param("participant2") String participant2);


    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender = :sender AND m.receiver = :receiver) OR " +
            "(m.sender = :receiver AND m.receiver = :sender) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConversation(@Param("sender") String sender, @Param("receiver") String receiver);


    @Query("SELECT m FROM Message m WHERE " +
            "((m.sender = :sender1 AND m.receiver = :receiver1) OR " +
            "(m.sender = :receiver1 AND m.receiver = :sender1)) OR " +
            "((m.sender = :sender2 AND m.receiver = :receiver2) OR " +
            "(m.sender = :receiver2 AND m.receiver = :sender2)) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findAllBySenderAndReceiverOrReceiverAndSender(
            @Param("sender1") String sender1,
            @Param("receiver1") String receiver1,
            @Param("sender2") String sender2,
            @Param("receiver2") String receiver2);
}
