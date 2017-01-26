package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.adapters.OrderAdapter;
import in.cakemporos.logistics.cakemporoslogistics.events.OnWebServiceCallDoneEventListener;
import in.cakemporos.logistics.cakemporoslogistics.services.LocationService;
import in.cakemporos.logistics.cakemporoslogistics.utilities.Factory;
import in.cakemporos.logistics.cakemporoslogistics.web.endpoints.OrderEndPoint;
import in.cakemporos.logistics.cakemporoslogistics.web.services.OrderService;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Order;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.enums.OrderStatus;
import retrofit2.Retrofit;

import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayContingencyError;
import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayError;
import static in.cakemporos.logistics.cakemporoslogistics.utilities.FlashMessage.displayMessage;

/**
 * Created by bloss on 14/8/16.
 */
public class OrderHistoryActivity extends BaseActivity implements OnWebServiceCallDoneEventListener {


    private static final int REQUEST_LOCATION_FINE = 1;

    private Order[] orders;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context ctx=this;
    private ImageButton home;
    private int item_clicked;
    Retrofit retrofit;

    private ProgressBar progressBar;
    private TextView blankMessage;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oh, menu);
        //
        //disable if not dispatched
        if(orders[item_clicked].getStatus()==OrderStatus.DISPATCHED)
            menu.getItem(1).setEnabled(true);
        else
            menu.getItem(1).setEnabled(false);

        if(orders[item_clicked].getStatus()==OrderStatus.DELIVERED){
            menu.getItem(2).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_order_details_oh) {
            Intent intent=new Intent(ctx,SingleOrderActivity.class);
            //Order order = orders[item_clicked];
            Bundle bundle=new Bundle();
            bundle.putSerializable("current_order",orders[item_clicked]);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_change_status_oh){
            //
            //Toast.makeText(ctx,"Change",Toast.LENGTH_SHORT).show();
            //
            Intent intent=new Intent(ctx,ChangeStatusActivity.class);
            startActivityForResult(intent,2);
            return true;
        }
        else if(id == R.id.action_rider_start_oh){
            //TODO: yaha daal Start ka code

            OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
            OrderService.startOrder(OrderHistoryActivity.this, retrofit, endPoint, new OnWebServiceCallDoneEventListener() {
                @Override
                public void onDone(int message_id, int code, Object... args) {
                    if(!isMyServiceRunning(LocationService.class)){
                        Intent serviceIntent = new Intent(OrderHistoryActivity.this, LocationService.class);
                        if (ActivityCompat.checkSelfPermission(OrderHistoryActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(OrderHistoryActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: ask permission

                            ActivityCompat.requestPermissions(OrderHistoryActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_FINE);

                            return;
                        }
                        startService(serviceIntent);
                    }

                }

                @Override
                public void onContingencyError(int code) {
                    displayContingencyError(OrderHistoryActivity.this, 0);
                }

                @Override
                public void onError(int message_id, int code, String... args) {
                    displayError(OrderHistoryActivity.this, message_id, Snackbar.LENGTH_LONG);

                }
            }, orders[item_clicked].getId());



        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        //
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_order_history);
        progressBar = (ProgressBar) findViewById(R.id.activity_order_history_progress);
        blankMessage = (TextView) findViewById(R.id.order_history_blank_message);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new OrderAdapter(orders,this);
        mRecyclerView.setAdapter(mAdapter);

        retrofit = Factory.createClient(getString(R.string.base_url));

        OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
        OrderService.getMyOrders(this, retrofit, endPoint, this);
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);


        home= (ImageButton) findViewById(R.id.home_img_button_order_history);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, final int position) {
                // TODO Handle item click
                TextView status=(TextView)view.findViewById(R.id.order_status_oh);

                Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar_menu_oh);
                setSupportActionBar(toolbar);
                item_clicked=position;

            }
        }));
        //
        //jhol
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -200);
        animation.setDuration(100);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());
        mRecyclerView.startAnimation(animation);

    }
    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            mRecyclerView.clearAnimation();
            mRecyclerView.setPadding(0, 50, 0, 0);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }

    @Override
    public void onDone(int message_id, int code, Object... args) {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        displayMessage(this, "Success", Snackbar.LENGTH_LONG);

        List<Order> orderlist = ((List<Order>) args[0]);
        if(orderlist!=null){
            //here goes orders  \o/
            //                   |
            //                  / \
            Collections.reverse(orderlist);
            orders=orderlist.toArray(new Order[orderlist.size()]);

            ((OrderAdapter)mAdapter).setmDataset(orders);
            if(orders.length==0) blankMessage.setVisibility(View.VISIBLE);
            else blankMessage.setVisibility(View.GONE);

            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onContingencyError(int code) {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        displayContingencyError(this, 0);
    }

    @Override
    public void onError(int message_id, int code, String... args) {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        displayError(this, message_id, Snackbar.LENGTH_LONG);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (resultCode == 2) {
                // TODO Extract the data returned from the child Activity.
                // Customer customerValues= (Customer) data.getSerializableExtra("customer");
                int val=data.getIntExtra("status",-1);
                Toast.makeText(ctx,val+"",Toast.LENGTH_SHORT).show();
                final OrderStatus orderStatus;
                switch (val)
                {
                    case 0:
                        orderStatus=OrderStatus.DELIVERED;

                        OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
                        //TODO Server side
                        OrderService.deliverOrder(OrderHistoryActivity.this, retrofit, endPoint, new OnWebServiceCallDoneEventListener() {
                            @Override
                            public void onDone(int message_id, int code, Object... args) {
                                //successful
                                orders[item_clicked].setStatus(orderStatus);
                                Toast.makeText(ctx,"os: "+orderStatus,Toast.LENGTH_SHORT).show();
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onContingencyError(int code) {
                                displayContingencyError(OrderHistoryActivity.this, 0);
                            }

                            @Override
                            public void onError(int message_id, int code, String... args) {
                                displayError(OrderHistoryActivity.this, message_id, Snackbar.LENGTH_LONG);
                            }
                        }, orders[item_clicked].getId());

                        break;
                    default:
                        orderStatus = OrderStatus.PENDING;
                }
                orders[item_clicked].setStatus(orderStatus);
                mAdapter.notifyDataSetChanged();
                //


                //
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == REQUEST_LOCATION_FINE){
            if(!isMyServiceRunning(LocationService.class)) {
                Intent serviceIntent = new Intent(this, LocationService.class);
                startService(serviceIntent);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
