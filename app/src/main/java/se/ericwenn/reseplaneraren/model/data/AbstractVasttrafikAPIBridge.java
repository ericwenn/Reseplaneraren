package se.ericwenn.reseplaneraren.model.data;

import se.ericwenn.reseplaneraren.services.IRestClient;
import se.ericwenn.reseplaneraren.services.RestClient;

/**
 * Created by ericwenn on 7/20/16.
 */
public abstract class AbstractVasttrafikAPIBridge implements IVasttrafikAPIBridge {

    protected IRestClient getClient() {
        return RestClient.getInstance();
    }
}
