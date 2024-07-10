package it.jpanik.dto;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class CommunityDto {

    private Long id;

    private String name;

    private List<UserCommunityDto> usersCommunity;

    private boolean isPublic;

    private String urlImage;
    public Long getId() {
        return id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserCommunityDto> getUsersCommunity() {
        return usersCommunity;
    }

    public void setUsersCommunity(List<UserCommunityDto> usersCommunity) {
        this.usersCommunity = usersCommunity;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CommunityDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name.toString() + "'")
                .add("usersCommunity='" + ( usersCommunity!=null ? usersCommunity.toString() : "null") + "'")
                .add("isPublic='" + isPublic + "'")
                .add("urlImage='" + urlImage + "'")
                .toString();
    }
}
