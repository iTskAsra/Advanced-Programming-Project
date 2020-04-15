package model;


import java.util.ArrayList;

public class Admin extends Account {
    public static ArrayList<Admin> allAdmins;
    private ArrayList<Request> requests;

    public Admin(String username, String password, String firstName, String lastName, String email, String phoneNumber, double credit, ArrayList<Request> requests) {
        super(username, password, firstName, lastName, email, phoneNumber, credit);
        this.requests = requests;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }
}