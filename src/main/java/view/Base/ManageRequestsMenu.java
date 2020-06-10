package view.Base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AdminController;
import controller.ExceptionsLibrary;
import controller.GetDataFromDatabase;
import controller.SortController;
import model.Off;
import model.Product;
import model.Request;
import model.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showAllRequests());
        submenus.put(2, showRequestDetails());
        submenus.put(3, processRequest());
        submenus.put(submenus.size() + 1, help());

        this.setSubmenus(submenus);
    }

    protected Menu help() {
        return new Menu("Help", this) {
            @Override
            public void show() {
                System.out.println("------------------------------");
                System.out.printf("Manage Requests\nYou can see all the requests sent to you and look at their details.\n");
                System.out.println("------------------------------");
            }

            @Override
            public void run() {
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu processRequest() {
        return new Menu("Process Request", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter request ID :");
            }

            @Override
            public void run() {
                int requestId = Integer.parseInt(Main.scanInput("int"));
                try {
                    Request request = GetDataFromDatabase.getRequest(requestId);
                    System.out.printf("Request ID : %d\nRequest Type : %s\n", request.getRequestId(), request.getRequestType());
                    System.out.println("Accept it or not? (yes or no)");
                    String status = Main.scanInput("String");
                    if (status.trim().equalsIgnoreCase("yes")) {
                        try {
                            AdminController.processRequest(requestId, true);
                        } catch (ExceptionsLibrary.NoRequestException e) {
                            System.out.println(e.getMessage());
                        } catch (ExceptionsLibrary.NoAccountException e) {
                            System.out.println(e.getMessage());
                        } catch (ExceptionsLibrary.UsernameAlreadyExists usernameAlreadyExists) {
                            System.out.println(usernameAlreadyExists.getMessage());
                        } catch (ExceptionsLibrary.NoProductException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Processed!");
                        getParentMenu().show();
                        getParentMenu().run();
                    } else if (status.trim().equalsIgnoreCase("no")) {
                        try {
                            AdminController.processRequest(requestId, false);
                        } catch (ExceptionsLibrary.NoRequestException e) {
                            System.out.println(e.getMessage());
                        } catch (ExceptionsLibrary.NoAccountException e) {
                            System.out.println(e.getMessage());
                        } catch (ExceptionsLibrary.UsernameAlreadyExists usernameAlreadyExists) {
                            System.out.println(usernameAlreadyExists.getMessage());
                        } catch (ExceptionsLibrary.NoProductException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Processed!");
                        getParentMenu().show();
                        getParentMenu().run();
                    } else {
                        System.out.println("Invalid input, try again! (yes or no)");
                        this.show();
                        this.run();
                    }
                } catch (ExceptionsLibrary.NoRequestException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }

    private Menu showRequestDetails() {
        return new Menu("Request details", this) {
            @Override
            public void show() {
                System.out.println(this.getName() + ":");
                System.out.println("Enter request ID :");
            }

            @Override
            public void run() {
                int requestId = Integer.parseInt(Main.scanInput("int"));
                String data = null;
                try {
                    data = AdminController.showRequest(requestId);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Request request = gson.fromJson(data, Request.class);
                    System.out.println("-".repeat(80));
                    System.out.println("Request Details:");
                    switch (request.getRequestType()) {
                        case REGISTER_SELLER:
                            Seller seller = gson.fromJson(request.getRequestDescription(), Seller.class);
                            System.out.printf("Username : %s\nFirst Name : %s\nLast Name : %s\nEmail : %s\nPhone Number : %s\nCredit : %.2f\n", seller.getUsername(), seller.getFirstName(), seller.getLastName(), seller.getEmail(), seller.getPhoneNumber(), seller.getCredit());
                            break;
                        case ADD_PRODUCT:
                        case EDIT_PODUCT:
                            Product product = gson.fromJson(request.getRequestDescription(), Product.class);
                            System.out.printf("Name : %s\nPrice : %.2f\nCategory : %s\nQuantity : %d\n", product.getName(), product.getPrice(), product.getCategory().getName(), product.getAvailability());
                            break;
                        case ADD_OFF:
                        case EDIT_OFF:
                            Off off = gson.fromJson(request.getRequestDescription(), Off.class);
                            System.out.printf("Off Amount : %s\nStart Date : %.2f\nEnd Date : %s\n", off.getOffAmount(), off.getStartDate(), off.getEndDate());
                            break;
                    }
                    System.out.printf("%s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%-20s%s%30s\n%s\n", "-".repeat(60), "Request ID :", " ".repeat(10), request.getRequestId(), "Type :", " ".repeat(10), request.getRequestType(), "Condition :", " ".repeat(10), request.getRequestCondition(), "Requester :", " ".repeat(10), request.getRequestSeller(), "-".repeat(60));
                    System.out.println("-".repeat(80));
                } catch (ExceptionsLibrary.NoRequestException e) {
                    System.out.println(e.getMessage());
                }
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
                ArrayList<Request> allRequests = null;
                try {
                    allRequests = AdminController.showAdminRequests();
                    if (allRequests.size() != 0) {
                        for (Request i : allRequests) {
                            System.out.printf("Request ID : %-15s %s Type: %20s %s Requester : %20s\n", i.getRequestId(), " ".repeat(5), i.getRequestType(), " ".repeat(5), i.getRequestSeller());
                        }
                        System.out.println("Do you want to sort? (yes/no each time you (want/don't want) to sort)");
                        while (Main.scanInput("String").trim().equalsIgnoreCase("yes")) {
                            SortHandler.sortRequest();
                            SortController.sortRequest(allRequests);
                            for (Request i : allRequests) {
                                System.out.printf("Request ID : %-15s %s Type: %20s %s Requester : %20s\n", i.getRequestId(), " ".repeat(5), i.getRequestType(), " ".repeat(5), i.getRequestSeller());
                            }
                            System.out.println("Sort again? (yes/no)");
                        }
                    } else {
                        System.out.println("No request left!");
                    }
                } catch (ExceptionsLibrary.NoRequestException e) {
                    System.out.println(e.getMessage());
                }
                getParentMenu().show();
                getParentMenu().run();
            }
        };
    }
}
