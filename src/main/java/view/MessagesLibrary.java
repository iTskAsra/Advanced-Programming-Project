package view;

public class MessagesLibrary {
    public static void errorLibrary(int errorCode){
        if (errorCode == 1){
            System.out.println("No account with this username.");
        }
        else if (errorCode == 2){
            System.out.println("Wrong Password!");
        }
        else if (errorCode == 3){
            System.out.println("An account already exists with this username.");
        }
        else if (errorCode == 4){
            System.out.println("You're not logged in!");
        }
        else if (errorCode == 5){
            System.out.println("Don't have this field!");
        }
        else if (errorCode == 6){
            System.out.println("Don't have a product with this ID!");
        }
        else if (errorCode == 7){
            System.out.println("Don't have an off with this ID!");
        }

    }

    public static void messagesLibrary(int messageCode){
        if (messageCode == 1){
            System.out.println("Registered!");
        }
        if (messageCode == 2){
            System.out.println("Logged in!");
        }
        if (messageCode == 3){
            System.out.println("Product removed!");
        }
    }
}
