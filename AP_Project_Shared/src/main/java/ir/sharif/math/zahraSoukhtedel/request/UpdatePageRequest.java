package ir.sharif.math.zahraSoukhtedel.request;

import ir.sharif.math.zahraSoukhtedel.response.Response;
import lombok.ToString;

@ToString
public class UpdatePageRequest extends Request{
    private final String pageName;

    public UpdatePageRequest(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.updatePage(pageName);
    }
}
