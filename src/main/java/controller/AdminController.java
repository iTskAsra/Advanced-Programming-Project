package controller;

import model.Admin;

public class AdminController {
    private  Admin admin;

    public AdminController(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
