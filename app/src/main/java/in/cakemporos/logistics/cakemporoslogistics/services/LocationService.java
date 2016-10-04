package in.cakemporos.logistics.cakemporoslogistics.services;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.events.OnWebServiceCallDoneEventListener;
import in.cakemporos.logistics.cakemporoslogistics.utilities.Factory;
import in.cakemporos.logistics.cakemporoslogistics.web.endpoints.OrderEndPoint;
import in.cakemporos.logistics.cakemporoslogistics.web.services.OrderService;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Order;
import retrofit2.Retrofit;

/**
 * Created by roger on 9/30/2016.
 */
public class LocationService extends Service implements OnWebServiceCallDoneEventListener {

    MyLocationListener locationListener;
    LocationManager locationManager;
    Retrofit retrofit;
    OrderEndPoint endPoint;
    final String TAG = LocationService.class.getSimpleName();

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Location Service Stopped");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Here inside onCreate");

        locationListener = new MyLocationListener();
        retrofit = Factory.createClient(getString(R.string.base_url));
        endPoint = retrofit.create(OrderEndPoint.class);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: ask permission

            return;
        }
        Location mostRecentLocation = locationManager.getLastKnownLocation(provider);
        if(mostRecentLocation!=null)
            OrderService.sendLocation(this, retrofit, endPoint, this, mostRecentLocation.getLatitude(), mostRecentLocation.getLongitude());




    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: ask permission

            return START_STICKY;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 45000, 0, locationListener);
        Log.d(TAG, "Here after onCreate");

        return START_STICKY;
    }

    @Override
    public void onDone(int message_id, int code, Object... args) {
        Log.d(TAG, getString(message_id));
        if(args != null && args.length != 0 && args[0] != null && args[0] instanceof Boolean){
            Boolean close = (Boolean) args[0];
            if(close) this.stopSelf();
        }
    }

    @Override
    public void onContingencyError(int code) {
        Log.d(TAG, getString(R.string.error_contingency));
    }

    @Override
    public void onError(int message_id, int code, String... args) {
        Log.d(TAG, getString(message_id));
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(final Location loc) {

            Handler handler = new Handler(Looper.getMainLooper());

            handler.post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(LocationService.this.getApplicationContext(), "Location Changed: " + loc.getLatitude() + " , " + loc.getLongitude(),Toast.LENGTH_SHORT).show();
                }
            });



            Log.d(TAG, "Updating location: " + loc.getLatitude() + " , " + loc.getLongitude());
            OrderService.sendLocation(LocationService.this, retrofit, endPoint, LocationService.this, loc.getLatitude(), loc.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}



    }
}
