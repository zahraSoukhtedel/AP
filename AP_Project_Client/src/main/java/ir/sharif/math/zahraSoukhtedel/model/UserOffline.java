package ir.sharif.math.zahraSoukhtedel.model;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.models.viewModels.ViewUser;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserOffline {

    public UserOffline(Client client){
        this.client = client;
    }


    @Getter @Setter
    private List<MessageOffline> newMessages = new ArrayList<>();


    @Getter @Setter
    private String username;

    @Getter @Setter
    private String newPassword;

    @Getter @Setter
    private Client client;

    @Getter @Setter
    private ViewUser viewUser;
    @Getter @Setter
    private List<ChatOffline> loadedChats = new ArrayList<>();
    @Getter @Setter
    private List<GroupOffline> loadedGroups = new ArrayList<>();
    @Getter @Setter
    private String lastSeenType;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private boolean publicData;

    //TODO check
    @Getter @Setter
    private LocalDate lastSeen;




}
