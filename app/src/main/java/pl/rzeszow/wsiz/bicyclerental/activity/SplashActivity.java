package pl.rzeszow.wsiz.bicyclerental.activity;

import android.content.Intent;
import android.os.Bundle;

import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.api.model.User;
import pl.rzeszow.wsiz.bicyclerental.fragment.RegisterFragment;
import pl.rzeszow.wsiz.bicyclerental.fragment.WelcomeFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public class SplashActivity extends BaseActivity implements
        WelcomeFragment.WelcomeFragmentListener,
        Callback<User> {

    @Override
    protected int getFragmentContainerId() {
        return R.id.container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pushFragment(WelcomeFragment.newInstance(), false);
    }


    @Override
    public void onRegisterClicked() {
        pushFragment(RegisterFragment.newInstance(), true);
    }

    @Override
    public void onLoginClicked(String login, String password) {
        getCore().getApiService().login(login, password, this);
    }

    @Override
    public void success(User user, Response response) {
        if (user != null){
            getCore().setUser(user);
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else{
            showMessage(getString(R.string.no_such_user));
        }
    }

    @Override
    public void failure(RetrofitError error) {
        showMessage(error.getMessage());
    }
}
