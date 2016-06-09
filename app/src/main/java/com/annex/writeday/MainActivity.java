package com.annex.writeday;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.annex.writeday.apiservices.PersistentCookieStore;
import com.annex.writeday.apiservices.model.User;
import com.annex.writeday.apiservices.service.APIService;
import com.annex.writeday.journals.JournalFragment;
import com.annex.writeday.profile.ProfileActivityFragment;
import com.annex.writeday.sign.LoginActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public Drawer result = null;
    private AccountHeader headerResult = null;
    private ActionBar actionBar;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);



         toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         actionBar = getSupportActionBar();


        if(savedInstanceState==null){
            FragmentManager mFragmentManager2 = getSupportFragmentManager();
            Fragment mFragment1 = new TestFragment().newInstance(1);
            String titleActionBar ="Лента";
            mFragmentManager2.beginTransaction().replace(R.id.flFragments, mFragment1).addToBackStack(titleActionBar).commit();
            actionBar.setTitle(R.string.Лента);
        }
        else {
            String title = savedInstanceState.getString("title");
            actionBar.setTitle(title);
        }

         headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("").withEmail("")//.withIcon(userIconWord)
                )
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        MyApplication application = (MyApplication) getApplicationContext();
                        User user = application.getLocalUser(getApplicationContext());

                        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        actionBar.setTitle(user.getNickname());
                        String titleActionBar =user.getNickname();

                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        Fragment mFragment = new ProfileActivityFragment();
                        mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment).addToBackStack(titleActionBar).commit();

                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();


        //Create the drawer
        result = new DrawerBuilder(this)
                //this layout have to contain child layouts
                //.withRootView(R.id.drawer_container)
                .withActivity(this)
                .withToolbar(toolbar)
                .withFullscreen(true)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.Лента).withIcon(GoogleMaterial.Icon.gmd_home).withBadge("12").withBadgeStyle(new BadgeStyle(Color.LTGRAY, Color.LTGRAY)),
                        new PrimaryDrawerItem().withName(R.string.Мой_блог).withIcon(GoogleMaterial.Icon.gmd_account),
                        new PrimaryDrawerItem().withName(R.string.Блоги).withIcon(GoogleMaterial.Icon.gmd_account),
                        new PrimaryDrawerItem().withName(R.string.Журналы).withIcon(GoogleMaterial.Icon.gmd_book),
                        new PrimaryDrawerItem().withName(R.string.Сообщения).withIcon(GoogleMaterial.Icon.gmd_book),
                        new PrimaryDrawerItem().withName(R.string.Вокруг_меня).withIcon(GoogleMaterial.Icon.gmd_book),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.FAQ).withIcon(GoogleMaterial.Icon.gmd_help),
                        new SecondaryDrawerItem().withName(R.string.Настройки).withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        FragmentManager mFragmentManager1 = getSupportFragmentManager();
                        String titleActionBar = mFragmentManager1.getBackStackEntryAt(mFragmentManager1.getBackStackEntryCount()-1).getName();
                        actionBar.setTitle(titleActionBar);
                        if(mFragmentManager1.getBackStackEntryCount()==2) {

                            actionBar.setDisplayHomeAsUpEnabled(false);
                            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                        }
                        onBackPressed();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        /*if (drawerItem instanceof Nameable) {
                            //Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                            //tv.setText(((Nameable) drawerItem).getName().getText(MainActivity.this));

                        }*/

//                        if (actionBar != null) {
//                            if (actionBar.getTitle() != null && actionBar.getTitle().equals(((Nameable) drawerItem).getName().getText(MainActivity.this)))
//                                return false;
//                            else
//                                Log.e("else","else");
                        actionBar.setTitle(((Nameable) drawerItem).getName().getText(MainActivity.this));
