package view;

public class  ExceptionLibrary {
    public static class WrongUsernameException extends Exception{
        String username;

        public String getUsername() {
            return username;
        }

        public WrongUsernameException(String message, String username) {
            super(message);
            this.username = username;
        }
    }

    public static class WrongPasswordException extends Exception{
        public WrongPasswordException(String message) {
            super(message);
        }
    }

    public static class UsernameExistException extends Exception{
        String username;

        public String getUsername() {
            return username;
        }

        public UsernameExistException(String message, String username) {
            super(message);
            this.username = username;
        }
    }

    public static class NoProductException{

    }

    public static class NotLoggedInException{

    }

    public static class AdminExist extends Exception {
        public AdminExist(String message) {
            super(message);
        }
    }
}
