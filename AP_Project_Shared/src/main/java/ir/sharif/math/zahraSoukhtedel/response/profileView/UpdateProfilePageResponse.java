package ir.sharif.math.zahraSoukhtedel.response.profileView;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

public class UpdateProfilePageResponse extends Response {
    private final String username;
    private final String avatarString;
    private final String firstname;
    private final String lastname;
    private final String lastSeen;
    private final String bio;
    private final String birthdate;
    private final String email;
    private final String phoneNumber;
    private final String blockString;
    private final String muteString;
    private final String followString;

    public UpdateProfilePageResponse(String username, String avatarString, String firstname, String lastname,
                                     String lastSeen, String bio, String birthdate, String email, String phoneNumber,
                                     String blockString, String muteString, String followString) {
        this.username = username;
        this.avatarString = avatarString;
        this.firstname = firstname;
        this.lastname = lastname;
        this.lastSeen = lastSeen;
        this.bio = bio;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.blockString = blockString;
        this.muteString = muteString;
        this.followString = followString;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateProfilePage(username, avatarString, firstname, lastname, lastSeen, bio, birthdate, email,
                phoneNumber, blockString, muteString, followString);
    }
}
