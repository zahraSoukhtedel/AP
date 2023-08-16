package ir.sharif.math.zahraSoukhtedel.controller.personalPage.listsPage;

import ir.sharif.math.zahraSoukhtedel.controller.ClientHandler;
import ir.sharif.math.zahraSoukhtedel.exceptions.DatabaseDisconnectException;
import ir.sharif.math.zahraSoukhtedel.database.Connector;
import ir.sharif.math.zahraSoukhtedel.models.Group;
import ir.sharif.math.zahraSoukhtedel.models.User;
import ir.sharif.math.zahraSoukhtedel.response.Response;
import ir.sharif.math.zahraSoukhtedel.response.ShowErrorResponse;
import ir.sharif.math.zahraSoukhtedel.response.personalPage.listsPage.CreateGroupResponse;
import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class NewGroupController {
    private final ClientHandler clientHandler;
    static private final Logger logger = LogManager.getLogger(NewGroupController.class);

    public NewGroupController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response createGroup(String groupName) {
        try {
            User user = clientHandler.getUser();
            List<Integer> userGroups = user.getGroups();
            for (Integer id : userGroups)
                if(Connector.getInstance().fetch(Group.class, id).getGroupName().equals(groupName)) {
                    logger.info(String.format("user %s wants to make a group which already exists.", user.getUsername()));
                    return new CreateGroupResponse(Config.getConfig("serverConfig").getProperty(Config.class,"newGroupPage").
                            getProperty("alreadyExistedGroupError"));
                }

            Group group = new Group(groupName);
            Connector.getInstance().save(group);
            user.addGroup(group.getId());
            group.addUser(user.getId());
            Connector.getInstance().save(user);
            Connector.getInstance().save(group);

            return new CreateGroupResponse("");
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("serverConfig").getProperty(Config.class,"server").getProperty("databaseDisconnectError"));
        }
    }
}
