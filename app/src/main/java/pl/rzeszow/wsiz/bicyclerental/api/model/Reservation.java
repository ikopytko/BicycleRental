package pl.rzeszow.wsiz.bicyclerental.api.model;

import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.adapter.holder.BaseHolder;
import pl.rzeszow.wsiz.bicyclerental.adapter.holder.ReservationHolder;


/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class Reservation implements BaseModel {

    @SerializedName("ReservationId")
    private long Id;
    @SerializedName("PointId")
    private long pointId;
    @SerializedName("ReturnPointId")
    private long returnPointId;
    @SerializedName("UserId")
    private long userId;
    @SerializedName("GiveDate")
    private Date giveDate;
    @SerializedName("ReturnDate")
    private Date returnDate;
    @SerializedName("BikeQuantity")
    private int bikeQuantity;

    public Reservation(long pointId, long userId, Date giveDate, Date returnDate, int bikeQuantity) {
        this.pointId = this.returnPointId = pointId;
        this.userId = userId;
        this.giveDate = giveDate;
        this.returnDate = returnDate;
        this.bikeQuantity = bikeQuantity;
    }

    public long getId() {
        return Id;
    }

    @Override
    public int getLayoutId() {
        return R.layout.holder;
    }

    @Override
    public BaseHolder getHolder(View view) {
        return new ReservationHolder(view);
    }

    @Override
    public BaseHolder getHolder(View view, View.OnClickListener listener) {
        return new ReservationHolder(view, listener);
    }

    public long getPointId() {
        return pointId;
    }

    public long getReturnPointId() {
        return returnPointId;
    }

    public long getUserId() {
        return userId;
    }

    public Date getGiveDate() {
        return giveDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public int getBikeQuantity() {
        return bikeQuantity;
    }
}
