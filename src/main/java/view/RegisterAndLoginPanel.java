package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.RegisterAndLogin;
import model.Admin;
import model.Customer;
import model.Seller;

import java.io.File;
import java.util.HashMap;

public class RegisterAndLoginPanel extends Menu {
    public RegisterAndLoginPanel(Menu parentMenu) {
        super("Login Or Register",parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1,getLoginMenu());
        submenus.put(2,getRegisterMenu());
        this.setSubmenus(submenus);
    }

    protected Menu getRegisterMenu() {
        return new Menu("Register",this) {
            @Override
            public void show() {
                System.out.println("Select Role:");
                System.out.printf("1. Customer\n2. Seller\n");
                if (GetDataFromDatabase.checkIfAnyAdminExists()){
                    System.out.println("3. Admin");
                }
            }

            @Override
            public void run() {
                int chosenNumber = Integer.parseInt(scanner.nextLine());
                if (chosenNumber == 1){
                    //TODO check username and password
                    //TODO check credit
                    System.out.println("Enter Username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter Password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter First Name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter Last Name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter Email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter Phone Number:");
                    String phoneNumber = scanner.nextLine();
                    System.out.println("Enter Credit:");
                    double credit = Double.parseDouble(scanner.nextLine());
                    Customer customer = new Customer(username,password,"Customer",firstName,lastName,email,phoneNumber,null,credit,null);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String data = gson.toJson(customer);
                    try {
                        RegisterAndLogin.register(data);
                        getParentMenu().show();
                        getParentMenu().run();
                    }
                    catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.AdminExist e ){
                        System.out.println(e.getMessage());
                    }

                }
                else if (chosenNumber == 2){
                    //TODO check username and password
                    System.out.println("Enter Username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter Password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter First Name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter Last Name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter Email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter Phone Number:");
                    String phoneNumber = scanner.nextLine();
                    System.out.println("Enter Credit:");
                    double credit = Double.parseDouble(scanner.nextLine());
                    System.out.println("Enter Company Name:");
                    String company = scanner.nextLine();
                    Seller seller = new Seller(username,password,"Seller",firstName,lastName,email,phoneNumber,null,credit,company,null,null,null);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String data = gson.toJson(seller);
                    try {
                        RegisterAndLogin.register(data);
                        getParentMenu().show();
                        getParentMenu().run();
                    }
                    catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.AdminExist e ){
                        System.out.println(e.getMessage());
                    }
                } else if (chosenNumber == 3 && new File("Resources/Accounts/Admin").listFiles().length == 0) {
                    //TODO check username and password
                    System.out.println("Enter Username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter Password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter First Name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter Last Name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter Email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter Phone Number:");
                    String phoneNumber = scanner.nextLine();
                    System.out.println("Enter Credit:");
                    double credit = Double.parseDouble(scanner.nextLine());
                    Admin admin = new Admin(username,password,"Admin",firstName,lastName,email,phoneNumber,null,credit,null);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String data = gson.toJson(admin);
                    try {
                        RegisterAndLogin.register(data);
                        getParentMenu().show();
                        getParentMenu().run();
                    }
                    catch (ExceptionsLibrary.NoAccountException | ExceptionsLibrary.AdminExist e ){
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    System.out.println("Enter a valid number!");
                    this.show();
                    this.run();
                }

            }
        };
    }

    protected Menu getLoginMenu() {
        return new Menu("Login",this) {
            HashMap<String,String> dataToSend = new HashMap<>();
            @Override
            public void show() {
                System.out.println("Enter Username:");
                //TODO check if username is valid
                String username = Menu.scanner.nextLine();
                System.out.println("Enter Username:");
                //TODO check if password is valid
                String password = Menu.scanner.nextLine();
                dataToSend.put("username",username);
                dataToSend.put("password", password);
            }

            @Override
            public void run() {
                try {
                    RegisterAndLogin.login(dataToSend);
                    Menu nextMenu = new MainMenu();
                    nextMenu.show();
                    nextMenu.run();
                }
                catch (ExceptionsLibrary.WrongUsernameException e){
                    System.out.printf("No account with username %s exist!\n",e.getUsername());
                    this.getParentMenu().show();
                    this.getParentMenu().run();
                }
                catch (ExceptionsLibrary.WrongPasswordException e){
                    System.out.printf("Password not correct!\n");
                    this.getParentMenu().show();
                    this.getParentMenu().run();
                }

            }
        };
    }

}
