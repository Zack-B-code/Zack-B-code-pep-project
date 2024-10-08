package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagebyIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessagebyIDHandler);
        app.patch("/messages/{message_id}", this::updateMessagebyIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesbyUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account added_account = accountService.UserAdd(account);
        if(added_account!=null){
            ctx.json(mapper.writeValueAsString(added_account));
        }else{
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedin_account = accountService.UserLoggedin(account);
        if(loggedin_account!=null){
            ctx.json(mapper.writeValueAsString(loggedin_account));
        }else{
            ctx.status(401);
        }
    }

    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message created_message = messageService.CreateMessage(message);
        if(created_message!=null){
            ctx.json(mapper.writeValueAsString(created_message));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.RetrieveAllMessages();
        ctx.json(messages);
    }

    private void getMessagebyIDHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.RetrieveMessageByMessageId(message_id);
        if (message == null)
        {
            ctx.json("");
        }else{
            ctx.json(message);
        }
    }

    private void deleteMessagebyIDHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.DeleteMessageByMessageId(message_id);
        if (message == null)
        {
            ctx.json("");
        }else{
            ctx.json(message);
        }
    }

    private void updateMessagebyIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updated_message = messageService.UpdateMessage(message_id, message);
        System.out.println(updated_message);
        if(updated_message!=null){
            ctx.json(mapper.writeValueAsString(updated_message));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesbyUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        int posted_by = Integer.parseInt(ctx.pathParam("posted_by"));
        List<Message> usermessages = messageService.RetrieveAllMessageForUser(posted_by, message_id);
        if (usermessages != null)
        {
            ctx.json(mapper.writeValueAsString(usermessages));
        }else{
            ctx.status(200);
        }        
    }
}