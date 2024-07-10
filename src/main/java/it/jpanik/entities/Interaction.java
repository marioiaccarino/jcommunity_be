package it.jpanik.entities;

import it.jpanik.enums.TypeOfInteraction;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "INTERACTION")
@SequenceGenerator(name = "POST_SEQUENCE_GENERATOR", allocationSize = 1, sequenceName ="POST_SEQ")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE_OF_INTERACTION")
    @Enumerated(EnumType.STRING)
    private TypeOfInteraction typeOfInteraction;

    @Column(name = "DATE_OF_INTERACTION")
    private Date dateOfInteraction;

    @JoinColumn(name = "POST")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Post post;

    @JoinColumn(name = "USER_COMMUNITY")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserCommunity userCommunity;

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

    public Date getDateOfInteraction() {
        return dateOfInteraction;
    }

    public void setDateOfInteraction(Date dateOfInteraction) {
        this.dateOfInteraction = dateOfInteraction;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserCommunity getUserCommunity() {
        return userCommunity;
    }

    public void setUserCommunity(UserCommunity userCommunity) {
        this.userCommunity = userCommunity;
    }
}
