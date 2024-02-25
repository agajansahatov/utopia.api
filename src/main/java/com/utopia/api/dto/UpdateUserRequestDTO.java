package com.utopia.api.dto;

import com.utopia.api.entities.User;

public class UpdateUserRequestDTO {
    private User oldUser;
    private User updatedUser;

    public User getOldUser() {
        return oldUser;
    }

    public void setOldUser(User oldUser) {
        this.oldUser = oldUser;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }
}