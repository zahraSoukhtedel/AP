package ir.sharif.math.zahraSoukhtedel.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@NoArgsConstructor
public class ChatState implements SaveAble{
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Setter
    @Getter
    @Column
    Integer chat;

    @Setter
    @Getter
    @Column
    LocalDateTime lastCheck;

    public ChatState(Integer chat) {
        this.chat = chat;
        this.lastCheck = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatState chatState = (ChatState) o;
        return chatState.getChat().equals(getChat());
    }
}
