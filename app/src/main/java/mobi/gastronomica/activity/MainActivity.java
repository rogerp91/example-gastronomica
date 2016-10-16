package mobi.gastronomica.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mobi.gastronomica.R;
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.adapte.DrawerListAdapter;
import mobi.gastronomica.fragment.MainFragment;
import mobi.gastronomica.model.ItemNavigation;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.PreferenceManager;

public class MainActivity extends BaseActivity {

    public static MainActivity activity = null;
    //var
    @InjectView(R.id.navList)
    ListView mDrawerList;
    @InjectView(R.id.drawerPane)
    RelativeLayout mDrawerPane;
    @InjectView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.userName)
    TextView userName;
    @InjectView(R.id.avatar)
    ImageView mAvatar;

    @InjectView(R.id.profileBox)
    LinearLayout mProfileBox;

    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<ItemNavigation> mNavItems;

    {
        mNavItems = new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;

        ButterKnife.inject(this);//libreria extrena
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setElevation(0.0f);

        userName.setText(PreferenceManager.getString(Constants.CLIENT_FIRST_NAME, this));

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0)).build();

        String url = Constants.API_BASE_URL_2 + PreferenceManager.getString(Constants.CLIENT_AVATAR, this);
        if (!url.equals("") || !url.equals("null")) {
            if (!url.isEmpty()) {
                ImageLoader.getInstance().displayImage(url, mAvatar, options);
            }
        } else {
            mAvatar.setImageDrawable(getDrawableResource(R.mipmap.ic_name_user));
        }

        mNavItems.add(new ItemNavigation(getResources().getString(R.string.agenda), R.mipmap.ic_agenda));
        mNavItems.add(new ItemNavigation(getResources().getString(R.string.change_pass), R.mipmap.ic_change_pass));
        mNavItems.add(new ItemNavigation(getResources().getString(R.string.longout), R.mipmap.ic_logout));
        mNavItems.add(new ItemNavigation(getResources().getString(R.string.about), R.mipmap.ic_acerca_de));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle(mNavItems.get(position).getTitle());
                selectItemFromDrawer(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mProfileBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToFragmentExtras(MainFragment.newInstance());
            }
        });

        if (savedInstanceState == null) {//cuando entra por primera vez
            displayView(0);
            goToFragmentExtras(MainFragment.newInstance());
            //selectItemFromDrawer(0);
        }
    }//onCreate

    private void displayView(int position) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
                setTitle(mNavItems.get(position).getTitle());
                break;
            case 2:
                mLogOut();
                break;
            default:
                break;
        }
    }

    private void selectItemFromDrawer(int position) {
        switch (position) {
            case 0:
                goToFragmentExtras(MainFragment.newInstance());
                mDrawerList.setItemChecked(position, true);
                setTitle(mNavItems.get(position).getTitle());
                mDrawerLayout.closeDrawer(mDrawerPane);
                break;
            case 1:
                mDrawerList.setItemChecked(position, true);
                setTitle(mNavItems.get(position).getTitle());
                mDrawerLayout.closeDrawer(mDrawerPane);
                break;
            case 2:
                mLogOut();
                displayView(0);
                mDrawerList.setItemChecked(2, false);
                break;
            case 3:
                mDrawerList.setItemChecked(position, true);
                setTitle(mNavItems.get(position).getTitle());
                mDrawerLayout.closeDrawer(mDrawerPane);
                break;
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated methd stub
        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    public void mLogOut() {
        new MaterialDialog.Builder(this)
                .title(R.string.cerrar_session)
                .content(R.string.msj_cerrar_session)
                .positiveText(R.string.agree)
                .backgroundColor(Color.WHITE)
                .negativeColor(getResources().getColor(R.color.secondary_text))
                .positiveColor(getResources().getColor(R.color.secondary_text))
                .negativeText(R.string.disagree)
                .iconRes(R.mipmap.ic_launcher)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        PreferenceManager.removeAll(MainActivity.this);
                        UILApplication.SQL.clear();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LogInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        mDrawerLayout.closeDrawer(mDrawerPane);
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}//endClass
