package in.cakemporos.logistics.cakemporoslogistics.web.endpoints;

import java.util.List;

import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.Location;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.Response;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.EntityBase;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by roger on 10/8/16.
 */
public interface OrderEndPoint {
    @GET("user/rider/order")
    public Call<List<Order>> getMyOrders(@Header("x-access-token") String accessToken);

    @PUT("user/rider/order/{orderid}/deliver")
    public Call<Response> deliverOrder(@Header("x-access-token") String accessToken,@Path("orderid") String id);

    @PUT("user/rider/order/{orderid}/location")
    public Call<Response> sendLocation(@Header("x-access-token") String accessToken, @Path("orderid") String id, @Body Location location);




}
