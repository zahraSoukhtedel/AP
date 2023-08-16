package ir.sharif.math.zahraSoukhtedel.controller.hyperlink;

import ir.sharif.math.zahraSoukhtedel.controller.Client;
import ir.sharif.math.zahraSoukhtedel.models.events.ChatGroupEventType;
import ir.sharif.math.zahraSoukhtedel.models.events.SwitchToProfileType;
import ir.sharif.math.zahraSoukhtedel.request.Request;
import ir.sharif.math.zahraSoukhtedel.request.messagingPage.chatGroupPage.ChatGroupEditRequest;
import ir.sharif.math.zahraSoukhtedel.request.profileView.SwitchToProfilePageRequest;
import ir.sharif.math.zahraSoukhtedel.view.Page;
import ir.sharif.math.zahraSoukhtedel.view.ViewManager;
import ir.sharif.math.zahraSoukhtedel.view.tweets.TweetFXController;
import javafx.scene.control.Hyperlink;

public class HyperlinkController {

    public static void checkHyperlink(String messageText, Hyperlink hyperlink){

        if(messageText.charAt(0) == '&'){
            String username = "";
            for(int i =1;i < messageText.length();i++)
                username+=messageText.charAt(i);

            String finalUsername = username;
            hyperlink.setOnAction(event -> {
                        Request request = new SwitchToProfilePageRequest(SwitchToProfileType.USERNAME, null, finalUsername);
                        Client.getClient().addRequest(request);
                    }
            );
        }
        if(messageText.charAt(0) == '#'){
            String groupChatName = "";
            for(int i =1;i < messageText.length();i++)
                groupChatName+=messageText.charAt(i);

            String finalGroupChatName = groupChatName;
            hyperlink.setOnAction(event -> {
                Request request = new ChatGroupEditRequest(ChatGroupEventType.ADD, finalGroupChatName,Client.getUserOffline().getUsername());
                Client.getClient().addRequest(request);
                    }
            );


        }
        if(messageText.charAt(0) == '$'){
            String tweetName = "";
            for(int i =1;i < messageText.length();i++)
                tweetName+=messageText.charAt(i);
            boolean myTweets = false;
            String finalTweetName = tweetName;
            hyperlink.setOnAction(event -> {
            Page tweetPage = new Page("tweetPage");
            TweetFXController tweetFXController = (TweetFXController) tweetPage.getFxController();

            tweetFXController.setTweetID(Integer.parseInt(finalTweetName));
            tweetFXController.setMyTweets(myTweets);

            ViewManager.getInstance().setPage(tweetPage);
                    }
            );
        }
    }
}
