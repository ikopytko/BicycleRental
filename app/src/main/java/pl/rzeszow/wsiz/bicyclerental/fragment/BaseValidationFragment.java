package pl.rzeszow.wsiz.bicyclerental.fragment;

import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;

import pl.rzeszow.wsiz.bicyclerental.fragment.BaseFragment;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public abstract class BaseValidationFragment extends BaseFragment implements
        Validator.ValidationListener{

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if (failedView instanceof EditText) {
            ((EditText) failedView).setError(failedRule.getFailureMessage());
        } else {
            showToastMessage(failedRule.getFailureMessage());
        }
    }
}
