package se.ericwenn.reseplaneraren.services;

import java.util.Map;

/**
 * Created by ericwenn on 7/14/16.
 */

public interface IRestClient {


    void addHeader( String key, String value);
    void clearHeaders();
    /**
     * Send a GET request to the server
     * @param url Endpoint
     * @param paramsMap GET Parameters
     * @param action Handler for response
     */
    void get(String url, Map<String,String> paramsMap, IResponseAction action);

    /**
     * Send a GET request to the server
     * @param url Endpoint
     * @param action Handler for response
     */
    void get(String url, IResponseAction action);




    /**
     * Send a POST request to the server
     * @param url Endpoint
     * @param action Handler for response
     */
    void post(String url, IResponseAction action);

    /**
     * Send a POST request to the server
     * @param url Endpoint
     * @param paramsMap POST Parameters
     * @param action Handler for response
     */
    void post(String url, Map<String,String> paramsMap, IResponseAction action);






    /**
     * Send a PUT request to the server
     * @param url Endpoint
     * @param action Handler for response
     */
    void put(String url, IResponseAction action);

    /**
     * Send a PUT request to the server
     * @param url Endpoint
     * @param paramsMap PUT Parameters
     * @param action Handler for response
     */
    void put(String url, Map<String,String> paramsMap, IResponseAction action);




    /**
     * Send a DELETE request to the server
     * @param url Endpoint
     * @param action Handler for response
     */
    void delete(String url, IResponseAction action);

    /**
     * Send a DELETE request to the server
     * @param url Endpoint
     * @param paramsMap DELETE Parameters
     * @param action Handler for response
     */
    void delete(String url, Map<String,String> paramsMap, IResponseAction action);

}

