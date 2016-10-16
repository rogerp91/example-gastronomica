package mobi.gastronomica.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nispok.snackbar.Snackbar;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.utils.ApiManager;
import mobi.gastronomica.components.MethodsInterfaces;
import mobi.gastronomica.utils.Functions;
import mobi.gastronomica.utils.JsonUtils;
import mobi.gastronomica.utils.Session;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class RegisterActivity extends BaseActivity implements MethodsInterfaces {

    private static final String TAG = makeLogTag(RegisterActivity.class);
    // var
    @InjectView(R.id.first_name)
    EditText mEditName;
    @InjectView(R.id.email)
    EditText mEditEmail;
    @InjectView(R.id.password)
    EditText mPassword;

    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.register_form)
    View mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);
    }


    @OnClick(R.id.btn_cancelar)
    public void clear() {
        clear(mEditName);
        clear(mEditEmail);
        clear(mPassword);
    }

    @OnClick(R.id.btn_registrar)
    public void validate() {

        mEditName.setError(null);
        mEditEmail.setError(null);
        mPassword.setError(null);

        String name = mEditName.getText().toString();
        String email = mEditEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Pass
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.not_empty));
            focusView = mPassword;
            cancel = true;
        } else {
            if (!Functions.isPasswordValid(password)) {
                mPassword.setError(getString(R.string.min_length_password));
                focusView = mPassword;
                cancel = true;
            }
        }

        //Email
        if (TextUtils.isEmpty(email)) {
            mEditEmail.setError(getString(R.string.not_empty));
            focusView = mEditEmail;
            cancel = true;
        } else {
            if (!Functions.isEmailValid(email)) {
                mEditEmail.setError(getString(R.string.validation_email));
                focusView = mEditEmail;
                cancel = true;
            }
        }


        if (TextUtils.isEmpty(name)) {
            mEditName.setError(getString(R.string.not_empty));
            focusView = mEditName;
            cancel = true;
        } else {
            if (!Functions.isNameValid(name)) {
                mEditName.setError(getString(R.string.validation_name));
                focusView = mEditName;
                cancel = true;
            } else {
                if (!Functions.isNameValidLenght(name)) {
                    mEditName.setError(getString(R.string.min_length_name));
                    focusView = mEditName;
                    cancel = true;
                }

            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (Functions.isOnline()) {
                JSONObject registro = JsonUtils.JsonClient(name, "", email, password);
                showView(true);
                requestClient(registro);
            } else {
                Snackbar.with(getApplicationContext()).text(getResources().getString(R.string.no_conexion)).color(getResources().getColor(R.color.error)).show(RegisterActivity.this);
            }

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        finish();
    }

    public void clear(EditText v) {
        v.setText("");
    }

    private void requestClient(JSONObject registro) {

        ApiManager.post("registro", registro, new JsonHttpResponseHandler() {

            boolean onFailure = false;
            boolean onSuccess = false;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                onSuccess = true;
                LOGI(TAG, "onSuccess");
                LOGI(TAG, response.toString());
                if (statusCode > 199 && statusCode < 300) {
                    JsonUtils.parseJsonPostClient(response, RegisterActivity.this);
                    //showView(false);
                    if (Session.SessionExists(RegisterActivity.this)) {
                        goToActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                } else {
                    showView(false);
                    Snackbar.with(getApplicationContext()).text("Ha ocurrido un error vuelve a intentarlo").color(getResources().getColor(R.color.error)).show(RegisterActivity.this);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("TAG", "onFailure");
                onFailure = true;
                showView(false);
                Snackbar.with(getApplicationContext()).text("Ha ocurrido un error vuelve a intentarlo").color(getResources().getColor(R.color.error)).show(RegisterActivity.this);
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                Log.i("TAG", "onRetry");
                showView(false);
            }

            @Override
            public void onCancel() {
                super.onCancel();
                Log.i("TAG", "onCancel");
                showView(false);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i("TAG", "onFinish");
            }
        });
    }

    @Override
    public void showView(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mForm.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showView(boolean show, View view) {
    }
}
