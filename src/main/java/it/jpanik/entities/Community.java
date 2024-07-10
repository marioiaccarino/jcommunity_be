package it.jpanik.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "COMMUNITY_SEQUENCE_GENERATOR", allocationSize = 1, sequenceName = "COMMUNITY_SEQ")
@Table(name = "COMMUNITY")
public class Community {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @OneToMany(mappedBy = "subscribedCommunity", cascade = CascadeType.ALL)
    private List<UserCommunity> userCommunityList;

    @Column(name = "LOGO")
    private String urlImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date dateOfCreation) {
        this.creationDate = dateOfCreation;
    }

    public List<UserCommunity> getUserCommunityList() {
        return userCommunityList;
    }

    public void setUserCommunityList(List<UserCommunity> users) {
        this.userCommunityList = users;
    }
}
