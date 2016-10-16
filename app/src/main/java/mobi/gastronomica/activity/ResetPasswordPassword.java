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
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.utils.ApiManager;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;
import mobi.gastronomica.utils.JsonUtils;
import mobi.gastronomica.utils.PreferenceManager;
import mobi.gastronomica.utils.Session;

public class ResetPasswordPassword extends BaseActivity {

    @InjectView(R.id.password)
    EditText mEditPassword;
    @InjectView(R.id.passsword_confirmar)
    EditText mEditPasswordConfirmar;
    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.reset_form)
    View mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_password);
        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_aceptar)
    public void mAceptar() {
        mEditPassword.setError(null);
        mEditPasswordConfirmar.setError(null);

        String password = mEditPassword.getText().toString();
        String confirmar = mEditPasswordConfirmar.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(confirmar)) {
            mEditPasswordConfirmar.setError(getString(R.string.not_empty));
            focusView = mEditPasswordConfirmar;
            cancel = true;

        } else {
            if (confirmar.length() < 8) {
                mEditPasswordConfirmar.setError("La contraseña tiene que tener minimo 8 caracteres");
                focusView = mEditPasswordConfirmar;
                cancel = true;
            } else {
                if (!confirmar.equals(password)) {
                    mEditPasswordConfirmar.setError("Las contraseñas tienen que se iguales");
                    focusView = mEditPasswordConfirmar;
                    cancel = true;
                }
            }
        }


        if (TextUtils.isEmpty(password)) {
            mEditPassword.setError(getString(R.string.not_empty));
            focusView = mEditPassword;
            cancel = true;

        } else {
            if (password.length() < 8) {
                mEditPassword.setError("La contraseña tiene que tener minimo 8 caracteres");
                focusView = mEditPassword;
                cancel = true;
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            if (confirmar.equals(password)) {
                if (Functions.isOnline()) {
                    JSONObject reset = new JSONObject();
                    String token = PreferenceManager.getString(Constants.RESET_RESET_PASSWORD_TOKEN, ResetPasswordPassword.this);
                    String url = "cambiar_password?password=" + confirmar + "&token=" + token;
                    showView(true);
                    requestReset(url);
                } else {
                    Snackbar.with(getApplicationContext()).text(getResources().getString(R.string.no_conexion)).color(getResources().getColor(R.color.error)).show(ResetPasswordPassword.this);
                }
            }

        }
    }

    private void requestReset(String url) {

        ApiManager.postLogin(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("ONSU", "onSuccess");
                Log.i("SON", response.toString());
                if (statusCode > 199 && statusCode < 300) {
                    try {
                        if (response.getInt("status") != 403) {
                            JsonUtils.parseJsonPostClient(response, ResetPasswordPassword.this);
                            //showView(false);
                            if (Session.SessionExists(ResetPasswordPassword.this)) {
                                finish();
                                goToActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            } else {
                                finish();
                            }
                        } else {
                            showView(false);
                            Snackbar.with(getApplicationContext()).color(getResources().getColor(R.color.error)).text(response.getString("mensaje")).show(ResetPasswordPassword.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showView(false);
                    Snackbar.with(getApplicationContext()).text("Ha ocurrido un error vuelve a intentarlo").color(getResources().getColor(R.color.error)).show(ResetPasswordPassword.this);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("TAG", "onFailure");
                Log.i("Failure-SON", errorResponse.toString());
                showView(false);
                Snackbar.with(getApplicationContext()).text("Ha ocurrido un error vuelve a intentarlo").color(getResources().getColor(R.color.error)).show(ResetPasswordPassword.this);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        ApiManager.cancelgetAll();
        finish();
        return super.onOptionsItemSelected(item);
    }
}
