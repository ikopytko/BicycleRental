package pl.rzeszow.wsiz.bicyclerental.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.rzeszow.wsiz.bicyclerental.Config;
import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.api.model.InfoResponse;
import pl.rzeszow.wsiz.bicyclerental.api.model.Point;
import pl.rzeszow.wsiz.bicyclerental.api.model.Reservation;
import pl.rzeszow.wsiz.bicyclerental.fragment.PointsListFragment;
import pl.rzeszow.wsiz.bicyclerental.fragment.PointsMapFragment;
import pl.rzeszow.wsiz.bicyclerental.interfaces.PointReservationListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public class MainActivity extends BaseActivity implements
        AdapterView.OnItemSelectedListener,
        SlideDateTimeListener,
        PointReservationListener {

    private Point pointToReserve;
    private SlideDateTimePicker reservationPicker;

    private List<Point> points;
    private GoogleMapOptions mapOptions;

    @Override
    protected boolean hasToolBar() {
        return true;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setUpToolbarSpinner(AppBarItem.values(), this);
        mapOptions = new GoogleMapOptions()
                .camera(new CameraPosition(Config.RZESZOW, 13, 0, 0));
        initPicker();
    }

    private void initPicker() {
        reservationPicker = new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(this)
                .setInitialDate(new Date())
                .setMinDate(new Date())
                .setTheme(SlideDateTimePicker.HOLO_LIGHT)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_account:
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        AppBarItem selected = (AppBarItem) parent.getAdapter().getItem(position);
        Fragment fragment = null;
        switch (selected) {
            case LIST:
                fragment = PointsListFragment.newInstance();
                break;
            case MAP:
                fragment = PointsMapFragment.newInstance(mapOptions);
                break;
        }
        pushFragment(fragment, false);

        if (points == null) {
            getCore().getApiService().getRentalPoints(new Callback<List<Point>>() {
                @Override
                public void success(List<Point> points, Response response) {
                    MainActivity.this.points = points;
                    updateFragmentView();
                }

                @Override
                public void failure(RetrofitError error) {
                    showMessage(error.getMessage());
                }
            });
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (points != null) {
            updateFragmentView();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateFragmentView() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof PointsLoaderListener) {
            ((PointsLoaderListener) fragment).pointsLoaded(this.points);
        }
    }

    @Override
    public void reservePoint(Point point) {
        pointToReserve = point;
        reservationPicker.setTitle(point.getName());
        reservationPicker.setMaxQuantity(point.getMaxBikes());
        reservationPicker.show();
    }

    @Override
    public void onDateTimeSet(Date fromDate, Date toDate, int quantity) {
        Reservation reservation = new Reservation(
                pointToReserve.getId(),
                getCore().getUser().getId(),
                fromDate,
                toDate,
                quantity);

        getCore().getApiService().reserve(reservation, new Callback<InfoResponse>() {
            @Override
            public void success(InfoResponse infoResponse, Response response) {
                showMessage(infoResponse.getMessage());
                pointToReserve = null;
            }

            @Override
            public void failure(RetrofitError error) {
//                Log.v("failure", ErrorReader.getDetailsOnError(error));
                showMessage(error.getMessage());
                pointToReserve = null;
            }
        });
    }

    @Override
    public void onDateTimeCancel() {
        pointToReserve = null;
    }

    public interface PointsLoaderListener {
        void pointsLoaded(List<Point> points);
    }

    public enum AppBarItem {
        LIST(R.string.rentalPoints),
        MAP(R.string.rentalMap);

        public int nameRes;

        AppBarItem(int nameRes) {
            this.nameRes = nameRes;
        }
    }
}
