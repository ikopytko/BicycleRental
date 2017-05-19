package pl.rzeszow.wsiz.bicyclerental.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.activity.MainActivity;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class ToolbarSpinnerAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MainActivity.AppBarItem> mItems = new ArrayList<>();

    public ToolbarSpinnerAdapter(Context context){
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    public void clear() {
        mItems.clear();
    }

    public void addItem(MainActivity.AppBarItem obj) {
        mItems.add(obj);
    }

    public void addItems(List<MainActivity.AppBarItem> objs) {
        mItems.addAll(objs);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
            view = layoutInflater.inflate(
                    R.layout.toolbar_spinner_item_dropdown, parent, false);
            view.setTag("DROPDOWN");
        }
        TextView textView = ButterKnife.findById(view, android.R.id.text1);
        textView.setText(getTitle(position));

        return view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
            view = layoutInflater.inflate(
                    R.layout.toolbar_spinner_item_actionbar, parent, false);
            view.setTag("NON_DROPDOWN");
        }
        TextView textView = ButterKnife.findById(view,android.R.id.text1);
        textView.setText(getTitle(position));
        return view;
    }

    private String getTitle(int position) {
        return position >= 0 && position < mItems.size() ?
                context.getString(mItems.get(position).nameRes) : "";
    }
}
