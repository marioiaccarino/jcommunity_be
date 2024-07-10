package it.jpanik.dto;

public class UserCommunityCreateDto {
    public String nickname;
    public Long communityId;
    public Long loggedUserId;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(Long loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    @Override
    public String toString() {
        return "UserCommunityCreateDto{" +
                "nickname='" + nickname + '\'' +
                ", communityId=" + communityId +
                ", loggedUserId=" + loggedUserId +
                '}';
    }
}
