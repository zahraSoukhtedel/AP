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
import java.util.Objects;

@ToString
@Entity(name = "Division")
@NoArgsConstructor
public class Group implements SaveAble {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Getter
    @Column
    private String groupName;

    @Setter
    @Getter
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> users;

    public Group(String groupName) {
        this.groupName = groupName;
        users = new ArrayList<>();
    }
    public void addUser(Integer user) { users.add(user); }

    public void removeUser(Integer user) { users.remove(user); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return group.getGroupName().equals(getGroupName());
    }

    @Override
    public int hashCode() { return Objects.hash(groupName); }
}