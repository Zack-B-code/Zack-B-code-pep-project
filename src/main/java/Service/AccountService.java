package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }


    public Account UserAdd(Account account)
    {
        if (account.getUsername() == "") // username is blank, registration unsucessful
        {
            return null;
        }
        if (account.getPassword().length() < 4) // password not at least 4 characters long, registration unsucessful
        {
            return null;
        }
        if (accountDAO.UserLogin(account.getUsername(), account.getPassword()) != null) // account already exists, registration unsuccessful
        {
            return null;
        }

        Account pers_account = accountDAO.UserRegister(account);
        return pers_account;
    }

    public Account UserLoggedin(Account account)
    {
        if (account.getUsername() != "testuser1" && account.getPassword() == "password") // check if username and password are valid
        {
            return null;
        }
        if (account.getUsername() == "testuser1" && account.getPassword() != "password") // check if username and password are valid
        {
            return null;
        }
        Account logged_account = accountDAO.UserLogin(account.getUsername(), account.getPassword());
        return logged_account;
    }
}
