package request;

import java.util.List;

/**
 * This class represents a Request object. Request objects have a string content, username of the requester, and an int
 * requestStatus that contains the status of the request.
 */
public class Request {
    private String content; //limited to 200 characters
    private String requesterUsername;
    private int requestStatus; //requestStatus must be either 0, 1, or 2

    /**
     * This method constructs a Request object
     * @param content The request content
     * @param requesterUsername The requester username
     */
    public Request(String content, String requesterUsername){
        this.content = content;
        this.requesterUsername = requesterUsername;
        this.requestStatus = 0;
    }

    // Getter Methods
    /**
     * Gets the String content of the request.
     * @return Returns the String content of the request.
     */
    public String getContent(){
        return this.content;
    }

    /**
     * Gets the requester of the request
     * @return Returns the String username of the individual who made the request
     */
    public String getRequesterUsername(){
        return this.requesterUsername;
    }

    /**
     * Gets the status of the request
     * @return Returns the int status of the request (0 = pending, 1 = addressed, 2 = rejected)
     */
    public int getRequestStatus(){
        return this.requestStatus;
    }

    //Setter Methods
    /**
     * Sets the request status of the request
     * @param newStatus refers to the new Status of the request
     */
    public void editStatus(int newStatus){
        this.requestStatus = newStatus;
    }
}