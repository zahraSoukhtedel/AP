package ir.sharif.math.zahraSoukhtedel.request.enterPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

import java.time.LocalDate;

@ToString
public class RegisterRequest extends Request {
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String bio;
    private final LocalDate birthDate;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final boolean publicData;
    private final String lastSeenType;

    public RegisterRequest(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                           String email, String phoneNumber, String password, boolean publicData, String lastSeenType) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.publicData = publicData;
        this.lastSeenType = lastSeenType;
    }


    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.register(username, firstname, lastname, bio, birthDate, email, phoneNumber,
                password, publicData, lastSeenType);
    }
}
