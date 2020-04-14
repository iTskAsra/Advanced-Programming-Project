package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static LocalDateTime localDateTime = LocalDateTime.now();
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Menu.setScanner(Main.scanner);
    }
}
