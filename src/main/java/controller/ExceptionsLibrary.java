package controller;

public class  ExceptionsLibrary {
    public static class WrongUsernameException extends Exception{
        public WrongUsernameException() {
            super("No account with this username!");
        }
    }

    public static class WrongPasswordException extends Exception{
        public WrongPasswordException() {
            super("Password is not correct!");
        }
    }

    public static class UsernameAlreadyExists extends Exception{
        public UsernameAlreadyExists() {
            super("An account already exists with this username!");
        }
    }


    public static class NoAccountException extends Exception{
        public NoAccountException() {
            super("No user exist with this username!");
        }
    }

    public static class NoProductException extends Exception{
        public NoProductException() {
            super("No product with this product ID");
        }
    }

    public static class NoOffException extends Exception{
        public NoOffException() {
            super("No off with this off ID");
        }
    }

    public static class NoRequestException extends Exception{
        public NoRequestException() {
            super("No request with this request ID");
        }
    }

    public static class NoSaleException extends Exception {
        public NoSaleException() {
            super("No sale found with this code!");
        }
    }

    public static class NoCategoryException extends Exception {
        public NoCategoryException() {
            super("No category found with this name!");
        }
    }

    public static class NotLoggedInException{

    }

    public static class CategoryExistsWithThisName extends Exception{
        public CategoryExistsWithThisName() {
            super("A category already exists with this name!");
        }
    }

    public static class AdminExist extends Exception {
        public AdminExist() {
            super("You can not register as an admin!");
        }
    }



    public static class NoFeatureWithThisName extends Exception {
        public NoFeatureWithThisName() {
            super("No feature found with this name");
        }
    }

    public static class NoFilterWithThisName extends Exception{
        public NoFilterWithThisName() {
            super("No filter found with this name!");
        }
    }

    public static class NoSortWithThisName extends Exception{
        public NoSortWithThisName() {
            super("No sort found with this name!");
        }
    }

    public static class SelectASeller extends Exception{
        public SelectASeller() {
            super("You should select a seller to proceed!");
        }
    }

    public static class NotEnoughNumberAvailableException extends Exception{
        public NotEnoughNumberAvailableException() {
            super("Not enough number of this product to proceed!");
        }
    }



}
