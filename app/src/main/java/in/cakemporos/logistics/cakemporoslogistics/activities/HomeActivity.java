package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.events.OnWebServiceCallDoneEventListener;
import in.cakemporos.logistics.cakemporoslogistics.receivers.DeregisterEventReceiver;
import in.cakemporos.logistics.cakemporoslogistics.staticvals.IntentFilters;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maitr on 29-Jul-16.
 */
public class HomeActivity extends BaseActivity {
    private View order_history,my_account,support,app_ver;
    private Context ctx_home=this;

    private DeregisterEventReceiver reciever;


    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //get views
        reciever = new DeregisterEventReceiver();

        order_history= findViewById(R.id.grid_1);
        my_account= findViewById(R.id.grid_2);
        support= findViewById(R.id.grid_3);
        app_ver= findViewById(R.id.grid_4);

        //set onlClicks
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_book=new Intent(ctx_home,SupportActivity.class);
                startActivity(intent_book);
            }
        });

        order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_book=new Intent(ctx_home,OrderHistoryActivity.class);
                startActivity(intent_book);
            }
        });
        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_book=new Intent(ctx_home,MyAccountActivity.class);
                startActivity(intent_book);
            }
        });
        app_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_book=new Intent(ctx_home,AppVersionActivity.class);
                startActivity(intent_book);
            }
        });

        //Web Services
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();





    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//
//    }
//
////    @Override
////    protected void onStart() {
////        super.onStart();
////        IntentFilter filter = new IntentFilter();
////        filter.addAction(IntentFilters.LOGOUT);
////        registerReceiver(reciever, filter);
////    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
}
