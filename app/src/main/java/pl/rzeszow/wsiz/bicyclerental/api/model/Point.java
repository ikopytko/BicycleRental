package pl.rzeszow.wsiz.bicyclerental.api.model;

import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.adapter.holder.BaseHolder;
import pl.rzeszow.wsiz.bicyclerental.adapter.holder.PointHolder;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class Point implements ClusterItem, BaseModel {

    private LatLng position;

    @SerializedName("PointId")
    private long pointId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Address")
    private String address;
    @SerializedName("MaxBikes")
    private int maxBikes;
    @SerializedName("GPSLat")
    private double gpsLat;
    @SerializedName("GPSLng")
    private double gpsLng;

    @Override
    public LatLng getPosition() {
        if (position == null)
            position = new LatLng(gpsLat, gpsLng);
        return position;
    }

    @Override
    public long getId() {
        return pointId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.holder;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getMaxBikes() {
        return maxBikes;
    }

    public double getGpsLat() {
        return gpsLat;
    }

    public double getGpsLng() {
        return gpsLng;
    }

    @Override
    public BaseHolder getHolder(View view) {
        return new PointHolder(view);
    }

    @Override
    public BaseHolder getHolder(View view, View.OnClickListener listener) {
        return new PointHolder(view, listener);
    }

}
