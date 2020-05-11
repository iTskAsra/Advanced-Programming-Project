package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.CustomerController;
import controller.ExceptionsLibrary;
import model.Customer;

import java.util.HashMap;

public class CustomerInfo extends Menu {
    public CustomerInfo(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();

        submenus.put(1, viewCustomerInfo());
        submenus.put(2, editCustomerInfo());

        this.setSubmenus(submenus);
    }

    private Menu viewCustomerInfo() {
        return new Menu("View Personal Info", this) {

            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                String customerData = null;
                try {
                    customerData = CustomerController.showCustomerInfo();
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Customer customer = gson.fromJson(customerData,Customer.class);
                    System.out.printf("Username : %s\nFirst Name : %s\nLast name : %s\nE-Mail : %s\nPhone Number : %s\n",customer.getUsername(),customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getPhoneNumber());
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }

        };
    }

    private Menu editCustomerInfo() {
        return new Menu("Edit Customer Info" , this){

            @Override
            public void show(){
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run(){
                String fields = Main.scanInput("String");
                String[] splitFields = fields.split("\\s*,\\s*");
                HashMap<String,String> editedData = new HashMap<>();
                for (String i : splitFields){
                    System.out.printf("Enter new %s\n",i.substring(0, 1).toUpperCase() + i.substring(1));
                    String newValue = Main.scanInput("String");
                    editedData.put(i,newValue);
                }
                try {
                    CustomerController.editCustomerInfo(editedData);
                } catch (ExceptionsLibrary.NoAccountException e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionsLibrary.NoFeatureWithThisName noFeatureWithThisName) {
                    System.out.println(noFeatureWithThisName.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

}
