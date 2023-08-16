package ir.sharif.math.zahraSoukhtedel.models.media;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
public abstract class Media {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Setter
    @Getter
    @Column
    String content;

    @Setter
    @Getter
    @Column
    Integer writer;

    @Setter
    @Getter
    @Column
    Integer image;

    @Setter
    @Getter
    @Column
    LocalDateTime dateTime;

    public Media(String content, Integer writer, Integer image) {
        this.content = content;
        this.writer = writer;
        this.image = image;
        dateTime = LocalDateTime.now();
    }
}
