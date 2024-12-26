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
    
}
