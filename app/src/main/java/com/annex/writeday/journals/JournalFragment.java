package com.annex.writeday.journals;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annex.writeday.R;
import com.astuetz.PagerSlidingTabStrip;


public class JournalFragment extends Fragment {

    private FragmentActivity myContext;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_journals, container, false);

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);

        tabs.setIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabs.setIndicatorHeight(6);
        tabs.setShouldExpand(true);
        tabs.setTabPaddingLeftRight(5);

        adapter = new MyPagerAdapter(myContext.getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String _TITLE = "";
            switch (position) {
                case 0:
                    _TITLE = "Все";
                    break;
                case 1:
                    _TITLE = "Избранные";
                    break;
                case 2:
                    _TITLE = "Лента";
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
            Log.e("position", "" + position);

            return CardFragment.newInstance(position);
        }

    }

}
