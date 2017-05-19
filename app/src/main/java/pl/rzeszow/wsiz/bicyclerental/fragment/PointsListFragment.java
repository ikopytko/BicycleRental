package pl.rzeszow.wsiz.bicyclerental.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.activity.MainActivity;
import pl.rzeszow.wsiz.bicyclerental.adapter.ItemsAdapter;
import pl.rzeszow.wsiz.bicyclerental.api.model.Point;
import pl.rzeszow.wsiz.bicyclerental.interfaces.PointReservationListener;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public class PointsListFragment extends BaseFragment implements
        MainActivity.PointsLoaderListener,
        ItemsAdapter.ItemActionListener<Point> {

    private PointReservationListener pointReservationListener;
    private ItemsAdapter<Point> mAdapter;
    @InjectView(R.id.points_list)
    ListView pointViewView;

    public static PointsListFragment newInstance() {
        return new PointsListFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pointReservationListener = (PointReservationListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_points_list, container, false);
        ButterKnife.inject(this, view);
        if (mAdapter != null) {
            pointViewView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void pointsLoaded(List<Point> points) {
        mAdapter = new ItemsAdapter<>(getActivity(), points, this);
        if (pointViewView != null)
            pointViewView.setAdapter(mAdapter);
    }

    @Override
    public void onItemAction(Point item) {
        pointReservationListener.reservePoint(item);
    }
}
