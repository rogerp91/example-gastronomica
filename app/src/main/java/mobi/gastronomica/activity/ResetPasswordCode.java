package mobi.gastronomica.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.utils.ApiManager;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.PreferenceManager;

public class ResetPasswordCode extends BaseActivity {

    @InjectView(R.id.title1)
    TextView title1;
    @InjectView(R.id.email)
    TextView email;
    @InjectView(R.id.code)
    EditText mEditCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_code);
        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title1.setText(Html.fromHtml(getString(R.string.msn_reset_5)));

        try {
            if (PreferenceManager.getBoolean(Constants.RESET_FLAT, this)) {
                if (!PreferenceManager.getString(Constants.RESET_CORREO_DESTINO, this).equals("")) {
                    email.setText(PreferenceManager.getString(Constants.RESET_CORREO_DESTINO, this));
                } else {
                    finish();
                }
            } else {
                finish();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_aceptar)
    public void mAceptar() {
        mEditCode.setError(null);

        String code = mEditCode.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(code)) {
            mEditCode.setError(getString(R.string.not_empty));
            focusView = mEditCode;
            cancel = true;

        } else {
            if (code.length() != 6) {
                mEditCode.setError("El codigo son solo 6 caracteres");
                focusView = mEditCode;
                cancel = true;
            } else {
                if (!code.equals(PreferenceManager.getString(Constants.RESET_RESET_PASSWORD_TOKEN, ResetPasswordCode.this))) {
                    //mEditCode.setError("Codigo erroneo");
                    Snackbar.with(getApplicationContext()).text(getResources().getString(R.string.error_code)).color(getResources().getColor(R.color.error)).show(ResetPasswordCode.this);
                    focusView = mEditCode;
                    cancel = true;
                }
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), ResetPasswordPassword.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ApiManager.cancelgetAll();
        finish();
        return super.onOptionsItemSelected(item);
    }
}