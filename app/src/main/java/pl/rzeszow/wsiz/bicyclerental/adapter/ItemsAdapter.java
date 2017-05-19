package pl.rzeszow.wsiz.bicyclerental.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import pl.rzeszow.wsiz.bicyclerental.adapter.holder.BaseHolder;
import pl.rzeszow.wsiz.bicyclerental.api.model.BaseModel;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class ItemsAdapter<T extends BaseModel> extends BaseAdapter {

    private Context context;
    private List<T> items;
    private LayoutInflater inflater;
    private ItemActionListener listener;

    public ItemsAdapter(Context context, List<T> items, ItemActionListener listener) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder viewHolder;
        final T item = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(item.getLayoutId(), null);
            viewHolder = item.getHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BaseHolder) convertView.getTag();
        }
        viewHolder.setActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemAction(item);
            }
        });
        // fill data
        viewHolder.bindData(context, item);

        return convertView;
    }

    public void remove(T item) {
        items.remove(item);
        notifyDataSetChanged();
    }

    public interface ItemActionListener<T> {
        void onItemAction(T item);
    }
}
