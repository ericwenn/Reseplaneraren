package se.ericwenn.reseplaneraren.services;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;

/**
 * RestResponseHandler class that decides what to do with the response.
 */
public class RestResponseHandler extends AsyncHttpResponseHandler implements ResponseHandlerInterface {
    private static final String TAG = "RestResponseHandler";
    private final IResponseAction action;

    public RestResponseHandler(IResponseAction action){
        super();
        this.action = action;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.d(TAG, "onSuccess() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], responseBody = [" + responseBody + "]");
        this.action.onSuccess(new String(responseBody));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        String responseString;

        Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], headers = [" + headers + "], responseBody = [" + responseBody + "], error = [" + error + "]");

        if(responseBody == null || responseBody.length == 0){
            responseString = "";
        } else{
            responseString = new String(responseBody);
        }

        this.action.onFailure(statusCode, responseString);
    }
}
