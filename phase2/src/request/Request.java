package request;

import java.io.Serializable;

/**
 * This class represents a Request object. Request objects have a string content, username of the requester, and an int
 * requestStatus that contains the status of the request.
 */
public class Request implements Serializable {
    private String content; //limited to 200 characters
    private String requesterUsername;
    private String requestStatus;

    /**
     * This method constructs a Request object
     * @param content The request content
     * @param requesterUsername The requester username
     */
    public Request(String content, String requesterUsername){
        this.content = content;
        this.requesterUsername = requesterUsername;
        this.requestStatus = "pending";
    }

    /**
     * This method constructs a Request object
     * @param content The request content
     * @param requesterUsername The requester username
     * @param requestStatus The status of this request
     */
    public Request(String content, String requesterUsername, String requestStatus){
        this.content = content;
        this.requesterUsername = requesterUsername;
        this.requestStatus = requestStatus;
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
    public String getRequestStatus(){
        return this.requestStatus;
    }

    //Setter Methods

    /**
     * Sets the request status of the request
     * @param newStatus refers to the new Status of the request
     */
    public void editStatus(String newStatus){
        this.requestStatus = newStatus;
    }

    // didn't include setter for content and username, because those should not be changed

    // Other Methods
    /**
     * A method that compares two requests
     * @param r the request being compared to
     * @return If this request and r are equivalent
     */
    public boolean equals(Request r){
        return this.requesterUsername.equals(r.getRequesterUsername()) && this.content.equals(r.getContent());
    }
}