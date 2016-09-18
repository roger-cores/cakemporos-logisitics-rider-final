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
public abstract class BaseActivity extends AppCompatActivity {
    private DeregisterEventReceiver reciever;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        reciever = new DeregisterEventReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(reciever);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IntentFilters.LOGOUT);
        registerReceiver(reciever, filter);
    }
}
