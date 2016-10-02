package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Order;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.enums.OrderStatus;

/**
 * Created by Maitreya on 28-Aug-16.
 */
public class SingleOrderActivity extends AppCompatActivity {
    private ImageButton home;
    private TextView cake_val_so,pickup_val_so,customer_val_so,phone_val_so,address_val_so,drop_val_so;
    private TextView order_id_so,order_status_so,pickupDate_so,dropDate_so,bookingDate_so;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baker_specific_order);
        //find views
        home=(ImageButton)findViewById(R.id.single_order_img_button_app_version);
        cake_val_so=(TextView)findViewById(R.id.cake_val_so);
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
        Order singleOrder= (Order) bundle.getSerializable("current_order");
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
        cake_val_so.setText(cake_values);
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
}
