package it.jpanik.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/*
 * @author Mario Iaccarino
 */
@Entity
@Table(name = "USER_COMMUNITY")
@SequenceGenerator(name = "USER_COMMUNITY_SEQUENCE_GENERATOR", allocationSize = 1, sequenceName = "USER_COMMUNITY_SEQ")
public class UserCommunity {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "REFERRED_USER")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User referredUser;

    @Column(name = "CREATION_DATE")
    private Date CreationDate;

    @JoinColumn(name = "COMMUNITY")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Community subscribedCommunity;

    @JoinColumn(name = "")

    @Column(name = "NICKNAME")
    private String nickName;

    @OneToMany(mappedBy = "userCommunity", cascade = CascadeType.ALL)
    private List<Interaction> interactions;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> publishedPosts;

    @OneToMany(mappedBy = "userCommunityWhoSends", cascade = CascadeType.ALL)
    private List<Friendship> sentFriendshipRequests;

    @OneToMany(mappedBy = "userCommunityWhoReceives", cascade = CascadeType.ALL)
    private List<Friendship> receivedFriendshipRequests;

    public List<Friendship> getSentFriendshipRequests() {
        return sentFriendshipRequests;
    }

    public void setSentFriendshipRequests(List<Friendship> sendedFriendshipRequests) {
        this.sentFriendshipRequests = sendedFriendshipRequests;
    }

    public List<Friendship> getReceivedFriendshipRequests() {
        return receivedFriendshipRequests;
    }

    public void setReceivedFriendshipRequests(List<Friendship> receivedFriendshipRequests) {
        this.receivedFriendshipRequests = receivedFriendshipRequests;
    }

    public String getNickName() {
        return nickName;
    }

    public List<Post> getPublishedPosts() {
        return publishedPosts;
    }

    public void setPublishedPosts(List<Post> publishedPosts) {
        this.publishedPosts = publishedPosts;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> userInteractions) {
        this.interactions = userInteractions;
    }
    public Community getSubscribedCommunity() {
        return subscribedCommunity;
    }

    public void setSubscribedCommunity(Community community) {
        this.subscribedCommunity = community;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReferredUser() {
        return referredUser;
    }

    public void setReferredUser(User referredUser) {
        this.referredUser = referredUser;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date dateOfCreation) {
        this.CreationDate = dateOfCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCommunity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(referredUser, that.referredUser) && Objects.equals(subscribedCommunity, that.subscribedCommunity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referredUser, subscribedCommunity);
    }
}
