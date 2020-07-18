package view.Base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ExceptionsLibrary;
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
        submenus.put(submenus.size()+1,help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help",this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Register and Login Panel\nHere you can create new account or login to your account if you already have One\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    public static boolean usernameOrPasswordIsValid (String usernameOrPassword){
        if (usernameOrPassword.matches("\\w*")){
            return true;
        }
        return false;
    }

    @Override
    public void show(){
        System.out.println(this.name + " :");

        for(int i = 1 ; i <= this.submenus.size() ; i++){
            System.out.println(i + ". " + this.submenus.get(i).getName());
        }

        if (this.parentMenu != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    @Override
    public void run(){
        Menu nextMenu = null;
        int chosenNum = Integer.parseInt(Main.scanInput("int"));
        if(chosenNum > this.submenus.size() + 1 || chosenNum < 1){
            System.out.println("Please select a valid number:");
            this.run();
        }

        if(chosenNum == this.submenus.size() + 1){
            if(this.parentMenu == null){
                System.exit(1);
            }
            else
                nextMenu = this.parentMenu;
        }
        else
            nextMenu = this.submenus.get(chosenNum);
        nextMenu.update();
        nextMenu.show();
        nextMenu.run();
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
                int chosenNumber = Integer.parseInt(Main.scanInput("int"));
                if (chosenNumber == 1){
                    System.out.println("Enter Username:");
                    String username = Main.scanInput("String");
                    while (!usernameOrPasswordIsValid(username)){
                        System.out.println("Username format invalid try again :");
                        username = Main.scanInput("String");
                    }
                    System.out.println("Enter Password:");
                    String password = Main.scanInput("String");
                    while (!usernameOrPasswordIsValid(password)){
                        System.out.println("Password format invalid try again :");
                        password = Main.scanInput("String");
                    }
                    System.out.println("Enter First Name:");
                    String firstName = Main.scanInput("String");
                    System.out.println("Enter Last Name:");
                    String lastName = Main.scanInput("String");
                    System.out.println("Enter Email:");
                    String email = Main.scanInput("String");
                    System.out.println("Enter Phone Number:");
                    String phoneNumber = Main.scanInput("String");
                    System.out.println("Enter Credit:");
                    double credit = Double.parseDouble(Main.scanInput("double"));
                    Customer customer = new Customer(username,password,"Customer",firstName,lastName,email,phoneNumber,null,credit,null);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String data = gson.toJson(customer);
                    try {
                        RegisterAndLogin.register(data);
                        System.out.println("Registered!");
                        getParentMenu().show();
                        getParentMenu().run();
                    }
                    catch (ExceptionsLibrary.AdminExist | ExceptionsLibrary.UsernameAlreadyExists e ){
                        System.out.println(e.getMessage());
                    }

                }
                else if (chosenNumber == 2){
                    System.out.println("Enter Username:");
                    String username = Main.scanInput("String");
                    while (!usernameOrPasswordIsValid(username)){
                        System.out.println("Username format invalid try again :");
                        username = Main.scanInput("String");
                    }
                    System.out.println("Enter Password:");
                    String password = Main.scanInput("String");
                    while (!usernameOrPasswordIsValid(password)){
                        System.out.println("Password format invalid try again :");
                        password = Main.scanInput("String");
                    }
                    System.out.println("Enter First Name:");
                    String firstName = Main.scanInput("String");
                    System.out.println("Enter Last Name:");
                    String lastName = Main.scanInput("String");
                    System.out.println("Enter Email:");
                    String email = Main.scanInput("String");
                    System.out.println("Enter Phone Number:");
                    String phoneNumber = Main.scanInput("String");
                    System.out.println("Enter Credit:");
                    double credit = Double.parseDouble(Main.scanInput("double"));
                    System.out.println("Enter Company Name:");
                    String company = Main.scanInput("String");
                    Seller seller = new Seller(username,password,"Seller",firstName,lastName,email,phoneNumber,null,credit,company,null,null,null);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String data = gson.toJson(seller);
                    try {
                        RegisterAndLogin.register(data);
                        System.out.println("Request sent!");
                        getParentMenu().show();
                        getParentMenu().run();
                    }
                    catch (ExceptionsLibrary.AdminExist | ExceptionsLibrary.UsernameAlreadyExists e ){
                        System.out.println(e.getMessage());
                    }
                } else if (chosenNumber == 3 && new File("Resources/Accounts/Admin").listFiles().length == 0) {
                    System.out.println("Enter Username:");
                    String username = Main.scanInput("String");
                    while (!usernameOrPasswordIsValid(username)){
                        System.out.println("Username format invalid try again :");
                        username = Main.scanInput("String");
                    }
                    System.out.println("Enter Password:");
                    String password = Main.scanInput("String");
                    while (!usernameOrPasswordIsValid(password)){
                        System.out.println("Password format invalid try again :");
                        password = Main.scanInput("String");
                    }
                    System.out.println("Enter First Name:");
                    String firstName = Main.scanInput("String");
                    System.out.println("Enter Last Name:");
                    String lastName = Main.scanInput("String");
                    System.out.println("Enter Email:");
                    String email = Main.scanInput("String");
                    System.out.println("Enter Phone Number:");
                    String phoneNumber = Main.scanInput("String");
                    System.out.println("Enter Credit:");
                    double credit = Double.parseDouble(Main.scanInput("double"));
                    Admin admin = new Admin(username,password,"Admin",firstName,lastName,email,phoneNumber,null,credit,null);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String data = gson.toJson(admin);
                    RegisterAndLogin.registerAdmin(data);
                    System.out.println("Registered!");
                    getParentMenu().show();
                    getParentMenu().run();
                }
                else {
                    System.out.println("Enter a valid number!");
                    this.show();
                    this.run();
                }
                getParentMenu().show();
                getParentMenu().run();

            }
        };
    }

    protected Menu getLoginMenu() {
        return new Menu("Login",this) {
            HashMap<String,String> dataToSend = new HashMap<>();
            @Override
            public void show() {
                System.out.println("Enter Username:");
                String username = Main.scanInput("String");
                while (!usernameOrPasswordIsValid(username)){
                    System.out.println("Username format invalid try again :");
                    username = Main.scanInput("String");
                }
                System.out.println("Enter Password:");
                String password = Main.scanInput("String");
                while (!usernameOrPasswordIsValid(password)){
                    System.out.println("Password format invalid try again :");
                    password = Main.scanInput("String");
                }
                dataToSend.put("username",username);
                dataToSend.put("password", password);
            }

            @Override
            public void run() {
                try {
                    RegisterAndLogin.login(dataToSend);
                    System.out.println("Logged in!");
                    getParentMenu().getParentMenu().update();
                    getParentMenu().getParentMenu().show();
                    getParentMenu().getParentMenu().run();
                }
                catch (ExceptionsLibrary.WrongUsernameException e){
                    System.out.println(e.getMessage());
                    getParentMenu().show();
                    getParentMenu().run();
                }
                catch (ExceptionsLibrary.WrongPasswordException e){
                    System.out.println(e.getMessage());
                    getParentMenu().show();
                    getParentMenu().run();

                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                    getParentMenu().show();
                    getParentMenu().run();
                }
            }
        };
    }

}