package view;

public class MessagesLibrary {
    public static void errorLibrary(int errorCode){
        if (errorCode == 1){
            System.out.println("No account with this username");
        }
        else if (errorCode == 2){
            System.out.println("Wrong Password!");
        }
        else if (errorCode == 3){
            System.out.println("An account already exists with this username");
        }
    }

    public static void messagesLibrary(int messageCode){
        if (messageCode == 1){
            System.out.println("Registered!");
        }
        if (messageCode == 2){
            System.out.println("Logged in!");
        }
    }
}
