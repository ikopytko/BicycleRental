package pl.rzeszow.wsiz.bicyclerental.adapter.holder;

import android.content.Context;
import android.view.View;

import pl.rzeszow.wsiz.bicyclerental.api.model.Point;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class PointHolder extends BaseHolder<Point> {

    private static final String action = "Reserve";

    public PointHolder(View view) {
        super(view);
    }

    public PointHolder(View view, View.OnClickListener onActionListener) {
        super(view, onActionListener);
    }

    @Override
    public void bindData(Context context, Point item) {
        title.setText(String.format("[%d] %s", item.getId(), item.getName()));
        subtitle.setText(item.getAddress());
        additional.setText(String.format("Max:%d", item.getMaxBikes()));
    }

    @Override
    protected String getAction() {
        return action;
    }
}
