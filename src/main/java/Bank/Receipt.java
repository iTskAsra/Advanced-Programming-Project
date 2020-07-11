package Bank;

import java.util.concurrent.ThreadLocalRandom;

import static Bank.BankInitialize.receipts;

public class Receipt {
    private int id;
    private String receiptType;
    private double money;
    private int sourceId;
    private int destinationId;
    private String description;
    private boolean paid;

    public Receipt(String receiptType, double money, int sourceId, int destinationId, String description) {
        this.receiptType = receiptType;
        this.money = money;
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.description = description;
        this.paid = false;
        this.id = ThreadLocalRandom.current().nextInt(10000,100000);
        while (checkReceiptsIds(id)){
            this.id = ThreadLocalRandom.current().nextInt(10000,100000);
        }

    }

    private boolean checkReceiptsIds(int id) {
        for (Receipt i : receipts){
            if (i.getId() == id){
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
