package ir.sharif.math.zahraSoukhtedel.models.viewModels;

import lombok.Getter;

public class ViewUser {

    @Getter
    private final String username;
    @Getter
    private final Integer userId;
    @Getter
    private final boolean isActive;

    public ViewUser(String username, Integer userId, boolean isActive) {
        this.username = username;
        this.userId = userId;
        this.isActive = isActive;
    }
}
