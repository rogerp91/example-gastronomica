package mobi.gastronomica.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nispok.snackbar.Snackbar;

import org.apache.http.Header;
import org.json.JSONException;
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

public class LogInActivity extends BaseActivity implements MethodsInterfaces {

    private static final String TAG = makeLogTag(LogInActivity.class);

    @InjectView(R.id.email)
    EditText mEditEmail;
    @InjectView(R.id.password)
    EditText mEditPassword;
    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.register_form)
    View mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_ingresar)
    public void mIngregar() {
        validate();
    }

    private void validate() {

        mEditEmail.setError(null);
        mEditPassword.setError(null);

        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Pass
        if (TextUtils.isEmpty(password)) {
            mEditPassword.setError(getString(R.string.not_empty));
            focusView = mEditPassword;
            cancel = true;
        } else {
            if (!Functions.isPasswordValid(password)) {
                mEditPassword.setError(getString(R.string.min_length_password));
                focusView = mEditPassword;
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

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (Functions.isOnline()) {
                showView(true);
                String cadena = "sesion?email=" + email + "&password=" + password;
                requestClient(cadena);
            } else {
                Snackbar.with(getApplicationContext()).text(getResources().getString(R.string.no_conexion)).color(getResources().getColor(R.color.error)).show(LogInActivity.this);
            }

        }
    }

    private void requestClient(String login) {

        ApiManager.postLogin(login, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LOGI(TAG, "onSuccess");
                LOGI(TAG, response.toString());
                if (statusCode > 199 && statusCode < 300) {
                    try {
                        if (response.getInt("status") != 403) {
                            JsonUtils.parseJsonPostClient(response, LogInActivity.this);
                            //showView(false);
                            if (Session.SessionExists(LogInActivity.this)) {
                                goToActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            } else {
                                finish();
                            }
                        } else {
                            showView(false);
                            Snackbar.with(getApplicationContext()).color(getResources().getColor(R.color.error)).text(response.getString("mensaje")).show(LogInActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showView(false);
                    Snackbar.with(getApplicationContext()).text("Ha ocurrido un error vuelve a intentarlo").color(getResources().getColor(R.color.error)).show(LogInActivity.this);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("TAG", "onFailure");
                showView(false);
                Snackbar.with(getApplicationContext()).text("Ha ocurrido un error vuelve a intentarlo").color(getResources().getColor(R.color.error)).show(LogInActivity.this);
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                showView(false);
                Log.i("TAG", "onRetry");
            }

            @Override
            public void onCancel() {
                super.onCancel();
                showView(false);
                Log.i("TAG", "onCancel");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i("TAG", "onFinish");
            }
        });
    }

    @OnClick(R.id.registrar)
    public void mRegistrar() {
        clear(mEditEmail);
        clear(mEditPassword);
        goToActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    @OnClick(R.id.olvidaste_pass_user)
    public void mOlvidarPassword() {
        goToActivity(new Intent(getApplicationContext(), ResetPasswordEmail.class));
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
    public void showView(boolean show, View view) {}

    public void clear(EditText v) {
        v.setText("");
    }
}
