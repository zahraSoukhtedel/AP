package ir.sharif.math.zahraSoukhtedel.model;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GroupOffline {
    @Getter @Setter
    private ViewGroup viewGroup;
    @Getter @Setter
    private List<MessageOffline> unSendMessages;

    public GroupOffline(ViewGroup viewGroup){
        this.viewGroup = viewGroup;
        this.unSendMessages = new ArrayList<>();
    }
}