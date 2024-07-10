package it.jpanik.dto;

import it.jpanik.enums.TypeOfInteraction;

import java.util.StringJoiner;

public class InteractionDto {

    private Long id;

    private TypeOfInteraction typeOfInteraction;

    private PostDto postDto;

    private UserCommunityDto userCommunity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfInteraction getTypeOfInteraction() {
        return typeOfInteraction;
    }

    public void setTypeOfInteraction(TypeOfInteraction typeOfInteraction) {
        this.typeOfInteraction = typeOfInteraction;
    }

    public PostDto getPostDto() {
        return postDto;
    }

    public void setPostDto(PostDto postDto) {
        this.postDto = postDto;
    }

    public UserCommunityDto getUserCommunity() {
        return userCommunity;
    }

    public void setUserCommunity(UserCommunityDto userCommunity) {
        this.userCommunity = userCommunity;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", InteractionDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("typeOfInteraction='" + typeOfInteraction + "'")
                .add("postDto='" +(postDto!=null ? postDto.toString() : "null") + "'")
                .add("userCommunity='" +(userCommunity!=null ? userCommunity.toString() : "null") + "'")
                .toString();
    }
}
