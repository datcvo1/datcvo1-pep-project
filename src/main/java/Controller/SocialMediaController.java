package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accService;
    MessageService msgService;

    public SocialMediaController() {
        accService = new AccountService();
        msgService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */ 
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postHandler);
        app.get("/messages", this::allMessagesHandler);
        app.get("/message/{messageID}", this::messageByIdHandler);
        app.delete("/delete/{messageID}", this::deleteMsgByIdHandler);
        app.put("/update/{messageID}", this::updateMsgByIdHandler);
        app.get("/user-messages/{userID}", this::getMsgByUID);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registrationHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account newAcc = om.readValue(context.body(), Account.class);

        if((newAcc.getUsername() != "" && newAcc.getPassword().length() > 3) && accService.registration(newAcc) != null)
            context.json(om.writeValueAsString(newAcc));
        else
            context.status(400);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(context.body(), Account.class);

        if(accService.login(acc) != null)
            context.json(om.writeValueAsString(acc));
        else
            context.status(401);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message newMsg = om.readValue(context.body(), Message.class);

        if((newMsg.getMessage_text().length() != 0 && newMsg.getMessage_text().length() <= 255) && msgService.createMessage(newMsg) != null)
            context.json(om.writeValueAsString(newMsg));
        else
            context.status(400);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void allMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        List<Message> messages = msgService.getAllMessages();

        context.json(om.writeValueAsString(messages));
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void messageByIdHandler(Context context) {
        
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMsgByIdHandler(Context context) {
        
    }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMsgByIdHandler(Context context) {
        
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMsgByUID(Context context) {
        
    }
}