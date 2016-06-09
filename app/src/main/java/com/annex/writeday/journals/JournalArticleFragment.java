package com.annex.writeday.journals;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annex.writeday.MyApplication;
import com.annex.writeday.R;
import com.annex.writeday.apiservices.service.APIService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalArticleFragment extends Fragment {

    private static final String ARG_journal_article_Id = "journal_article_Id";
    private static final String ARG_journal_article_mTitle= "mTitle";
    private static final String ARG_journal_article_mImage= "mImage";
    private static final String ARG_journal_article_mDate= "mDate";
    private static final String ARG_journal_article_mDescription= "mDescription";
    private static final String ARG_journal_article_mImageArticles= "mImageArticles";

    private String journal_article_Id;
    private String mTitle;
    private String mImage;
    private String mDate;
    private String mDescription;
    private String mImageArticles;

    private String articleID;
    private String articleBody;
    private String articleTitle;
    private String articleImages;
    private String articleArticleLikes;
    private String articleDate;
    private String articleArticleComments;
    private String full_directory;
    private String miniature;

    public static JournalArticleFragment newInstance(String journal_article_Id, String mTitle,
                                                     String mImage, String mDate, String mDescription, String mImageArticles){
        JournalArticleFragment f = new JournalArticleFragment();
        Bundle b = new Bundle();
        b.putString(ARG_journal_article_Id, journal_article_Id);
        b.putString(ARG_journal_article_mTitle, mTitle);
        b.putString(ARG_journal_article_mImage, mImage);
        b.putString(ARG_journal_article_mDate, mDate);
        b.putString(ARG_journal_article_mDescription, mDescription);
        b.putString(ARG_journal_article_mImageArticles, mImageArticles);
        f.setArguments(b);
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_journal_article, null);
        journal_article_Id = getArguments().getString(ARG_journal_article_Id);

        //loadArticle();

        mTitle = getArguments().getString(ARG_journal_article_mTitle);
//        mImage = getArguments().getString(ARG_journal_article_mImage);
        mDate = getArguments().getString(ARG_journal_article_mDate);
        mDescription = getArguments().getString(ARG_journal_article_mDescription);
        mImageArticles = getArguments().getString(ARG_journal_article_mImageArticles);

        TextView title = (TextView) v.findViewById(R.id.title_article);
        title.setText(mTitle);
        TextView date = (TextView) v.findViewById(R.id.date_article);
        date.setText(mDate.substring(0, 10));
        TextView description = (TextView) v.findViewById(R.id.description_article);
        description.setText(mDescription);
        ImageView image = (ImageView) v.findViewById(R.id.imageArticlesView_article);

        Picasso mPicasso = Picasso.with(getContext());
        mPicasso.load(mImageArticles).placeholder(R.drawable.logo).error(R.drawable.logo).into(image);

        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void loadArticle() {

        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.loadArticle(journal_article_Id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.e("responseBody",responseBody);

                        //JSONArray articlesJson = new JSONArray(responseBody);
                        JSONObject articlesJson = new JSONObject(responseBody);
                        JSONObject articleJson, subArticleJson;
//                        JournalArticles journalArticles;
                        subArticleJson = articlesJson/*.getJSONObject(0)*/.getJSONObject("journal").getJSONObject("logo_image");

                        full_directory = subArticleJson.getString("full_directory");
                        miniature = subArticleJson.getString("miniature");
                        //for (int i = 0; i < articlesJson.length(); i++) {
                            articleJson = articlesJson/*.getJSONObject(i)*/;
                            try {
                                articleID = articleJson.getString("id");
                                articleBody = articleJson.getString("body");
                                articleTitle = articleJson.getString("title");
                                articleImages = articleJson.getString("images");
                                articleArticleLikes = articleJson.getString("articleLikes");
                                articleDate = articleJson.getString("date");
                                articleArticleComments = articleJson.getString("articleComments");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            journalArticles = new JournalArticles(Page_Journals_Fragment.this.getContext(),
//                                    articleID, articleBody, articleTitle, articleImages, articleArticleLikes,
//                                    articleDate, articleArticleComments, full_directory, miniature);
//
//                            mArticlesJournals.add(journalArticles);
//                            checkJournalsArticlesIsEmpty();
//                            mAdapter.notifyDataSetChanged();
                        //}

                    } catch (Exception e) {
                        Log.e("Failed 2", "" + e.getMessage());
                    }
                } else {
                    Log.e("Failed 2", "1");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("Failed 2", "T: " + t.getMessage());
            }
        });
    }

}
