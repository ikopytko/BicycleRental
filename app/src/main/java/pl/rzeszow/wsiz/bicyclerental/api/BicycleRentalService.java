package pl.rzeszow.wsiz.bicyclerental.api;

import java.util.List;

import pl.rzeszow.wsiz.bicyclerental.api.model.InfoResponse;
import pl.rzeszow.wsiz.bicyclerental.api.model.Point;
import pl.rzeszow.wsiz.bicyclerental.api.model.Reservation;
import pl.rzeszow.wsiz.bicyclerental.api.model.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public interface BicycleRentalService {

    @GET("/GetRentalPoints/")
    void getRentalPoints(Callback<List<Point>> dataCallback);

    @GET("/GetAllReservations/")
    void getUserReservations(@Query("userId") long userId, Callback<List<Reservation>> response);

    @GET("/CancelReservation/")
    void cancelReservation(@Query("userId") long userId, @Query("reservationId") long reservationId, Callback<InfoResponse> response);

    @POST("/Register/")
    void register(@Body User user, Callback<InfoResponse> response);

    @POST("/Login/")
    void login(@Query("login") String login, @Body String password, Callback<User> response);

    @POST("/ReserveBike/")
    void reserve(@Body Reservation reservation, Callback<InfoResponse> response);

}
