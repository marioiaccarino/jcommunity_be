package it.jpanik.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class PostDto {

    private Long id;

    private String title;
    
    private String description;

    private String urlImage;

    private UserCommunityDto author;

    private List<InteractionDto> interactions = new ArrayList<>();

    public UserCommunityDto getAuthor() {
        return author;
    }

    public void setAuthor(UserCommunityDto author) {
        this.author = author;
    }

    public List<InteractionDto> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<InteractionDto> interactions) {
        this.interactions = interactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("title='" + title + "'")
                .add("description='" + description + "'")
                .add("author='" +(author!=null ? author.toString() : "null") + "'")
                .add("urlImage='" + urlImage + "'")
                .add("title='" + title + "'")
                .add("interactions='" +(interactions!=null ? interactions.toString() : "null") + "'")
                .toString();
    }
}
