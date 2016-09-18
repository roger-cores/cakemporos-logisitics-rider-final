package in.cakemporos.logistics.cakemporoslogistics.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import in.cakemporos.logistics.cakemporoslogistics.R;
import in.cakemporos.logistics.cakemporoslogistics.adapters.OrderAdapter;
import in.cakemporos.logistics.cakemporoslogistics.events.OnWebServiceCallDoneEventListener;
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
    private Order[] orders;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context ctx=this;
    private ImageButton home;
    Retrofit retrofit;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oh, menu);
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
            Toast.makeText(this,"Order Details",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        //
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_order_history);

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
        //
        home= (ImageButton) findViewById(R.id.home_img_button_order_history);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //

        //
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, final int position) {
                // TODO Handle item click
                TextView status=(TextView)view.findViewById(R.id.order_status_oh);

                // Ok
//                menu_dots.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(final View view) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
//                        builder.setTitle("Confirm Status Change");
//                        builder.setMessage("Are you sure you want to DELIVER this order?");
//
//                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
//                                OrderService.deliverOrder(OrderHistoryActivity.this, retrofit, endPoint, new OnWebServiceCallDoneEventListener() {
//                                    @Override
//                                    public void onDone(int message_id, int code, Object... args) {
//                                        //successful
//                                        orders[position].setStatus(OrderStatus.DEL);
//                                        mAdapter.notifyDataSetChanged();
//                                    }
//
//                                    @Override
//                                    public void onContingencyError(int code) {
//                                        displayContingencyError(OrderHistoryActivity.this, 0);
//                                    }
//
//                                    @Override
//                                    public void onError(int message_id, int code, String... args) {
//                                        displayError(OrderHistoryActivity.this, message_id, Snackbar.LENGTH_LONG);
//                                    }
//                                }, orders[position].getId());
//
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Do nothing
//                                dialog.dismiss();
//                            }
//                        });
//
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                    }
//                });
            }
        }));


    }

    @Override
    public void onDone(int message_id, int code, Object... args) {
        displayMessage(this, "Success", Snackbar.LENGTH_LONG);

        List<Order> orderlist = ((List<Order>) args[0]);
        if(orderlist!=null){
            //here goes orders  \o/
            //                   |
            //                  / \
            Collections.reverse(orderlist);
            orders=orderlist.toArray(new Order[orderlist.size()]);
            ((OrderAdapter)mAdapter).setmDataset(orders);
            mAdapter.notifyDataSetChanged();

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
