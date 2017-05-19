package pl.rzeszow.wsiz.bicyclerental.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.rzeszow.wsiz.bicyclerental.R;


/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public class WelcomeFragment extends BaseValidationFragment {

    private Validator validator;

    @Required(order = 1)
    @InjectView(R.id.login)
    EditText login;

    @Required(order = 2)
    @InjectView(R.id.password)
    EditText password;

    WelcomeFragmentListener welcomeFragmentListener;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    public interface WelcomeFragmentListener {
        void onRegisterClicked();

        void onLoginClicked(String login, String password);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            welcomeFragmentListener = (WelcomeFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WelcomeFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        ButterKnife.inject(this, view);
        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }

    @OnClick(R.id.btnLogin)
    void onLoginClicked() {
        validator.validate();
    }

    @OnClick(R.id.btnRegister)
    void onRegisterClicked() {
        welcomeFragmentListener.onRegisterClicked();
    }

    @Override
    public void onValidationSucceeded() {
        welcomeFragmentListener.onLoginClicked(
                String.valueOf(login.getText()),
                String.valueOf(password.getText()));
    }
}
