package UT;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class RequestTest{
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        ArrayList<String[]> list = new ArrayList<String[]>();
        String[] a = new String[4];
        a[0] = "hello";
        a[1] = "how";
        a[2] = "are";
        a[3] = "3";
        String[] b = new String[4];
        b[0] = "q";
        b[1] = "w";
        b[2] = "e";
        b[3] = "4";
        list.add(a);
        list.add(b);
        String data = gson.toJson(list);
        System.out.println(data);

    }
}