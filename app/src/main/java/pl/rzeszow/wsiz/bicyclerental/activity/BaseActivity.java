package pl.rzeszow.wsiz.bicyclerental.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

import pl.rzeszow.wsiz.bicyclerental.CoreApp;
import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.adapter.ToolbarSpinnerAdapter;
import pl.rzeszow.wsiz.bicyclerental.fragment.BaseFragment;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public abstract class BaseActivity extends ActionBarActivity implements
        BaseFragment.BaseFragmentListener {

    private Toolbar mToolbar;
    private Toast mToast;
    private ProgressDialog mProgressBar;

    protected boolean hasToolBar() {
        return false;
    }

    protected abstract int getFragmentContainerId();

    @Override
    public CoreApp getCore() {
        return ((CoreApp) getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = new Toast(this);
        if (hasToolBar()) {
            setUpToolbar();
        }
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);
    }

    protected void setUpToolbarSpinner(MainActivity.AppBarItem[] items,
                                       AdapterView.OnItemSelectedListener onItemSelectedListener) {
        if (!hasToolBar()) {
            return;
        }

        View spinnerContainer = LayoutInflater.from(this)
                .inflate(R.layout.toolbar_spinner, mToolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mToolbar.addView(spinnerContainer, lp);

        ToolbarSpinnerAdapter spinnerAdapter = new ToolbarSpinnerAdapter(this);
        spinnerAdapter.addItems(Arrays.asList(items));

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override
    public void showMessage(String message) {
        mToast.cancel();
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void showProgress(String message) {
        mProgressBar = ProgressDialog.show(this, "", message);
    }

    protected void pushFragment(Fragment fragment, String tag) {
        pushFragment(fragment, true, tag);
    }

    protected void pushFragment(Fragment fragment, boolean backstack) {
        pushFragment(fragment, backstack, null);
    }

    protected void pushFragment(Fragment fragment, boolean backstack, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainerId(), fragment);
        if (backstack)
            transaction.addToBackStack(tag);

        transaction.commit();
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getFragmentContainerId());
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }
}
