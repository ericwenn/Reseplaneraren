package se.ericwenn.reseplaneraren.services;

public interface IResponseAction {
    void onSuccess(String responseBody);
    void onFailure(int statusCode, String responseBody);
}
