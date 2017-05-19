package pl.rzeszow.wsiz.bicyclerental.adapter.holder;

import android.content.Context;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.rzeszow.wsiz.bicyclerental.api.model.Reservation;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class ReservationHolder extends BaseHolder<Reservation> {

    private static final String action = "Cancel";

    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd", new Locale("pl", "PL"));

    public ReservationHolder(View view) {
        super(view);
    }

    public ReservationHolder(View view, View.OnClickListener onActionListener) {
        super(view, onActionListener);
    }

    @Override
    public void bindData(Context context, Reservation item) {
        title.setText(String.format("[#%d] Reservation for %d bikes", item.getId(), item.getBikeQuantity()));
        subtitle.setText(String.format("%s : %s",
                formatter.format(item.getGiveDate()),
                formatter.format(item.getReturnDate())));
        int days = (int) ((item.getReturnDate().getTime() -
                item.getGiveDate().getTime()) / TimeUnit.DAYS.toMillis(1l));
        additional.setText(String.format("%d days", days));
    }

    @Override
    protected String getAction() {
        return action;
    }
}
