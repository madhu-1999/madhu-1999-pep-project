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
     * @return created object account if insertion successful, else null
     */
    public Account registerAccount(Account account){
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

    /**
     * Handles user login
     * @param account
     * @return Account object for user who has logined.
     */
    public Account userLogin(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        Account registeredAccount = null;
        registeredAccount = accountDAO.getAccount(username);
        if(registeredAccount != null && !registeredAccount.getPassword().equals(password)) {
            registeredAccount = null;
        }
        return registeredAccount;
    }
}
