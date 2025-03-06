package Service;

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
    
}
