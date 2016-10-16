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
import mobi.gastronomica.utils.Functions;
import mobi.gastronomica.utils.JsonUtils;

public class ResetPasswordEmail extends BaseActivity {

    @InjectView(R.id.email)
    EditText mEditEmail;
    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.reset_form)
    View mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_email);
        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_aceptar)
    public void mAceptar() {
        mEditEmail.setError(null);

        String email = mEditEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
                JSONObject reset = new JSONObject();
                try {
                    reset.put("email", email);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                showView(true);
                requestReset(reset);
            } else {
                Snackbar.with(getApplicationContext()).text(getResources().getString(R.string.no_conexion)).color(getResources().getColor(R.color.error)).show(ResetPasswordEmail.this);
            }

        }
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

    private void requestReset(JSONObject reset) {

        ApiManager.post("resetear_password", reset, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("", "onSuccess");
                if (statusCode > 199 && statusCode < 300) {
                    if (!response.isNull("error")) {
                        try {
                            showView(false);
                            Snackbar.with(getApplicationContext()).text(response.getString("error")).color(getResources().getColor(R.color.error)).show(ResetPasswordEmail.this);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        Log.i("onSuccess", response.toString());
                        JsonUtils.parseJsonResetPassword(response, ResetPasswordEmail.this);
                        finish();
                        startActivity(new Intent(getApplicationContext(), ResetPasswordCode.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                    //showView(false);
                } else {
                    showView(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("TAG", "onFailure");
                showView(false);
                Snackbar.with(getApplicationContext()).text("Ha ocurrido un error vuelve a intentarlo").color(getResources().getColor(R.color.error)).show(ResetPasswordEmail.this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        ApiManager.cancelgetAll();
        finish();
        return super.onOptionsItemSelected(item);
    }
}