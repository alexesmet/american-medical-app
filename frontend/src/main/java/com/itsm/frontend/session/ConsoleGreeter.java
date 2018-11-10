package com.itsm.frontend.session;

import com.itsm.common.entity.User;
import com.itsm.frontend.core.ConsoleMenu;
import com.itsm.frontend.storage.interf.InterfaceUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("ConsoleGreeter")
public class ConsoleGreeter implements Runnable {
    private ConsoleMenu consoleMenu;
    private UserSessionHolder sessionHolder;
    private InterfaceUserStorage userStorage;
    private PasswordEncryptor passwordEncryptor;


    @Autowired
    public ConsoleGreeter(ConsoleMenu consoleMenu, UserSessionHolder sessionHolder, InterfaceUserStorage userStorage, PasswordEncryptor passwordEncryptor) {
        this.consoleMenu = consoleMenu;
        this.sessionHolder = sessionHolder;
        this.userStorage = userStorage;
        this.passwordEncryptor = passwordEncryptor;
    }


    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("Enter username/email and password.");
            System.out.print("  Username: ");
            String name = sc.nextLine();


            String pswd;
            if (System.console() != null) {
                System.out.print("  Password: (secured) ");
                char[] chars = System.console().readPassword();
                pswd = new String(chars);
            } else {
                System.out.print("  Password: ");
                pswd = sc.nextLine();
            }

            if (name.equals("")){
                System.out.println("Bye.");
                break;
            }

            User user;
            if (name.contains("@")) {
                user = userStorage.getByEmail(name);
            } else {
                user = userStorage.getByName(name);
            }

            if (user == null) {
                System.out.println("No such user in the database.");
                continue;
            }

            if (user.getPassword().equals(passwordEncryptor.encrypt(pswd))) {
                sessionHolder.setUser(user);
                consoleMenu.run();
            } else {
                System.out.println("Incorrect password.");
            }
        }
    }
}
