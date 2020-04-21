package model;

import java.util.ArrayList;
import java.util.Random;

public class Request {
    private int requestId;
    private String requestDescription;
    private RequestType requestType;
    private RequestOrCommentCondition requestCondition;
    private Seller requestSeller;

    public Request(String requestDescription, RequestType requestType, RequestOrCommentCondition requestCondition,Seller requestSeller) {
        this.requestDescription = requestDescription;
        this.requestType = requestType;
        this.requestCondition = requestCondition;
        Random random = new Random();
        requestId = random.nextInt(10000);
        this.requestSeller = requestSeller;
    }

    public Seller getRequestSeller() {
        return requestSeller;
    }

    public void setRequestSeller(Seller requestSeller) {
        this.requestSeller = requestSeller;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestOrCommentCondition getRequestCondition() {
        return requestCondition;
    }

    public void setRequestCondition(RequestOrCommentCondition requestCondition) {
        this.requestCondition = requestCondition;
    }
}
