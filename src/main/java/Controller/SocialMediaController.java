package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

public class SocialMediaController {
    AccountService accService;
    MessageService msgService;

    public SocialMediaController() {
        accService = new AccountService();
        msgService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postHandler);
        app.get("/messages", this::allMessagesHandler);
        app.get("/messages/{message_id}", this::messageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMsgByIdHandler);
        app.patch("/messages/{message_id}", this::updateMsgByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMsgByUID);
        return app;
    }

    private void registrationHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account newAcc = om.readValue(context.body(), Account.class);

        if((newAcc.getUsername() != "" && newAcc.getPassword().length() > 3) && accService.registration(newAcc) != null)
            context.json(om.writeValueAsString(newAcc));
        else
            context.status(400);
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(context.body(), Account.class);

        if(accService.login(acc) != null)
            context.json(om.writeValueAsString(acc));
        else
            context.status(401);
    }

    private void postHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message newMsg = om.readValue(context.body(), Message.class);

        if((newMsg.getMessage_text().length() != 0 && newMsg.getMessage_text().length() <= 255) && msgService.createMessage(newMsg) != null)
            context.json(om.writeValueAsString(newMsg));
        else
            context.status(400);
    }

    private void allMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        List<Message> messages = msgService.getAllMessages();

        context.json(om.writeValueAsString(messages));
    }

    private void messageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message msg = msgService.getMessage(Integer.parseInt(context.pathParam("message_id")));

        if(msg != null)
            context.json(om.writeValueAsString(msg));
    }

    private void deleteMsgByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message msg = msgService.deleteMessage(Integer.parseInt(context.pathParam("message_id")));

        if(msg != null)
            context.json(om.writeValueAsString(msg));
    }

    private void updateMsgByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        String text = om.readValue(context.body(), Message.class).getMessage_text();
        int id = Integer.parseInt(context.pathParam("message_id"));
        System.out.println(text);
        if(text == null || text.isBlank() || text.length() > 255) {
            context.status(400);
            return;
        }

        Message msg = msgService.updateMessage(text, id);
        if(msg == null)
            context.status(400);
        else
            context.json(om.writeValueAsString(msg));
    }

    private void getMsgByUID(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        List<Message> messages = msgService.getAllMessagesFrom(Integer.parseInt(context.pathParam("account_id")));

        context.json(om.writeValueAsString(messages));
    }
}