package ir.sharif.math.zahraSoukhtedel.request.personalPage.editPage;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class UpdateProfilePageRequest extends Request {
    private final Integer userToBeVisited;

    public UpdateProfilePageRequest(Integer userToBeVisited) {
        this.userToBeVisited = userToBeVisited;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updateProfilePage(userToBeVisited);
    }
}
