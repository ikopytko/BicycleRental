package pl.rzeszow.wsiz.bicyclerental.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.adapter.ItemsAdapter;
import pl.rzeszow.wsiz.bicyclerental.api.model.InfoResponse;
import pl.rzeszow.wsiz.bicyclerental.api.model.Reservation;
import pl.rzeszow.wsiz.bicyclerental.api.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public class AccountFragment extends BaseFragment implements
        ItemsAdapter.ItemActionListener<Reservation> {

    private ItemsAdapter<Reservation> mAdapter;
    private User user;

    @InjectView(R.id.photo)
    ImageView photo;
    @InjectView(R.id.firstName)
    TextView firstName;
    @InjectView(R.id.lastName)
    TextView lastName;
    @InjectView(R.id.email)
    TextView email;
    @InjectView(R.id.phone)
    TextView phone;

    @InjectView(android.R.id.empty)
    TextView emptyList;
    @InjectView(R.id.reservations_list)
    ListView reservationListView;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getCore().getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.inject(this, view);
        setContent();
        getCore().getApiService().getUserReservations(user.getId(), new Callback<List<Reservation>>() {
            @Override
            public void success(List<Reservation> reservations, Response response) {
                if (reservations != null && reservations.size() > 0) {
                    mAdapter = new ItemsAdapter<>(AccountFragment.this.getActivity(), reservations, AccountFragment.this);
                    reservationListView.setAdapter(mAdapter);
                    emptyList.setVisibility(View.GONE);
                } else {
                    emptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                emptyList.setVisibility(View.VISIBLE);
                showToastMessage(error.getMessage());
            }
        });
        return view;
    }

    private void setContent() {
        Resources res = getResources();

        Bitmap src = BitmapFactory.decodeResource(res, R.drawable.im_person);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, src);
        dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
        photo.setImageDrawable(dr);

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phone.setText(user.getContactPhone());
        email.setText(user.getEmail());
    }

    @Override
    public void onItemAction(final Reservation item) {
        getCore().getApiService().cancelReservation(user.getId(), item.getId(), new Callback<InfoResponse>() {
            @Override
            public void success(InfoResponse infoResponse, Response response) {
                showToastMessage(infoResponse.getMessage());
                if (infoResponse.isOk()) {
                    mAdapter.remove(item);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showToastMessage(error.getMessage());
            }
        });
    }
}
