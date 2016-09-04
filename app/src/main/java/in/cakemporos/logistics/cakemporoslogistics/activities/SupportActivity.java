package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import in.cakemporos.logistics.cakemporoslogistics.R;

/**
 * Created by maitr on 15-Aug-16.
 */
public class SupportActivity extends AppCompatActivity {
    private ImageButton home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        //find views
        home=(ImageButton)findViewById(R.id.home_img_button_support);
        //onclick
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