//                        }
                        String titleActionBar =((Nameable) drawerItem).getName().getText(MainActivity.this) ;
                        FragmentManager mFragmentManager = getSupportFragmentManager();

                        switch (position) {
//                            case 0:
//
//                                //ПОНЯТИЯ НЕ ИМЕЮ, НО 0 ЧЕМ ТО ЗАНЯТ
//
//                                break;
                            case 1:
                                Fragment mFragment1 = new TestFragment().newInstance(1);
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment1).addToBackStack(titleActionBar).commit();
                                break;
                            case 2:
                                Fragment mFragment2 = new TestFragment().newInstance(2);
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment2).addToBackStack(titleActionBar).commit();
                                break;
                            case 3:
                                Fragment mFragment3 = new TestFragment().newInstance(3);
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment3).addToBackStack(titleActionBar).commit();
                                break;
                            case 4:
                                Fragment mFragment4 = new JournalFragment();
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment4).addToBackStack(titleActionBar).commit();
                                break;
                            case 5:
                                Fragment mFragment5 = new TestFragment().newInstance(5);
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment5).addToBackStack(titleActionBar).commit();
                                break;
                            case 6:
                                Fragment mFragment6 = new TestFragment().newInstance(6);
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment6).addToBackStack(titleActionBar).commit();
                                break;
                            case 7:
                                Fragment mFragment7 = new TestFragment().newInstance(7);
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment7).addToBackStack(titleActionBar).commit();
                                break;
                            case 8:
                                Fragment mFragment8 = new TestFragment().newInstance(8);
                                mFragmentManager.beginTransaction().replace(R.id.flFragments, mFragment8).addToBackStack(titleActionBar).commit();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();




        Intent intent = getIntent();
        String isNewUser = intent.getStringExtra("NEW_USER");
        if (isNewUser != null && isNewUser.equals("YES")) {
            loadUserNetworkData();
        } else {
            loadUserLocalData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                FragmentManager mFragmentManager1 = getSupportFragmentManager();
//
//                actionBar.setTitle("Назад");
//                if(mFragmentManager1.getBackStackEntryCount()==2){
//                    Log.e("e", "e");
//
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                    result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
//
//                }
                onBackPressed();
                break;
            case R.id.menu_edit:
                FragmentManager mFragmentManager = getSupportFragmentManager();
                Fragment frag1 = new SettingsFragment();
                mFragmentManager.beginTransaction().replace(R.id.flFragments, frag1).addToBackStack(null).commit();
                break;
            case R.id.menu_exit:
                logoutUser();
                break;
            default:
                break;
        }
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    public void updateHeaderDrawer(String username, String nickname) {

        char ch = nickname.charAt(0);

        TextDrawable userIconWord = TextDrawable.builder()
                .beginConfig()
                .endConfig()
                .buildRoundRect(String.valueOf(ch), getResources().getColor(R.color.colorAccent), 60);

        headerResult.removeProfile(0);
        headerResult.addProfiles(new ProfileDrawerItem().withName(nickname).withEmail(username).withIcon(userIconWord));
    }

    private void loadUserLocalData() {

        MyApplication application = (MyApplication) getApplicationContext();
        User user = application.getLocalUser(getApplicationContext());
        updateHeaderDrawer(user.getUserId()/*user.getUsername()*/, user.getNickname());

    }

    private void loadUserNetworkData() {

        final MyApplication mApplication = (MyApplication)getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.userProfile();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {

                        String responseBody = response.body().string();
                        JSONObject obj = new JSONObject(responseBody);
                        Log.e("obj", obj.toString());
                        String id = obj.getString("id");
                        String phone = obj.getString("phone");
                        String friends = obj.getString("friends");
                        String profileImage = obj.getString("profileImage");
                        String email = obj.getString("email");
                        String nickname = obj.getString("nickname");
                        String role = obj.getString("role");
                        String friedStatus = obj.getString("friedStatus");

                        updateHeaderDrawer(id/*username*/, nickname);

                        Realm realm = Realm.getInstance(getApplicationContext());
                        realm.beginTransaction();
                        User user = realm.createObject(User.class);
                        user.setUserId(id);
                        user.setPhone(phone);
                        user.setFriends(friends);
                        user.setProfileImage(profileImage);
                        user.setEmail(email);
                        user.setNickname(nickname);
                        //user.setUsername(username);
                        user.setRole(role);
                        user.setFriedStatus(friedStatus);
                        realm.commitTransaction();

                    } catch (Exception e) {
                        Log.e("Failed", "" + e.getMessage());
                    }
                } else {
                    Log.e("Failed", "1");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure","если ты это видешь,то здесь какойто пиздец");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        
        outState.putString("title",actionBar.getTitle().toString());
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
            FragmentManager mFragmentManager1 = getSupportFragmentManager();
            if (mFragmentManager1.getBackStackEntryCount() > 0) {
                String titleActionBar = mFragmentManager1.getBackStackEntryAt(mFragmentManager1.getBackStackEntryCount() - 1).getName();
                Log.e("Name",titleActionBar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                if(titleActionBar.equals("Лента")||titleActionBar.equals("Мой блог")||titleActionBar.equals("Блоги")
                        ||titleActionBar.equals("Журналы")||titleActionBar.equals("Сообщения")||titleActionBar.equals("Вокруг меня")
                        ||titleActionBar.equals("FAQ")||titleActionBar.equals("Насмтройки")) {
                    //очищаем стек
                    for (int i = mFragmentManager1.getBackStackEntryCount(); i > 0; i--)
                        super.onBackPressed();
                    //и выходим
                    super.onBackPressed();
                    return;
                }
                actionBar.setTitle(titleActionBar);
            }
            else super.onBackPressed();
        }
    }

        private void logoutUser() {

        Realm realm = Realm.getInstance(getApplicationContext());
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> results = query.findAll();

        realm.beginTransaction();
        results.clear();
        realm.commitTransaction();

        PersistentCookieStore cookieStore = new PersistentCookieStore(getApplicationContext());
        cookieStore.removeAll();

        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

    }

}
