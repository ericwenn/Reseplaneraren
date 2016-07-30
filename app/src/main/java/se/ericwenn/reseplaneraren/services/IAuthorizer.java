package se.ericwenn.reseplaneraren.services;

/**
 * Created by ericwenn on 7/20/16.
 */
public interface IAuthorizer {

    void authorize( IRestClient client, AuthorizationListener l);

    interface AuthorizationListener {
        void onAuthorized( IRestClient client );
    }

}
