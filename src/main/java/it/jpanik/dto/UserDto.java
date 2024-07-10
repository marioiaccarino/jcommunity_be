package it.jpanik.dto;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author Mario Iaccarino
 */
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String urlImage;
    private List<UserCommunityDto> usersCommunity;
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<UserCommunityDto> getUsersCommunity() {
        return usersCommunity;
    }

    public UserDto() {}

    public void setUsersCommunity(List<UserCommunityDto> usersCommunity) {
        this.usersCommunity = usersCommunity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + firstName + "'")
                .add("surname='" + lastName + "'")
                .add("urlImage='" + urlImage + "'")
                .add("usersCommunity='" + (usersCommunity!=null ? usersCommunity.toString() : "null") + "'")
                .add("email='" + email + "'")
                .add("password='" + '*' + "'")
                .toString();
    }
}
