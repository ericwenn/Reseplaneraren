package se.ericwenn.reseplaneraren.services;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.Base64;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by ericwenn on 7/20/16.
 */
public class VasttrafikAuthorizer implements IAuthorizer {

    private static final String key = "7hXzaFPPYvLCxD0IqnPNa3to5aca";
    private static final String secret = "IGijc3btOsBlAVMgnnYEovUHmNAa";
    private static final String TAG = "VasttrafikAuthorizer";

    private static VasttrafikAuthorizer instance = null;
    private VasttrafikToken token = null;





    class VasttrafikToken {
        public String scope;
        public String token_type;
        public int expires_in;
        public String access_token;
        public Date expiration;

        public String getToken() {
            return access_token;
        }

        public boolean isValid() {
            Date d = new Date();
            return d.before(expiration);
        }
        public void calculateExpiresIn() {
            Date d = new Date();
            d.setTime(d.getTime() + expires_in*1000);
            expiration = d;
        }
    }

    public static synchronized VasttrafikAuthorizer getInstance() {
        if( instance == null) {
            instance = new VasttrafikAuthorizer();
        }
        return instance;
    }


    @Override
    public void authorize(IRestClient client, AuthorizationListener l) {
        if( token == null || !token.isValid()) {
            Log.d(TAG, "Token is not valid, attempting to authorize....");
            getToken(client, l);
        } else {
            client.addHeader("Authorization", "Bearer "+token.getToken());
            l.onAuthorized(client);
        }

    }


    private void getToken(final IRestClient client, final AuthorizationListener l) {
        String base64 = Base64.encodeToString((key+":"+secret).getBytes(), Base64.DEFAULT);


        // TODO
        // Problems with 3rd lib asynchttp
        // Adding fake-parameters before and after the actual parameter solves the problem for now
        HashMap<String,String> p = new HashMap<>();
        p.put("grant_type_fake", "fake");
        p.put("grant_type", "client_credentials");
        p.put("f_grant_type_fake2", "fake");

        client.addHeader("Authorization", "Basic "+base64);


        // http://httpbin.org/post
        // https://api.vasttrafik.se/token
        client.post("token", p, new IResponseAction() {


            @Override
            public void onSuccess(String responseBody) {
                Gson g = new Gson();
                token = g.fromJson(responseBody, VasttrafikToken.class);
                token.calculateExpiresIn();

                client.clearHeaders();
                client.addHeader("Authorization", "Bearer "+token.getToken());


                l.onAuthorized(client);
            }

            @Override
            public void onFailure(int statusCode, String responseBody) {
                Log.e(TAG, "Authorization failed with status ["+statusCode+"] and responseBody ["+responseBody+"]");
            }
        });
    }
}
