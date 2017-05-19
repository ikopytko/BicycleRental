package pl.rzeszow.wsiz.bicyclerental.adapter.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rzeszow.wsiz.bicyclerental.R;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public abstract class BaseHolder<T> {

    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.subtitle)
    TextView subtitle;
    @InjectView(R.id.additional)
    TextView additional;
    @InjectView(R.id.btn_action)
    TextView action;

    public BaseHolder(View view) {
        this(view, null);
    }

    public void setActionClickListener(View.OnClickListener onActionListener) {
        action.setOnClickListener(onActionListener);
    }

    public BaseHolder(View view, View.OnClickListener onActionListener) {
        ButterKnife.inject(this, view);
        action.setText(getAction());
        setActionClickListener(onActionListener);
    }

    public abstract void bindData(Context context, T item);

    protected abstract String getAction();

}
