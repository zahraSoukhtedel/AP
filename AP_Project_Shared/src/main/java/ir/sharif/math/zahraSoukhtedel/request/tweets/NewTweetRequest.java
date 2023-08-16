package ir.sharif.math.zahraSoukhtedel.request.tweets;

import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.RequestVisitor;
import ir.sharif.math.zahraSoukhtedel.response.Response;

public class NewTweetRequest extends Request {
    private final String content;
    private final String avatarString;
    private final Integer upPost;

    public NewTweetRequest(String content, String avatarString, Integer upPost) {
        this.content = content;
        this.avatarString = avatarString;
        this.upPost = upPost;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.newTweet(content, avatarString, upPost);
    }

    @Override
    public String toString() {
        return "NewTweetRequest{" +
                "content='" + content + '\'' +
                ", upPost=" + upPost +
                '}';
    }
}
