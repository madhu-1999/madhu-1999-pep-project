package Service;

import java.util.List;
import java.util.ArrayList;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    /**
     * Creates a new blog post entry.
     * @param message
     * @return created Message object if successfully created, else null
     */
    public Message createMessage(Message message) {
        Message createdMessage = null;
        boolean existsUser = accountDAO.isAccountExists(message.getPosted_by());
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() <=255 && existsUser) {
            int message_id = messageDAO.createMessage(message);
            if(message_id != -1)
            createdMessage = messageDAO.getMessage(message_id);
        }
        return createdMessage;
    }
    
    /**
     * Fetches all messages created by all users
     * @return List of all messages created by all users
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Fetches all messages created by given account id
     * @return List of all messages created by given account id
     */
    public List<Message> getAllMessagesByAccountId(int account_id) {
        if(account_id == -1)
            return new ArrayList<>();
       return messageDAO.getAllMessagesByAccountId(account_id);
    }
    
}
