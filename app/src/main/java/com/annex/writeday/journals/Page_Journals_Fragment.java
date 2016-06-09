package com.annex.writeday.journals;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annex.writeday.MainActivity;
import com.annex.writeday.MyApplication;
import com.annex.writeday.R;
import com.annex.writeday.apiservices.service.APIService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Page_Journals_Fragment extends Fragment {

    private RecyclerView mJournalsView;///////////////////////////////////
    private List<JournalArticles> mArticlesJournals = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;//////////////////////////////////////////
    private String journalId;

    private String articleID;
    private String articleBody;
    private String articleTitle;
    private String articleImages;
    private String articleArticleLikes;
    private String articleDate;
    private String articleArticleComments;

    private String full_directory;
    private String miniature;

    private TextView emptyView;

    ProgressDialog progressDialog;

    private static final String ARG_journalId = "journalId";

    public static Page_Journals_Fragment newInstance(String journalId){
        Page_Journals_Fragment f = new Page_Journals_Fragment();
        Bundle b = new Bundle();
        b.putString(ARG_journalId, journalId);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Загрузка журналов...");

        journalId = getArguments().getString(ARG_journalId);

        mAdapter = new JournalArticlesAdapter(getActivity().getApplicationContext(), mArticlesJournals,(MainActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_page__journal, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mJournalsView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        mJournalsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mJournalsView.setAdapter(mAdapter);

        emptyView = (TextView) view.findViewById(R.id.empty_view1);


        journalArticles();
    }

    private void checkJournalsArticlesIsEmpty() {

        if (mArticlesJournals.isEmpty()) {
            mJournalsView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
        else {
            mJournalsView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

    }

    private void journalArticles() {

        progressDialog.show();

        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.journalArticles(journalId);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();

                        JSONArray articlesJson = new JSONArray(responseBody);
                        JSONObject articleJson, subArticleJson;
                        JournalArticles journalArticles;
                        subArticleJson = articlesJson.getJSONObject(0).getJSONObject("journal").getJSONObject("logo_image");
                        full_directory = subArticleJson.getString("full_directory");
                        miniature = subArticleJson.getString("miniature");
                        for (int i = 0; i < articlesJson.length(); i++) {
                            articleJson = articlesJson.getJSONObject(i);
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
                            journalArticles = new JournalArticles(Page_Journals_Fragment.this.getContext(),
                                    articleID,articleBody,articleTitle,articleImages,articleArticleLikes,
                                    articleDate,articleArticleComments,full_directory,miniature);

                            mArticlesJournals.add(journalArticles);
                            checkJournalsArticlesIsEmpty();
                            mAdapter.notifyDataSetChanged();
                        }

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
