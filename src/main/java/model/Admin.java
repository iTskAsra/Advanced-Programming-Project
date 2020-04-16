package model;


import java.util.ArrayList;

public class Admin extends Account {
    public static ArrayList<Admin> allAdmins;
    private ArrayList<Request> requests;

    public Admin(String username, String password, String role, String firstName, String lastName, String email, String phoneNumber, ArrayList<Sale> saleCodes, double credit, ArrayList<Request> requests) {
        super(username, password, role, firstName, lastName, email, phoneNumber, saleCodes, credit);
        this.requests = requests;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }
}