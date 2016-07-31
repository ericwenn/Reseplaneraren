package se.ericwenn.reseplaneraren.util;

/**
 * Created by ericwenn on 7/31/16.
 */
public interface DataPromise<T> {

    void onResolve( ResolvedHandler<T> l);
    void onReject( RejectedHandler<T> l);

    interface ResolvedHandler<T> {
        void onResolve( T data );
    }

    interface RejectedHandler<T> {
        void onReject( Exception e );
    }
}
