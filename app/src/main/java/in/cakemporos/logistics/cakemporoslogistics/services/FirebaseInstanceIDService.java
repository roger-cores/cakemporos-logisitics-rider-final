package in.cakemporos.logistics.cakemporoslogistics.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Retrofit;

/**
 * Created by roger on 9/17/2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {



    Retrofit retrofit;
    String TAG = FirebaseInstanceIDService.class.getName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(this.getClass().getName(), "Refreshed token: " + refreshedToken);



        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }


}
