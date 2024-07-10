package it.jpanik.dto;

import java.util.List;
import java.util.StringJoiner;

public class PostSearchDto {
    private List<Long> usersCommunityId;
    private Long communityId;

    public List<Long> getUsersCommunityId() {
        return usersCommunityId;
    }

    public void setUsersCommunityId(List<Long> usersCommunityId) {
        this.usersCommunityId = usersCommunityId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostSearchDto.class.getSimpleName() + "[", "]")
                .add("userCommunityId='" + usersCommunityId + "'")
                .add("communityId='" + communityId + "'")
                .toString();
    }
}
