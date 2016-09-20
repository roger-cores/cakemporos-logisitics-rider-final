package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import in.cakemporos.logistics.cakemporoslogistics.receivers.DeregisterEventReceiver;
import in.cakemporos.logistics.cakemporoslogistics.staticvals.IntentFilters;

/**
 * Created by roger on 9/18/2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        DeregisterEventReceiver reciever;
        reciever = new DeregisterEventReceiver();
        try {
            unregisterReceiver(reciever);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(IntentFilters.LOGOUT);
//        registerReceiver(reciever, filter);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        DeregisterEventReceiver reciever;
        reciever = new DeregisterEventReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IntentFilters.LOGOUT);
        registerReceiver(reciever, filter);
    }
}
