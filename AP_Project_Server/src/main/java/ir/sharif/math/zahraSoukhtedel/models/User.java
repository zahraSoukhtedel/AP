package ir.sharif.math.zahraSoukhtedel.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Entity
@NoArgsConstructor
public class User implements SaveAble {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Setter
    @Getter
    private String firstname;
    @Column
    @Setter
    @Getter
    private String lastname;
    @Column
    @Setter
    @Getter
    private String username;
    @Column
    @Setter
    @Getter
    private String bio;
    @Column
    @Setter
    @Getter
    private LocalDate birthDate;
    @Column
    @Setter
    @Getter
    private String email;
    @Column
    @Setter
    @Getter
    private String phoneNumber;
    @Column
    @Setter
    @Getter
    private String password;
    @Column
    @Setter
    @Getter
    private boolean publicData;
    @Column
    @Setter
    @Getter
    private boolean isActive;
    @Column
    @Setter
    @Getter
    private LocalDateTime lastSeen;
    @Column
    @Setter
    @Getter
    private String lastSeenType;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> followings;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> followers;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> blockList;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> tweets;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> requestNotifications;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> likedTweets;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> requests;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> notifications;

    @Setter
    @Getter
    private boolean isPrivate;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> groups;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> mutedUsers;

    @Setter
    @Getter
    private Integer avatar;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> reportedSpamTweets;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> chatStates;

    public User(String username, String firstname, String lastname, String bio, LocalDate birthDate, String email, String phoneNumber, String password, boolean publicData, String lastSeenType, Integer avatarID) {
        //get from user.
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.birthDate = birthDate;

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.publicData = publicData;
        this.lastSeenType = lastSeenType;
        this.avatar = avatarID;


        //fill them by default.
        this.isActive = true;
        this.isPrivate = false;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.blockList = new ArrayList<>();
        this.lastSeen = LocalDateTime.now();
        this.groups = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.likedTweets = new ArrayList<>();
        this.requestNotifications = new ArrayList<>();

        this.requests = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.mutedUsers = new ArrayList<>();
        this.reportedSpamTweets = new ArrayList<>();

        this.chatStates = new ArrayList<>();
    }

    public void addToLikedTweets(Integer tweet) {
        this.likedTweets.add(tweet);
    }

    public void removeFromTweets(Integer tweet) {
        this.tweets.remove(tweet);
    }

    public void removeFromLikedTweets(Integer tweet) {
        this.likedTweets.remove(tweet);
    }

    public void addToTweets(Integer tweet) {
        this.tweets.add(tweet);
    }

    public void addToFollowings(Integer user) {
        followings.add(user);
    }

    public void removeFromFollowings(Integer user) {
        followings.remove(user);
    }

    public void addToFollowers(Integer user) {
        followers.add(user);
    }

    public void removeFromFollowers(Integer user) {
        followers.remove(user);
    }

    public void addToBlocklist(Integer user) {
        blockList.add(user);
    }

    public void removeFromBlocklist(Integer user) {
        blockList.remove(user);
    }

    public void addToRequestNotifications(String content) {
        requestNotifications.add(content);
        if (requestNotifications.size() > 10)
            requestNotifications.remove(0);
    }

    public void addToNotifications(String content) {
        notifications.add(content);
        if (notifications.size() > 10)
            notifications.remove(0);
    }

    public void addToRequests(Integer requester) {
        requests.add(requester);
    }

    public void removeFromRequests(Integer requester) {
        requests.remove(requester);
    }

    public void removeGroup(Integer group) {
        groups.remove(group);
    }

    public void addGroup(Integer group) {
        groups.add(group);
    }

    public void addToMutedUsers(Integer user) {
        mutedUsers.add(user);
    }

    public void removeFromMutedUsers(Integer user) {
        mutedUsers.remove(user);
    }

    public void removeFromRequestNotifications(String requestNotification) {
        requestNotifications.remove(requestNotification);
    }

    public void removeFromNotifications(String notification) {
        notifications.remove(notification);
    }

    public void addToReportedSpamTweets(Integer tweet) {
        reportedSpamTweets.add(tweet);
    }

    public void addChatState(Integer chatState) {
        chatStates.add(chatState);
    }

    public void removeFromChatStates(Integer chatState) {
        chatStates.remove(chatState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
