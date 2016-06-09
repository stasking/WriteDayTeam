package com.annex.writeday.journals;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annex.writeday.MainActivity;
import com.annex.writeday.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    Picasso mPicasso;
    Typeface type;
    MainActivity myMA;
    Toolbar toolbar;

    private List<Journal> mJournal;

    public JournalAdapter(Context context, List<Journal> journals,MainActivity MA) {
        mJournal = journals;
        mPicasso = Picasso.with(context);
        myMA = MA;
    }
    public JournalAdapter(){

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        int layout = R.layout.journal_list_item;

        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);

        mPicasso = Picasso.with(parent.getContext());



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Journal journal = mJournal.get(position);
        viewHolder.setTitle(journal.getTitle());
        viewHolder.setDescription(journal.getDescription());
        viewHolder.setImageWord(journal.getURLImage());

        viewHolder.setId(journal.getJournalId());
    }

    @Override
    public int getItemCount() {
        return mJournal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleView;
        private TextView mDescriptionView;
        private ImageView mImageView;
        private String mJournalId;

        public ViewHolder(View itemView) {
            super(itemView);

            mTitleView = (TextView) itemView.findViewById(R.id.title);
            mDescriptionView = (TextView) itemView.findViewById(R.id.description);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mJournalId = "";

            type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/verdana.ttf");

            mTitleView.setTypeface(type);
            mDescriptionView.setTypeface(type);

            itemView.setOnClickListener(this);
        }

        public void setTitle(String title) {
            if (null == mTitleView) return;
            mTitleView.setText(title);
        }

        public String setId(String id) {
            if (null == mJournalId) return id;
            mJournalId = id;
            return id;
        }

        public void setDescription(String description) {
            if (null == mDescriptionView) return;
            mDescriptionView.setText(description);
        }

        public void setImageWord(String URL_image) {
            if (null == mImageView) return;
            mPicasso.load(URL_image).placeholder(R.drawable.logo).error(R.drawable.logo).into(mImageView);
        }

        @Override
        public void onClick(View view) {
            myMA.result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
            myMA.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            myMA.getSupportActionBar().setTitle(mTitleView.getText());

            FragmentManager mFragmentManager = myMA.getSupportFragmentManager();
            Fragment frag1 = Page_Journals_Fragment.newInstance(mJournalId);
            String titleActionBar =(String) mTitleView.getText();
            Log.e("Name", titleActionBar);
            mFragmentManager.beginTransaction().replace(R.id.flFragments, frag1).addToBackStack(titleActionBar).commit();
        }
    }
}