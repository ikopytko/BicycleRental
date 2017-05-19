package pl.rzeszow.wsiz.bicyclerental.activity;

import android.os.Bundle;

import pl.rzeszow.wsiz.bicyclerental.R;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public class AccountActivity extends BaseActivity {

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }
}
