package it.jpanik.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
 * @author Mario Iaccarino
 */
@Entity
@Table(name = "POST")
@SequenceGenerator(name = "POST_SEQUENCE_GENERATOR", allocationSize = 1, sequenceName = "POST_SEQ")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "URL_IMAGE")
    private String urlImage;

    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Interaction> interactions;

    @JoinColumn(name = "AUTHOR")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserCommunity author;

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

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> postInteractions) {
        this.interactions = postInteractions;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public UserCommunity getAuthor() {
        return author;
    }

    public void setAuthor(UserCommunity author) {
        this.author = author;
    }
}
