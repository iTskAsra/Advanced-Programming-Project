package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import model.Admin;
import model.Request;
import model.Sale;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(String name, Menu parentMenu) {
        super(name,parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllRequests());
        submenus.put(2, showRequestDetails());
        submenus.put(3, processRequest());
        this.setSubmenus(submenus);
    }

    private Menu processRequest() {
        return new Menu("Process Request",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter request ID :");
            }

            @Override
            public void run() {
                int requestId = Integer.parseInt(Menu.scanner.nextLine());
                if (AdminController.checkIfRequestExist(requestId)){
                    System.out.println("Accept it or not? (yes or no)");
                    String status = Menu.scanner.nextLine();
                    if (status.trim().equalsIgnoreCase("yes")){
                        AdminController.processRequest(requestId,true);
                        getParentMenu().show();
                        getParentMenu().run();
                    }
                    else if (status.trim().equalsIgnoreCase("no")){
                        AdminController.processRequest(requestId,false);
                        getParentMenu().show();
                        getParentMenu().run();
                    }
                    else {
                        this.show();
                        this.run();
                    }
                }
                else {
                    //TODO error
                }

            }
        };
    }

    private Menu showRequestDetails() {
        return new Menu("Request details",this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter request ID :");
            }

            @Override
            public void run() {
                int requestId = Integer.parseInt(Menu.scanner.nextLine());
                String data = AdminController.showRequest(requestId);
                Gson gson = new GsonBuilder().serializeNulls().create();
                Request request = gson.fromJson(data, Request.class);
                System.out.printf("%s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%s\n","-".repeat(60), "Request ID :", " ".repeat(10), request.getRequestId(), "Type :", " ".repeat(10), request.getRequestType(), "Condition :", " ".repeat(10), request.getRequestCondition(), "Requester :", " ".repeat(10), request.getRequestSeller().getUsername(),"-".repeat(60));
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showAllRequests() {
        return new Menu("Show all requests", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
            }

            @Override
            public void run() {
                ArrayList<Request> allRequests = AdminController.showAdminRequests();
                for (Request i : allRequests) {
                    System.out.printf("%-15s%s%20s%s%20s\n", i.getRequestId(), " ".repeat(5), i.getRequestType() , " ".repeat(5) , i.getRequestCondition());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
