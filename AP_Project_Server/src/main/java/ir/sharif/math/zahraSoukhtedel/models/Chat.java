package ir.sharif.math.zahraSoukhtedel.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
public class Chat implements SaveAble {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Getter
    @Column
    String chatName;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Integer> messages;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Integer> users;

    @Setter
    @Getter
    @Column
    boolean isGroup;

    public Chat(String chatName, boolean isGroup) {
        this.chatName = chatName;
        this.isGroup = isGroup;
        this.users = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void addUser(Integer userID) {
        users.add(userID);
    }

    public void addMessage(Integer messageID) {
        messages.add(messageID);
    }

    public void removeMessage(Integer messageID) {
        messages.remove(messageID);
    }

    public void removeUser(Integer userID) {
        users.remove(userID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return chat.getId().equals(getId());
    }
}
