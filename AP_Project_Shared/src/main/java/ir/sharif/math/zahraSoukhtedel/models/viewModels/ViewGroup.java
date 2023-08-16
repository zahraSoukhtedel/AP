package ir.sharif.math.zahraSoukhtedel.models.viewModels;

import lombok.Getter;

public class ViewGroup {
    @Getter
    private final String groupName;
    @Getter
    private final Integer groupId;

    public ViewGroup(String groupName, Integer groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }
}
