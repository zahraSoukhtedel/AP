package ir.sharif.math.zahraSoukhtedel.model;

import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewMessage;
import lombok.Getter;
import lombok.Setter;

public class MessageOffline {
    public MessageOffline(ViewMessage viewMessage){
        this.viewMessage = viewMessage;
    }

    @Getter @Setter
    private ViewMessage viewMessage;
    @Getter @Setter
    private String imageAddress;
}