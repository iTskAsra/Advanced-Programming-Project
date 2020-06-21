package controller;

import model.Account;
import model.Customer;
import model.Off;
import model.Sale;
import view.Base.Main;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

public class SetPeriodicSales {
    public static void setPeriodicSales() throws ExceptionsLibrary.NoAccountException {
        if (isOkToRun()) {
            Main.localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM");
            String formattedDate = Main.localDateTime.format(dateTimeFormatter);
            String[] splitDate = formattedDate.split("-");
            int date = Integer.parseInt(splitDate[0]);
            if (date % 3 == 0) {
                Random random = new Random();
                double minRangePercent = 5;
                double maxRangePercent = 25;
                double percent = minRangePercent + (maxRangePercent - minRangePercent + 1) * random.nextDouble();
                double minRangeAmount = 10000;
                double maxRangeAmount = 50000;
                double amount = minRangeAmount + (maxRangeAmount - minRangeAmount + 1) * random.nextDouble();
                int minValidTimes = 1;
                int maxValidTimes = 5;
                int validTimes = random.nextInt(5) + 1;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int minDays = 3;
                int maxDays = 10;
                int days = random.nextInt(7 + 1) + 3;
                calendar.add(Calendar.DAY_OF_YEAR, days);
                Date date1 = calendar.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String startDate = Main.localDateTime.format(Main.dateTimeFormatter);
                String endDate = simpleDateFormat.format(date1);
                ArrayList<Integer> usernamesNumbers = random.ints(0, numberOfCustomers()).limit(numberOfCustomers() / 6).boxed().collect(Collectors.toCollection(ArrayList::new));
                ArrayList<Account> saleAccounts = new ArrayList<>();
                String customerPath = "Resources/Accounts/Customer";
                File customerFolder = new File(customerPath);
                FileFilter fileFilter = new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if (file.getName().endsWith(".json")) {
                            return true;
                        }
                        return false;
                    }
                };
                File[] listFiles = customerFolder.listFiles(fileFilter);
                for (int count = 0; count < listFiles.length; count++) {
                    if (usernamesNumbers.contains(count)) {
                        File i = listFiles[count];
                        String fileName = i.getName();
                        String username = fileName.replace(".json", "");
                        Customer customer = (Customer) GetDataFromDatabase.getAccount(username);
                        saleAccounts.add(customer);
                    }
                }
                Sale sale = new Sale(startDate, endDate, percent, amount, validTimes, saleAccounts);
                AdminController.addSale(sale);

            }
        }
    }

    private static boolean isOkToRun() {
        if (numberOfCustomers() == 0) {
            return false;
        }
        return true;
    }

    private static int numberOfCustomers() {
        String customerPath = "Resources/Accounts/Customer";
        File customerFolder = new File(customerPath);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".json")) {
                    return true;
                }
                return false;
            }
        };
        int number = customerFolder.listFiles(fileFilter).length;
        return number;
    }

    private static boolean offsSet() {
        File file = new File("Resources/Offs");
        if (file.exists()){
            return true;
        }
        return false;
    }

    public static Double randomOff() {
        double minRangeAmount = 10000;
        double maxRangeAmount = 50000;
        Random random = new Random();
        double amount = minRangeAmount + (maxRangeAmount - minRangeAmount + 1) * random.nextDouble();
        return amount;
    }

    public static void removeExpiredOff() throws ExceptionsLibrary.NoOffException, ParseException, ExceptionsLibrary.NoProductException, ExceptionsLibrary.NoAccountException {
        if (offsSet()) {
            Main.localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM");
            String offsPath = "Resources/Offs";
            File offsFolder = new File(offsPath);
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.getName().endsWith(".json")) {
                        return true;
                    }
                    return false;
                }
            };
            for (File i : offsFolder.listFiles(fileFilter)) {
                String offId = i.getName().replace(".json", "");
                Off off = GetDataFromDatabase.getOff(Integer.parseInt(offId));
                Date offEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(off.getEndDate());
                Date date = new Date();
                if (date.compareTo(offEndDate) >= 0){
                    SetDataToDatabase.removeOff(off.getOffId());
                    i.delete();
                }
            }
        }
    }
}
