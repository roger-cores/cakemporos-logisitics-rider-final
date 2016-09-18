package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import in.cakemporos.logistics.cakemporoslogistics.R;

/**
 * Created by maitr on 15-Aug-16.
 */
public class SupportActivity extends BaseActivity {
    private ImageButton home;
    private String manager_phone,manager_email,email_subject,email_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        //find views
        home = (ImageButton) findViewById(R.id.home_img_button_support);
        //onclick
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //
        //set phone and email
        manager_email = "delivery@cakemporos.in";
        email_subject = "Successful Delivery";
        email_text="Cake delivered successfully";
        manager_phone = "9766526943";
    }

    public void callManager(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + manager_phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }
    public void emailManager(View view)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{manager_email});
        email.putExtra(Intent.EXTRA_SUBJECT, email_subject);
        email.putExtra(Intent.EXTRA_TEXT, email_text);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}
