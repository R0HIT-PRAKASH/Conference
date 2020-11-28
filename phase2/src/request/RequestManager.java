package request;

import message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The RequestManager class is responsible for handling request-related actions. allUserRequests is a map that maps
 * usernames to all of their request objects.
 */
public class RequestManager {
    protected HashMap<String, List<Request>> allRequests;

    /**
     * This constructs a RequestManager object with an empty allRequests.
     */
    public RequestManager(){
        this.allRequests = new HashMap<String, List<Request>>();
    }

    /**
     * Creates a new Request object
     * @param content refers to the content of the request
     * @param requesterUsername refers to the username of the requester
     * @return Return the created Request
     */
    public Request createNewRequest(String content, String requesterUsername){
        return new Request(content, requesterUsername);
    }

    /**
     * Adds a new request to the list of all requests a user has made
     * @param username Refers to the username of the user.
     * @param request Refers to the request to be added.
     */
    public void addRequest(String username, Request request){
        this.allRequests.get(username).add(request);
    }

    /**
     * Determines if the content of the Request is valid or not
     * @param content refers to the intended content of the request
     * @return returns if the content is valid (<= 200 chars) or not
     */
    public boolean checkIsValidRequest(String content){
        return content.length() <= 200;
    }

    /**
     * Determines if the status of the request is valid or not
     * @param status refers to the intended status of the request
     * @return returns if the status is valid (is 0, 1, or 2)
     */
    public boolean checkIsValidStatus(int status){
        return status == 0 || status == 1 || status == 2;
    }

    /**
     * The method adds a new username and list of request objects to the map
     * @param username refers to the username of the user.
     */
    public void addUserRequests(String username){
        this.allRequests.put(username, new ArrayList<Request>());
    }

    /**
     * The method gets all the Requests
     * @return refers to the map allRequests
     */
    public HashMap<String, List<Request>> getAllRequests(){
        return allRequests;
    }
}
