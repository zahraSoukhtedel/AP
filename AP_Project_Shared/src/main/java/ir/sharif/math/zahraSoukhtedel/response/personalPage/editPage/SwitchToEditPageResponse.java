package ir.sharif.math.zahraSoukhtedel.response.personalPage.editPage;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ResponseVisitor;

import java.time.LocalDate;

public class SwitchToEditPageResponse extends Response {

    private final String username;
    private final String firstname;
    private final String lastname;
    private final String bio;
    private final LocalDate birthdate;
    private final String email;
    private final String phoneNumber;
    private final String lastSeenType;
    private final boolean isPrivate;
    private final boolean isPublicData;

    public SwitchToEditPageResponse(String username, String firstname, String lastname, String bio,
                                  LocalDate birthdate, String email, String phoneNumber, String lastSeenType,
                                  boolean isPrivate, boolean isPublicData) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastSeenType = lastSeenType;
        this.isPrivate = isPrivate;
        this.isPublicData = isPublicData;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.switchToEditPage(username, firstname, lastname, bio, birthdate, email, phoneNumber, lastSeenType,
                isPrivate, isPublicData);
    }
}
