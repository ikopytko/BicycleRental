package pl.rzeszow.wsiz.bicyclerental.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import pl.rzeszow.wsiz.bicyclerental.CoreApp;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public abstract class BaseFragment extends Fragment {

    private BaseFragmentListener baseFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            baseFragmentListener = (BaseFragmentListener) activity;
        } catch (ClassCastException e) {
            throw (new ClassCastException(activity.getClass().getSimpleName() +
                    " must implement BaseFragmentListener"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToastMessage(String message) {
        baseFragmentListener.showMessage(message);
    }

    protected void goBack(){
        baseFragmentListener.goBack();
    }

    protected CoreApp getCore(){
        return baseFragmentListener.getCore();
    }

    public interface BaseFragmentListener {
        void showMessage(String message);

        void showProgress(String message);

        CoreApp getCore();

        void goBack();
    }

}
