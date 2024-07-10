package it.jpanik.dto;

import java.util.List;
import java.util.StringJoiner;

public class UserCommunityDto {

    private Long id;

    private UserDto user;

    private CommunityDto subscribedCommunity;

    private String nickname;

    private List<InteractionDto> interactions;

    private List<PostDto> publishedPosts;

    private List<FriendshipDto> sentFriendshipRequests;

    private List<FriendshipDto> receivedFriendshipRequests;

    public List<FriendshipDto> getSentFriendshipRequests() {
        return sentFriendshipRequests;
    }

    public void setSentFriendshipRequests(List<FriendshipDto> sentFriendshipRequests) {
        this.sentFriendshipRequests = sentFriendshipRequests;
    }

    public List<FriendshipDto> getReceivedFriendshipRequests() {
        return receivedFriendshipRequests;
    }

    public void setReceivedFriendshipRequests(List<FriendshipDto> receivedFriendshipRequests) {
        this.receivedFriendshipRequests = receivedFriendshipRequests;
    }

    public List<InteractionDto> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<InteractionDto> interactions) {
        this.interactions = interactions;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public CommunityDto getSubscribedCommunity() {
        return subscribedCommunity;
    }

    public List<PostDto> getPublishedPosts() {
        return publishedPosts;
    }

    public void setPublishedPosts(List<PostDto> publishedPosts) {
        this.publishedPosts = publishedPosts;
    }
    public void setSubscribedCommunity(CommunityDto subscribedCommunity) {
        this.subscribedCommunity = subscribedCommunity;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserCommunityDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("user='" + user.toString() + "'")
                .add("community='" +(subscribedCommunity !=null ? subscribedCommunity.toString(): "null") + "'")
                .add("nickname='" + nickname + "'")
                .add("interactions='" +(interactions!=null ? interactions.toString(): "null") + "'")
                .add("publishedPosts='" +(publishedPosts!=null ? publishedPosts.toString(): "null") + "'")
                .add("sentFriendshipRequests='" +(sentFriendshipRequests!=null ? sentFriendshipRequests.toString(): "null") + "'")
                .add("receivedFriendshipRequests='" +(receivedFriendshipRequests!=null ? receivedFriendshipRequests.toString(): "null") + "'")

                .toString();
    }
}
