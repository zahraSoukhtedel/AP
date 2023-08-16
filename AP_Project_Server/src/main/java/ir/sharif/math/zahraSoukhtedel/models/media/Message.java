package ir.sharif.math.zahraSoukhtedel.models.media;

import ir.sharif.math.zahraSoukhtedel.models.SaveAble;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@ToString
@NoArgsConstructor
public class Message extends Media implements SaveAble {

    @Setter
    @Getter
    @Column
    Integer mainMedia;

    @Setter
    @Getter
    @Column
    boolean isForwardedTweet;

    @Setter
    @Getter
    @Column
    boolean isDeleted;
    //###########################################
    @Setter
    @Getter
    @Column
    boolean isTiming;
    //#####################################3

    public Message(String content, Integer writer, Integer mainMedia, Integer image, boolean isForwardedTweet) {
        super(content, writer, image);
        this.mainMedia = mainMedia;
        this.isForwardedTweet = isForwardedTweet;
        isDeleted = false;
    }

    public Message(String content, Integer writer, Integer mainMedia, Integer image, boolean isForwardedTweet, LocalDateTime dateTime, boolean isTiming) {
        super(content, writer, image);
        this.mainMedia = mainMedia;
        this.isForwardedTweet = isForwardedTweet;
        isDeleted = false;
        //########################################
        this.dateTime = dateTime;
        this.isTiming = isTiming;
        //#####################################
    }



    public static void sortByDateTime(List<Message> messages) {
        Comparator<Message> byDateTime = Comparator.comparing(Message::getDateTime);
        messages.sort(byDateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return message.getId().equals(getId());
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
