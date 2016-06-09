package com.annex.writeday.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annex.writeday.MyApplication;
import com.annex.writeday.R;
import com.annex.writeday.apiservices.model.User;
import com.annex.writeday.journals.CardFragment;

public class ProfileActivityFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;
    private FragmentActivity myContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        MyApplication application = (MyApplication) myContext.getApplicationContext();
        User user = application.getLocalUser(myContext.getApplicationContext());

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.materialup_tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.materialup_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) view.findViewById(R.id.materialup_appbar);
        mProfileImage = (ImageView) view.findViewById(R.id.materialup_profile_image);

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        viewPager.setAdapter(new MyPagerAdapter1(myContext.getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        Typeface type = Typeface.createFromAsset(getResources().getAssets(),"fonts/verdana.ttf");
        TextView name =(TextView) view.findViewById(R.id.name);
        name.setText(user.getUsername());
        name.setTypeface(type);

        TextView online =(TextView) view.findViewById(R.id.online);
        online.setTypeface(type);

        TextView status =(TextView) view.findViewById(R.id.status);
        status.setTypeface(type);

        TextView p1 =(TextView) view.findViewById(R.id.p1);
        p1.setTypeface(type);

        TextView p2 =(TextView) view.findViewById(R.id.p2);
        p2.setTypeface(type);

        TextView p3 =(TextView) view.findViewById(R.id.p3);
        p3.setTypeface(type);

        return view;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0) {
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        }
        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize ;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }


public class MyPagerAdapter1 extends FragmentPagerAdapter {

        public MyPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String _TITLE = "";
            switch (position) {
                case 0:
                    _TITLE = "О себе";
                    break;
                case 1:
                    _TITLE = "Журналы";
                    break;
                case 2:
                    _TITLE = "Фото";
                    break;
            }
            return _TITLE;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment Profile;
            switch (position) {
                case 0:
                    Profile = new ProfileInfoFragment();
                    break;
                case 1:
                    Profile = new CardFragment().newInstance(1);
                    break;
                case 2:
                    Profile = new CardFragment().newInstance(2);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    break;
                default:
                    Profile = new ProfileInfoFragment();
                    break;
            }
            return Profile;
        }

    }
}
