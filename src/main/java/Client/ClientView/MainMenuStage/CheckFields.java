package Client.ClientView.MainMenuStage;


import Client.ClientController.ExceptionsLibrary;

public class CheckFields {

    public static void checkField(String type, String input) throws ExceptionsLibrary.NotAcceptableFormatInput {
        if (type.equals("int")){
            try {
                int test = Integer.parseInt(input);
            }
            catch (NumberFormatException e){
                throw new ExceptionsLibrary.NotAcceptableFormatInput();
            }

        }
        else if (type.equals("double")){
            try {
                double test = Double.parseDouble(input);
            }
            catch (NumberFormatException e){
                throw new ExceptionsLibrary.NotAcceptableFormatInput();
            }

        }
    }

}
