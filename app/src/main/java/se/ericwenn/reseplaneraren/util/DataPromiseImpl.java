package se.ericwenn.reseplaneraren.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericwenn on 7/31/16.
 */
public class DataPromiseImpl<T> implements DataPromise<T> {

    private List<ResolvedHandler<T>> resolveHandlers = new ArrayList<>();
    private List<RejectedHandler<T>> rejectHandlers = new ArrayList<>();

    private T resolvedData = null;
    private Exception rejection = null;


    @Override
    public void onResolve(ResolvedHandler<T> l) {
        if( resolvedData != null && !resolveHandlers.contains(l) ) {
            l.onResolve(resolvedData);
        }
        resolveHandlers.add(l);
    }

    @Override
    public void onReject(RejectedHandler<T> l) {
        if(rejection != null && !rejectHandlers.contains(l)) {
            l.onReject(rejection);
        }
        rejectHandlers.add(l);
    }

    public void resolveData( T data ) {
        resolvedData = data;
        for( ResolvedHandler<T> r : resolveHandlers ) {
            r.onResolve(data);
        }
    }

    public void rejectData( Exception e ) {
        for( RejectedHandler<T> r : rejectHandlers ) {
            r.onReject(e);
        }
    }
}
