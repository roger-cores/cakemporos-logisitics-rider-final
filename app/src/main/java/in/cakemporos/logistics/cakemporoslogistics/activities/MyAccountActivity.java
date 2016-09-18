package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.events.OnWebServiceCallDoneEventListener;
import in.cakemporos.logistics.cakemporoslogistics.web.endpoints.AuthenticationEndPoint;
import in.cakemporos.logistics.cakemporoslogistics.web.services.AuthenticationService;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Baker;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Rider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayContingencyError;
import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayError;
import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayMessage;

/**
 * Created by maitr on 14-Aug-16.
 */
public class MyAccountActivity extends BaseActivity implements OnWebServiceCallDoneEventListener {
    private ImageButton home;
    private TextView email_baker,address_baker,phone_baker;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        //find views
        home=(ImageButton)findViewById(R.id.home_img_button_my_account);
        email_baker=(TextView)findViewById(R.id.email_baker_ma);
        address_baker=(TextView)findViewById(R.id.address_baker_ma);
        phone_baker=(TextView)findViewById(R.id.phone_baker_ma);
        //onclick
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        retrofit=new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthenticationEndPoint endPoint = retrofit.create(AuthenticationEndPoint.class);

        //Below line was giving error...
        //I have tried to remove
        // Please check this in case I missed something
        //
        AuthenticationService.getMyInfo(this, retrofit, endPoint, this);
        //End

    }

    @Override
    public void onDone(int message_id, int code, Object... args) {
        displayMessage(this, "Success", Snackbar.LENGTH_LONG);

        if(args.length>0) {
            Rider rider = (Rider) args[0];

            //here is baker info
            phone_baker.setText(rider.getUser().getPhone());
            email_baker.setText(rider.getUser().getEmail());
            address_baker.setText(rider.getVehicleNumber());
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Toast.makeText(this,"Password successfully updated",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void changePassword(View view)
    {
        Intent intent=new Intent(this,ChangePasswordActivity.class);
        intent.putExtra("email_rider", email_baker.getText());
        startActivityForResult(intent,1);
    }

}
