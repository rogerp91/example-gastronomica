package mobi.gastronomica.activity;

import android.os.Bundle;
import android.view.MenuItem;

import mobi.gastronomica.R;
import mobi.gastronomica.utils.ApiManager;

public class ContainerActivity extends BaseActivity {

    boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_base);
        if (getIntent().getExtras() != null) {
            IdFragment(getIntent());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ApiManager.cancelgetAll();

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        /**
         ApiManager.cancelgetAll();
         android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
         Log.i("back stack entry", fm.getBackStackEntryCount() + "");
         fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
         **/
        //finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         ApiManager.cancelgetAll();
         android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
         Log.i("back stack entry", fm.getBackStackEntryCount() + "");
         fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
         //fm.popBackStack();
         **/
        finish();
    }
}//endClass
