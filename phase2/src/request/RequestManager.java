package request;

import saver.ReaderWriter;
import saver.ReaderWriter;
import user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The RequestManager class is responsible for handling request-related actions. allRequests is a map that maps
 * usernames to all of their request objects. requestStatus is a map that maps request statuses to their request
 * objects.
 */

public class RequestManager {

    protected HashMap<String, List<Request>> allRequests;
    protected HashMap<String, List<Request>> requestStatus;
    ReaderWriter RW;

    /**
     * This constructs a RequestManager object with an empty allRequests, and an empty requestStatus that is given it's
     * keys.
     */
    public RequestManager( ReaderWriter RW){
        this.allRequests = new HashMap<String, List<Request>>();
        this.requestStatus = new HashMap<String, List<Request>>();
        this.requestStatus.put("pending", new ArrayList<Request>());
        this.requestStatus.put("addressed", new ArrayList<Request>());
        this.requestStatus.put("rejected", new ArrayList<Request>());
        this.RW = RW;
    }

    public RequestManager(){
        this.allRequests = new HashMap<String, List<Request>>();
        this.requestStatus = new HashMap<String, List<Request>>();
        this.requestStatus.put("pending", new ArrayList<Request>());
        this.requestStatus.put("addressed", new ArrayList<Request>());
        this.requestStatus.put("rejected", new ArrayList<Request>());
    }

    // Getter Methods

    /**
     * The method gets all the Requests
     * @return refers to the map allRequests
     */
    public HashMap<String, List<Request>> getAllRequests(){
        return allRequests;
    }

    /**
     * The method gets all the Request Statuses
     * @return refers to the map allRequests
     */
    public HashMap<String, List<Request>> getAllRequestStatuses(){
        return requestStatus;
    }

    /**
     * The method gets all requests with a status
     * @param status refers to the status being wanted
     * @return refers to a list containing all requests with indicated status
     */
    public List<Request> getStatusRequests(String status){
        return this.requestStatus.get(status);
    }

    /**
     * The method gets all requests associated with a user
     * @param username refers to the username of the user who's requests we want to see
     * @return refers to a list containing all of user's requests
     */
    public List<Request> getUserRequests(String username){
        return this.allRequests.get(username);
    }

    /**
     * Gets the status of a request
     * @param request The request we want the status of
     * @return either "pending", "addressed", or "rejected".
     */
    public String getRequestStatus(Request request){return request.getRequestStatus();}

    /**
     * Gets the string content of the request
     * @param request The request we want the content of
     * @return the String content of the request.
     */
    public String getRequestContent(Request request){return request.getContent();}

    // Setter Methods

    /**
     * The method sets the requests HashMap
     */
    public void setAllRequests(HashMap<String, List<Request>> requests){
        this.allRequests = requests;
    }

    /**
     * The method sets the requestStatus HashMap
     */
    public void setAllRequestStatuses(HashMap<String, List<Request>> requestStatus){
        this.requestStatus = requestStatus;
    }

    /**
     * This method sets the map of requests to the deserialized HashMap object containing usernames as keys
     * and the corresponding requests as values.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from users.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find users.
     */
    public void setAllRequestsReadIn() throws IOException, ClassNotFoundException {
        Object uncastedRequests = RW.readRequests();
        HashMap<String, List<Request>> requestMap = (HashMap<String, List<Request>>) uncastedRequests;
        setAllRequests(requestMap);
    }

    /**
     * This method sets the map of request statuses to the deserialized HashMap object containing request statuses as keys
     * and the corresponding request objects as values.
     * @throws IOException Refers to the exception that is raised when the program can't get input or output from users.
     * @throws ClassNotFoundException Refers to the exception that is raised when the program can't find users.
     */
    public void setAllRequestStatusesReadIn() throws IOException, ClassNotFoundException {
        Object uncastedRequestStatuses = RW.readRequestStatuses();
        HashMap<String, List<Request>> requestStatusesMap = (HashMap<String, List<Request>>) uncastedRequestStatuses;
        setAllRequestStatuses(requestStatusesMap);
    }

    // Other Methods

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
     * Creates a new Request object
     * @param content refers to the content of the request
     * @param requesterUsername refers to the username of the requester
     * @param requestStatus refers to the status of the request
     * @return Return the created Request
     */
    public void createNewRequest(String content, String requesterUsername, String requestStatus){
        Request readIn = new Request(content, requesterUsername);
        addRequest(requesterUsername, readIn);
        updateRequestStatus(readIn, requestStatus);
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
     * Determines if the status of the request is valid or not when organizers go to mark it
     * @param status refers to the intended status of the request
     * @return returns if the new status is valid
     */
    public boolean checkIsValidStatus(String status){
        return status.equals("addressed") || status.equals("rejected");
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
        this.requestStatus.get("pending").remove(request);
        request.editStatus(status);
        this.requestStatus.get(status).add(request);
    }

    /**
     * The method adds a new username and list of request objects to the map
     * @param username refers to the username of the user.
     */
    public void addUserRequests(String username){
        this.allRequests.put(username, new ArrayList<Request>());
    }

    public List<String> getRequestInformation(Request request){
        List<String> requestInfo = new ArrayList<>();
        requestInfo.add(getRequestStatus(request));
        requestInfo.add(getRequestContent(request));
        return requestInfo;
    }

    public List<List<String>> getUsersRequestInfo(String username){
        List<Request> requests = getUserRequests(username);
        List<List<String>> usersRequests = new ArrayList<>();
        for(Request request : requests){
            usersRequests.add(getRequestInformation(request));
        }
        return usersRequests;
    }
}