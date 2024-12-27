package Service;

import DAO.MessageDao;
import Model.Message;

import java.util.List;

import DAO.AccountDao;

public class MessageService {
    MessageDao msgDao;
    AccountDao accDao;
    

    public MessageService() {
        msgDao = new MessageDao();
        accDao = new AccountDao();
    }

    public Object createMessage(Message newMsg) {
        if(accDao.getAccount(newMsg.getPosted_by()) != null)
            return msgDao.insertMessage(newMsg);
        
        return null;
    }

    public List<Message> getAllMessages() {
        return msgDao.getAllMessages();
    }

    public Message getMessage(int message_id) {
        return msgDao.getMessage(message_id);
    }

    public Message deleteMessage(int message_id) {
        return msgDao.deleteMessage(message_id);
    }

    public Message updateMessage(String text, int id) {
        if(getMessage(id) == null)
            return null;

        return msgDao.updateMessage(text, id);
    }

    public List<Message> getAllMessagesFrom(int id) {
        return msgDao.getAllMessagesFrom(id);
    }
    
}
