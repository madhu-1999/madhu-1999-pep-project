package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::registerAccountHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("accounts/{id}/messages", this::getAllMessagesByAccountIdHandler);
        app.get("messages/{message_id}", this::getMessageById);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler for registering account
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccountHandler(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount != null){
            ctx.status(200).json(registeredAccount);
        } else {
            ctx.status(400).result("");
        }
    }

    /**
     * Handler for user account login
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account registeredAccount = accountService.userLogin(account);
        if(registeredAccount != null){
            ctx.status(200).json(registeredAccount);
        } else {
            ctx.status(401).result("");
        }
    }

    /**
     * Handler for creating message
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createMessageHandler(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null){
            ctx.status(200).json(createdMessage);
        } else {
            ctx.status(400).result("");
        }
    }

    /**
     * Handler for fetching all messages created by all accounts
     * @param ctx
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> allMessages = messageService.getAllMessages();
        ctx.status(200).json(allMessages);
    }
    
    /**
     * Handler for fetching all messages created by given account id.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesByAccountIdHandler(Context ctx) {
        int accountId = -1;
        try {
            accountId = Integer.parseInt(ctx.pathParam("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Message> allMessages = messageService.getAllMessagesByAccountId(accountId);
        ctx.status(200).json(allMessages);
    }

    /**
     * Fetches a message given its message_id
     * @param ctx
     */
    private void getMessageById(Context ctx) {
        int message_id = -1;
        try {
            message_id = Integer.parseInt(ctx.pathParam("message_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message message = messageService.getMessageById(message_id);
        if(message != null){
            ctx.status(200).json(message);
        } else {
            ctx.status(200);
        }
    }
}