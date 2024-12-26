package Service;

import DAO.MessageDao;
import Model.Message;
import DAO.AccountDao;

public class MessageService {
    MessageDao msgDao;
    AccountDao accDao;
    

    public MessageService() {
        msgDao = new MessageDao();
        accDao = new AccountDao();
    }

    public Object createMessage(Message newMsg) {
        return msgDao.insertMessage(newMsg);
    }
    
}
