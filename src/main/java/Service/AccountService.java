package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Registers user account if:
     * <ul>
     * <li> Username does not exist
     * <li> Username is not null
     * <li> Password is atleast 4 characters long
     * </ul>
     * @param account
     * @return
     */
    public Account registerUser(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        Account registeredAccount = null;
        if(username != null && password != null && !username.contentEquals("") && password.length() >=4 && !accountDAO.isUsernameExists(username)){
            if(accountDAO.registerUser(account)){
                registeredAccount = accountDAO.getAccount(username);
            }
        }
        return registeredAccount;
    }
}
