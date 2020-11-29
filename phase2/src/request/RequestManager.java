package request;

import user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The RequestManager class is responsible for handling request-related actions. allUserRequests is a map that maps
 * usernames to all of their request objects. requestStatus is a map that maps request statuses to their request
 * objects.
 */
public class RequestManager {
    protected HashMap<String, List<Request>> allRequests;
    protected HashMap<String, List<Request>> requestStatus;
    /**
     * This constructs a RequestManager object with an empty allRequests, and an empty requestStatus that is given it's
     * keys.
     */
    public RequestManager(){
        this.allRequests = new HashMap<String, List<Request>>();
        this.requestStatus = new HashMap<String, List<Request>>();
        this.requestStatus.put("pending", new ArrayList<Request>());
        this.requestStatus.put("addressed", new ArrayList<Request>());
        this.requestStatus.put("rejected", new ArrayList<Request>());
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
        this.requestStatus.get("pending").add(request);
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
    public boolean checkIsValidStatus(String status){
        return status.equals("pending") || status.equals("addressed") || status.equals("rejected");
    }

    /**
     * Updates the request status of a request in a hashmap
     * @param request refers to the request being changed
     * @param status refers to the new status of the request
     */
    public void updateRequestStatus(Request request, String status){
        String user = request.getRequesterUsername();
        List<Request> userRequest = this.allRequests.get(user);
        for (Request r: userRequest){
            if(r.equals(request)){
                r.editStatus(status);
            }
        }
        this.requestStatus.remove("pending", request);
        request.editStatus(status);
        this.requestStatus.get(status).add(request);
    }

    /**
     * The method gets all the Requests
     * @return refers to the map allRequests
     */
    public HashMap<String, List<Request>> getAllRequests(){
        return allRequests;
    }

    /**
     * The method adds a new username and list of request objects to the map
     * @param username refers to the username of the user.
     */
    public void addUserRequests(String username){
        this.allRequests.put(username, new ArrayList<Request>());
    }

    /**
     * The method gets all requests with a status
     * @param status refers to the status being wanted
     * @return refers to a list containing all requests with indicated status
     */
    public List<Request> getStatusRequests(String status){
        return this.allRequests.get(status);
    }

    /**
     * The method gets all requests associated with a user
     * @param username refers to the username of the user who's requests we want to see
     * @return refers to a list containing all of user's requests
     */
    public List<Request> getUserRequests(String username){
        return this.allRequests.get(username);
    }
}