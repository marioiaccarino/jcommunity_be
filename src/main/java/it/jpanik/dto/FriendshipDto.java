package it.jpanik.dto;

import it.jpanik.enums.TypeOfFriendship;

import java.util.StringJoiner;

public class FriendshipDto {

    private Long id;

    private UserCommunityDto userCommunityWhoSends;

    private UserCommunityDto userCommunityWhoReceives;

    private TypeOfFriendship typeOfFriendship;

    private boolean isAccepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCommunityDto getUserCommunityWhoSends() {
        return this.userCommunityWhoSends;
    }

    public void setUserCommunityWhoSends(UserCommunityDto userCommunityWhoSends) {
        this.userCommunityWhoSends = userCommunityWhoSends;
    }

    public UserCommunityDto getUserCommunityWhoReceives() {
        return userCommunityWhoReceives;
    }

    public void setUserCommunityWhoReceives(UserCommunityDto userCommunityWhoReceives) {
        this.userCommunityWhoReceives = userCommunityWhoReceives;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public TypeOfFriendship getTypeOfFriendship() {
        return typeOfFriendship;
    }

    public void setTypeOfFriendship(TypeOfFriendship typeOfFriendship) {
        this.typeOfFriendship = typeOfFriendship;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FriendshipDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("userCommunityWhoSends='" +(userCommunityWhoSends!=null ? userCommunityWhoSends.toString() : "null") + "'")
                .add("userCommunityWhoReceives='" +(userCommunityWhoReceives!=null ? userCommunityWhoReceives.toString() : "null") + "'")
                .add("typeOfFriendship='" + typeOfFriendship + "'")
                .add("isAccepted='" + isAccepted + "'")
                .toString();
    }
}
