package pl.rzeszow.wsiz.bicyclerental.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Required;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.rzeszow.wsiz.bicyclerental.R;
import pl.rzeszow.wsiz.bicyclerental.api.model.InfoResponse;
import pl.rzeszow.wsiz.bicyclerental.api.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Roman Savka on
 * 28-Mar-15.
 */
public class RegisterFragment extends BaseValidationFragment implements
        Callback<InfoResponse> {

    private Validator validator;
    private User user;

    @Required(order = 1)
    @InjectView(R.id.login)
    EditText login;

    @Required(order = 2)
    @InjectView(R.id.password)
    EditText password;

    @Required(order = 3)
    @InjectView(R.id.lastName)
    EditText lastName;

    @Required(order = 4)
    @InjectView(R.id.firstName)
    EditText firstName;

    @Required(order = 5)
    @InjectView(R.id.phone)
    EditText phone;

    @Required(order = 6)
    @Email(order = 7, messageResId = R.string.validEmail)
    @InjectView(R.id.email)
    EditText email;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        ButterKnife.inject(this, view);
        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }

    @OnClick(R.id.submit)
    void onSubmitClicked() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        user = new User(
                String.valueOf(login.getText()),
                String.valueOf(password.getText()),
                String.valueOf(lastName.getText()),
                String.valueOf(firstName.getText()),
                String.valueOf(email.getText()),
                String.valueOf(phone.getText()));

        getCore().getApiService().register(user, this);
    }

    @Override
    public void success(InfoResponse infoResponse, Response response) {
        showToastMessage(infoResponse.getMessage());
        if (infoResponse.isOk()) {
            user.setId(infoResponse.getValue());
            getCore().setUser(user);
            goBack();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        showToastMessage(error.getMessage());
    }
}
