package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.events.OnWebServiceCallDoneEventListener;
import in.cakemporos.logistics.cakemporoslogistics.utilities.Factory;
import in.cakemporos.logistics.cakemporoslogistics.web.endpoints.AuthenticationEndPoint;
import in.cakemporos.logistics.cakemporoslogistics.web.services.AuthenticationService;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.UserInfo;
import retrofit2.Retrofit;

import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayContingencyError;
import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayError;
import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayMessage;

/**
 * Created by maitr on 14-Aug-16.
 */
public class MyAccountActivity extends AppCompatActivity implements OnWebServiceCallDoneEventListener{
    private ImageButton home;
    private TextView user_name_ma,phone_ma, vehicle_number_ma;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        //find views
        home=(ImageButton)findViewById(R.id.home_img_button_my_account);
        user_name_ma= (TextView) findViewById(R.id.user_name_ma);
        phone_ma=(TextView) findViewById(R.id.phone_no_ma);
        vehicle_number_ma =(TextView) findViewById(R.id.address_ma);
        //onclick
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        retrofit = Factory.createClient(getString(R.string.base_url));
        AuthenticationEndPoint endPoint = retrofit.create(AuthenticationEndPoint.class);
        AuthenticationService.getUserInfo(this, retrofit, endPoint, this);

    }
    public void changePass(View view)
    {
        Toast.makeText(this,"Change pwd",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDone(int message_id, int code, Object... args) {
        displayMessage(this, "Success", Snackbar.LENGTH_LONG);

        UserInfo userInfo = (UserInfo) args[0];

        if(userInfo!=null){
            //here
            user_name_ma.setText(userInfo.getEmail());
            phone_ma.setText(userInfo.getPhone().toString());
            vehicle_number_ma.setText(userInfo.getRider().getVehicleNumber());
        }
    }

    @Override
    public void onContingencyError(int code) {
        displayContingencyError(this, 0);
    }

    @Override
    public void onError(int message_id, int code, String... args) {
        displayError(this, message_id, Snackbar.LENGTH_LONG);
    }
}
