package ir.sharif.math.zahraSoukhtedel.models.media;

import ir.sharif.math.zahraSoukhtedel.models.SaveAble;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@ToString
@NoArgsConstructor
public class Tweet extends Media implements SaveAble {

    @Setter
    @Getter
    @Column
    private Integer upPost;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> likes;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> comments;

    @Setter
    @Getter
    @Column
    int spamReports;

    public Tweet(String content, Integer writer, Integer upPost, Integer image) {
        super(content, writer, image);
        this.upPost = upPost;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        spamReports = 0;
    }

    public void addLike(Integer user) {
        this.likes.add(user);
    }

    public void removeLike(Integer user) {
        this.likes.remove(user);
    }

    public int getLikeNumbers() {
        return likes.size();
    }

    public static void sortByDateTime(List<Tweet> tweets) {
        Comparator<Tweet> byDateTime = Comparator.comparing(Tweet::getDateTime).reversed();
        tweets.sort(byDateTime);
    }

    public static void sortByLikeNumbers(List<Tweet> tweets) {
        Comparator<Tweet> byDateTime = Comparator.comparing(Tweet::getLikeNumbers).reversed();
        tweets.sort(byDateTime);
    }

    public void addComment(Integer comment) {
        this.comments.add(comment);
    }

    public void reportSpam() {
        spamReports++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return tweet.getId().equals(getId());
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
