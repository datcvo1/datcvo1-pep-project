package Service;

import DAO.AccountDao;
import Model.Account;

public class AccountService {
    public AccountDao dao;

    public AccountService() {
        dao = new AccountDao();
    }

    public Account registration(Account acc) {
        if(dao.usernameUsed(acc.username) == true)
            return null;

        return dao.insertAccount(acc);
    }

    public Account login(Account acc) {
        return dao.getAccount(acc);
    }
}
