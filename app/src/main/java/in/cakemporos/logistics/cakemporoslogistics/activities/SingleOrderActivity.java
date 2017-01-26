package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.utilities.EnumFormatter;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Order;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.enums.OrderStatus;

/**
 * Created by Maitreya on 28-Aug-16.
 */
public class SingleOrderActivity extends BaseActivity {
    private static final int REQUEST_CALL_BAKER_PHONE = 0;
    private static final int REQUEST_CALL_CUSTOMER_PHONE = 1;
    private ImageButton home;
    private TextView cake_val_type, cake_val_wt, cake_val_cost,pickup_val_so,customer_val_so,phone_val_so,address_val_so,drop_val_so;
    private TextView order_id_so,order_status_so,pickupDate_so,dropDate_so,bookingDate_so, orderInstructions;
    private Order singleOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baker_specific_order);
        //find views
        home=(ImageButton)findViewById(R.id.single_order_img_button_app_version);
        cake_val_type=(TextView)findViewById(R.id.cake_val_type);
        cake_val_wt=(TextView)findViewById(R.id.cake_val_wt);
        cake_val_cost=(TextView)findViewById(R.id.cake_val_cost);
        pickup_val_so=(TextView)findViewById(R.id.pickup_val_so);
        customer_val_so=(TextView)findViewById(R.id.customer_val_so);
        phone_val_so=(TextView)findViewById(R.id.phone_val_so);
        address_val_so=(TextView)findViewById(R.id.address_val_so);
        drop_val_so=(TextView)findViewById(R.id.drop_val_so);
        order_id_so=(TextView) findViewById(R.id.order_id_order_history_detailed);
        order_status_so=(TextView) findViewById(R.id.order_status_oh_detailed);
        pickupDate_so=(TextView) findViewById(R.id.pickup_date_oh_detailed);
        dropDate_so=(TextView) findViewById(R.id.drop_date_oh_detailed);
        bookingDate_so=(TextView) findViewById(R.id.booking_date_oh_detailed);
        orderInstructions=(TextView) findViewById(R.id.baker_specific_order_instructions);
        //onclick
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //
        Intent past_Intent=getIntent();
        Bundle bundle=past_Intent.getExtras();
        singleOrder= (Order) bundle.getSerializable("current_order");
        //
        Toast.makeText(this,"order id: "+singleOrder.getId(),Toast.LENGTH_SHORT).show();
        //
        //Retrofit code goes here
        //
        /*
        Locality testLocality=new Locality();
        testLocality.setName("Navagaon");
        Customer testCustomer=new Customer();
        testCustomer.setAddress("qwertyuiopas\nsnkjn nkjlkjlkjlkj adansdknklsandksand\nasmdasdsad");
        testCustomer.setFirstName("Maitreya");
        testCustomer.setLastName("Save");
        testCustomer.setLocality(testLocality);
        testCustomer.setPhone(789456123l);
        //
        testOrder.setLocality(testLocality);
        testOrder.setCustomer(testCustomer);
        testOrder.setCakeType(CakeType.CUSTOMIZED);
        testOrder.setCost(300l);
        testOrder.setWeight(OrderWeight.HALF);
        Calendar c=Calendar.getInstance();
        testOrder.setPickUpDate(c.getTime());
        testOrder.setDropDate(c.getTime());
        testOrder.setDropAltPhone(333444555l);
        //
        */
        //Retrofit code ends here
        //
        //Date Conversion
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy @ hh:mm a");
        SimpleDateFormat formatter1 =new SimpleDateFormat("dd-MMM-yyyy");
        //Set values on text views
        String cake_values=singleOrder.getCost()+" "+singleOrder.getCakeType()+" "+singleOrder.getWeight();
        String pickupdate_value=formatter.format(singleOrder.getPickUpDate().getTime());
        String pickupdate_head=formatter1.format(singleOrder.getPickUpDate().getTime());
        String dropdate_head=formatter1.format(singleOrder.getDropDate().getTime());
        String bookingdate_head=formatter1.format(singleOrder.getBookingDate().getTime());
        String customer_value=singleOrder.getCustomer().getFirstName()+" "+singleOrder.getCustomer().getLastName();
        String phone_values=singleOrder.getCustomer().getPhone()+" / "+singleOrder.getDropAltPhone();
        String dropdate_value=formatter.format(singleOrder.getDropDate().getTime());
        cake_val_type.setText(singleOrder.getCakeType().toString());
        cake_val_wt.setText(Float.toString(EnumFormatter.getOrderWeight(singleOrder.getWeight())) + " Kg.");
        cake_val_cost.setText("Rs. " + singleOrder.getCost());
        pickup_val_so.setText(pickupdate_value);
        customer_val_so.setText(customer_value);
        phone_val_so.setText(phone_values);
        address_val_so.setText(singleOrder.getCustomer().getAddress());
        drop_val_so.setText(dropdate_value);
        order_id_so.setText(singleOrder.getOrderCode());
        order_status_so.setText(singleOrder.getStatus().toString());
        pickupDate_so.setText("Pick Up Date\n"+pickupdate_head);
        dropDate_so.setText("Drop Date\n"+dropdate_head);
        bookingDate_so.setText("Booking Date\n"+bookingdate_head);
        orderInstructions.setText(singleOrder.getInstructions());
        //
        if (singleOrder.getStatus().equals(OrderStatus.CANCELLED)){
            order_status_so.setBackgroundColor(Color.RED);
        }
        else if (singleOrder.getStatus().equals(OrderStatus.DISPATCHED)){
            order_status_so.setBackgroundColor(Color.rgb(0,100,0));
        }
        else if(singleOrder.getStatus().equals(OrderStatus.DELIVERED)){
            order_status_so.setBackgroundColor(Color.BLUE);
        }
    }

    public void callBaker(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + singleOrder.getBaker().getUser().getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(SingleOrderActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_BAKER_PHONE);


            return;
        }
        startActivity(callIntent);
    }

    public void callCustomer(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + singleOrder.getCustomer().getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(SingleOrderActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_CUSTOMER_PHONE);


            return;
        }
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_BAKER_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + singleOrder.getBaker().getUser().getPhone()));
                    try {
                        startActivity(callIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case REQUEST_CALL_CUSTOMER_PHONE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + singleOrder.getCustomer().getPhone()));
                    try {
                        startActivity(callIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
