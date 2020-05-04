package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Off;
import model.Product;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class OffPageController {

    public static ArrayList<Off> listOffs(){
        ArrayList<Off> offsList = new ArrayList<>();
        String path = "Resources/Offs";
        File folder = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file1) {
                if (file1.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        for (File i : folder.listFiles(fileFilter)){
            String offId = i.getName().replace(".json","");
            Off off = GetDataFromDatabase.getOff(Integer.parseInt(offId));
            offsList.add(off);
        }
        return offsList;
    }


    public static String goToProductPage(int productId) {
        Product product = GetDataFromDatabase.getProduct(productId);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String data = gson.toJson(product);
        return data;
    }
}
