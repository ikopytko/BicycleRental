package pl.rzeszow.wsiz.bicyclerental.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.activity.MainActivity;
import pl.rzeszow.wsiz.bicyclerental.api.model.Point;
import pl.rzeszow.wsiz.bicyclerental.interfaces.PointReservationListener;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class PointsMapFragment extends BaseFragment implements
        MainActivity.PointsLoaderListener,
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback {

    private static final String MAP_OPTIONS = "map:options";

    private CameraPosition cameraPosition;
    private HashMap<Marker, Point> pointHashMap;
    private List<Point> points;
    private PointReservationListener pointReservationListener;

    public static PointsMapFragment newInstance(GoogleMapOptions options) {
        Bundle args = new Bundle();
        args.putParcelable(MAP_OPTIONS, options);
        PointsMapFragment fragment = new PointsMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pointReservationListener = (PointReservationListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cameraPosition = ((GoogleMapOptions) getArguments().getParcelable(MAP_OPTIONS)).getCamera();
        }
        pointHashMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_points_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.pints_map);
        if (fragment != null && fragment instanceof SupportMapFragment) {
            ((SupportMapFragment) fragment).getMapAsync(this);
        }
    }

    @Override
    public void pointsLoaded(List<Point> points) {
        this.points = points;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setInfoWindowAdapter(new PointInfoWindowAdapter(getActivity()));
        googleMap.setOnInfoWindowClickListener(this);

        if (points != null) {
            for (Point p : points) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(p.getPosition()));
                pointHashMap.put(marker, p);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        pointReservationListener.reservePoint(pointHashMap.get(marker));
    }

    class PointInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private LayoutInflater inflater;

        public PointInfoWindowAdapter(Context context) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            View view = inflater.inflate(R.layout.holder_map_info_window, null);
            if (marker != null) {
                Point point = pointHashMap.get(marker);
                ((TextView) view.findViewById(android.R.id.title)).setText(
                        String.format("[%d] %s",
                                point.getId(),
                                point.getName()));
                ((TextView) view.findViewById(R.id.subtitle)).setText(
                        String.format("B:%d %s", point.getMaxBikes(), "Reserve"));
            }
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}

