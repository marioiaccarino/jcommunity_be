package it.jpanik.dto;

import java.util.StringJoiner;

public class UserSearchDto {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserSearchDto.class.getSimpleName() + "[", "]")
                .add("id='" + userId + "'")
                .toString();
    }
}
