package model;

import java.util.ArrayList;

public class Request {
    public static ArrayList<Request> allRequests;
    private String requestDescription;
    private String requestType;
    private RequestOrCommentCondition requestCondition;

    public Request(String requestDescription, String requestType, RequestOrCommentCondition requestCondition) {
        this.requestDescription = requestDescription;
        this.requestType = requestType;
        this.requestCondition = requestCondition;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public RequestOrCommentCondition getRequestCondition() {
        return requestCondition;
    }

    public void setRequestCondition(RequestOrCommentCondition requestCondition) {
        this.requestCondition = requestCondition;
    }
}
