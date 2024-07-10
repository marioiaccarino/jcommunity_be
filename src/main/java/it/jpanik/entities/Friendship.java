package it.jpanik.entities;

import it.jpanik.enums.TypeOfFriendship;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "FRIENDSHIP_SEQUENCE_GENERATOR", allocationSize = 1, sequenceName = "FRIENDSHIP_SEQ")
@Table(name = "FRIENDSHIP")
public class Friendship {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

     @JoinColumn(name = "USERCOMMUNITY_WHO_SENDS")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserCommunity userCommunityWhoSends;

     @JoinColumn(name = "USERCOMMUNITY_WHO_RECEIVE")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserCommunity userCommunityWhoReceives;

    @Column(name = "IS_ACCEPTED")
    private boolean isAccepted;

    @Column(name="DATE_OF_FRIENDSHIP")
    private Date dateOfFriendship;

    @Column(name = "TYPE_OF_FRIENDSHIP")
    @Enumerated(EnumType.STRING)
    private TypeOfFriendship typeOfFriendship;

    public TypeOfFriendship getTypeOfFriendship() {
        return typeOfFriendship;
    }

    public void setTypeOfFriendship(TypeOfFriendship typeOfFriendship) {
        this.typeOfFriendship = typeOfFriendship;
    }

    public UserCommunity getUserCommunityWhoSends() {
        return userCommunityWhoSends;
    }

    public void setUserCommunityWhoSends(UserCommunity userCommunityWhoSends) {
        this.userCommunityWhoSends = userCommunityWhoSends;
    }

    public UserCommunity getUserCommunityWhoReceives() {
        return userCommunityWhoReceives;
    }

    public void setUserCommunityWhoReceives(UserCommunity userCommunityWhoReceives) {
        this.userCommunityWhoReceives = userCommunityWhoReceives;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Date getDateOfFriendship() {
        return dateOfFriendship;
    }

    public void setDateOfFriendship(Date dateOfFriendship) {
        this.dateOfFriendship = dateOfFriendship;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
