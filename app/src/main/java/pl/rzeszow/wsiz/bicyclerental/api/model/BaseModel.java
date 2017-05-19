package pl.rzeszow.wsiz.bicyclerental.api.model;

import android.view.View;

import pl.rzeszow.wsiz.bicyclerental.adapter.holder.BaseHolder;

/**
 * Created by Roman Savka on
 * 31-Mar-15.
 */
public interface BaseModel {

    long getId();

    int getLayoutId();

    BaseHolder getHolder(View view);

    BaseHolder getHolder(View view, View.OnClickListener listener);
}
