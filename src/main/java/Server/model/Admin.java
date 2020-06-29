package Server.model;


import java.util.ArrayList;

public class Admin extends Account {

    public Admin(String username, String password, String role, String firstName, String lastName, String email, String phoneNumber, ArrayList<Sale> saleCodes, double credit, ArrayList<Request> requests) {
        super(username, password, role, firstName, lastName, email, phoneNumber, saleCodes, credit);
    }
}