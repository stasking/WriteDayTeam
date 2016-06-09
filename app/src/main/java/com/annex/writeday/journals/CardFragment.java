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


public class CardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private RecyclerView mJournalsView;
    private List<Journal> mJournals = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private String full_directory;
    private String image_category;
    private String full_image;
    private String miniature;
    private String date;
    private String id;

    ProgressDialog progressDialog;

    public static CardFragment newInstance(int position) {
        CardFragment f = new CardFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
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

        position = getArguments().getInt(ARG_POSITION);

        mAdapter = new JournalAdapter(getActivity().getApplicationContext(), mJournals,(MainActivity) getActivity());
    }

    private TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_journals, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mJournalsView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mJournalsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mJournalsView.setAdapter(mAdapter);

        emptyView = (TextView) view.findViewById(R.id.empty_view);

        checkJournalsIsEmpty();

        if(position == 0)
            loadAllJournals();
    }

    private void checkJournalsIsEmpty() {

        if (mJournals.isEmpty()) {
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

    private void loadAllJournals() {
        progressDialog.show();
        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.journalAll();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        String responseBody = response.body().string();

                        mJournals.clear();

                        JSONArray journalsJson = new JSONArray(responseBody);
                        JSONObject journalJson, subJournalJson;
                        Journal journal;

                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



                                //TODO НЕ ЗАБЫТЬ РЕШИТЬ ВОПРОС С ID ЮЗЕРА!!!!!!!!!!!!

                                //  он у меня нигде не используются.



                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                        for (int i = 0; i < journalsJson.length(); i++) {

                            journalJson = journalsJson.getJSONObject(i);

                            try {
                                subJournalJson = journalJson.getJSONObject("logo_image");
                                id = journalJson.get("id").toString();
                                full_directory = subJournalJson.get("full_directory").toString();
                                image_category = subJournalJson.get("image_category").toString();
                                full_image = subJournalJson.get("full_image").toString();
                                miniature = subJournalJson.get("miniature").toString();
                                date = subJournalJson.get("date").toString();
                            } catch (JSONException e) {
                                id = "";
                                date = "";
                                e.printStackTrace();
                            }

                            journal = new Journal(CardFragment.this.getContext(),
                                    journalJson.getString("name"),full_directory,image_category,
                                    full_image,miniature,date,id);

                            mJournals.add(journal);
                            checkJournalsIsEmpty();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e("Failed 1", "" + e.getMessage());
                    }
                }
                else
                {
                    Log.e("Failed 1", "1");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("Failed 1", "T: " + t.getMessage());

            }
        });

    }

}
