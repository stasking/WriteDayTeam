package com.annex.writeday.journals;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
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

public class JournalArticlesAdapter  extends RecyclerView.Adapter<JournalArticlesAdapter.ViewHolder> {

    Picasso mPicasso;
    Typeface type;
    MainActivity myMA;
    JournalArticles articlesJournal;


    private List<JournalArticles> mArticlesJournals;

    public JournalArticlesAdapter(Context context, List<JournalArticles> ArticlesJournals,MainActivity MA) {
        this.mArticlesJournals = ArticlesJournals;
        mPicasso = Picasso.with(context);
        myMA = MA;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.journal_articles_list_item;

        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);

        mPicasso = Picasso.with(parent.getContext());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        articlesJournal = mArticlesJournals.get(position);
        viewHolder.setTitle(articlesJournal.getArticleTitle());
        viewHolder.setImageWord(articlesJournal.getURLImage());
        viewHolder.setDate(articlesJournal.getArticleDate().substring(0,10));
        viewHolder.setDescription(articlesJournal.getArticleDescription());
        viewHolder.setImageArticlesWord(articlesJournal.getArticleURLImage());

        viewHolder.setIdArticle(articlesJournal.getArticleID());
    }

    @Override
    public int getItemCount() {
        return mArticlesJournals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleView;
        private ImageView mImageView;
        private TextView mDateView;
        private TextView mDescriptionView;
        private ImageView mImageArticlesView;

        private String journal_article_Id;

        private String mTitle;
        private String mImage;
        private String mDate;
        private String mDescription;
        private String mImageArticles;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleView = (TextView) itemView.findViewById(R.id.title1);
            mImageView = (ImageView) itemView.findViewById(R.id.imageJournal);
            mDateView = (TextView) itemView.findViewById(R.id.date1);
            mDescriptionView = (TextView) itemView.findViewById(R.id.description_articles);
            mImageArticlesView = (ImageView) itemView.findViewById(R.id.imageArticlesView);

            type = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/verdana.ttf");

            mTitleView.setTypeface(type);
            mDateView.setTypeface(type);
            mDescriptionView.setTypeface(type);

            journal_article_Id = "";

            itemView.setOnClickListener(this);
        }

        public String setIdArticle(String id) {
            if (null == journal_article_Id) return id;
            journal_article_Id = id;
            return id;
        }

        public void setTitle(String title) {
            if (null == mTitleView) return;
            mTitleView.setText(title);
            mTitle=title;
        }

        public void setImageWord(String URL_image) {
            if (null == mImageView) return;
            mPicasso.load(URL_image).placeholder(R.drawable.logo).error(R.drawable.logo).into(mImageView);
            mImage = URL_image;
        }

        public void setDate(String date) {
            if (null == mDateView) return;
            mDateView.setText(date);
            mDate = date;
        }

        public void setDescription(String description) {
            if (null == mDescriptionView) return;
            mDescriptionView.setText(description);
            mDescription = description;
        }

        public void setImageArticlesWord(String URL_image) {
            if (null == mImageArticlesView) return;
            mPicasso.load(URL_image).placeholder(R.drawable.logo).error(R.drawable.logo).into(mImageArticlesView);
            mImageArticles = URL_image;
        }

        @Override
        public void onClick(View v) {
            FragmentManager mFragmentManager = myMA.getSupportFragmentManager();
            Fragment frag1 = JournalArticleFragment.newInstance(journal_article_Id, mTitle, mImage, mDate, mDescription, mImageArticles);
            String titleActionBar = mTitle;
            Log.e("Name", titleActionBar);
            mFragmentManager.beginTransaction().replace(R.id.flFragments, frag1).addToBackStack(titleActionBar).commit();
        }
    }
}

