package com.annex.writeday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position=0;
    private String title;

    public TestFragment newInstance(int position) {
        TestFragment f = new TestFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (savedInstanceState != null) {
//            Log.e("я тут","я тут");
//            title = savedInstanceState.getString("title");
//            //position = savedInstanceState.getInt("position");
//        }
//        else
        position = getArguments().getInt(ARG_POSITION);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        if(position==0){
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            textView.setText(title);
        }

        if(position == 1) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            title = "Здесь должна быть ЛЕНТА";
            textView.setText(title);
        }
        if(position == 2) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            title = "Здесь должна быть МОЙ БЛОГ";
            textView.setText(title);
        }
        if(position == 3) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            title = "Здесь должна быть БЛОГИ";
            textView.setText(title);
        }
        /*if(position == 4) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            textView.setText("Здесь должна быть ЖУРНАЛЫ");
        }*/
        if(position == 5) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            title = "Здесь должна быть СООБЩЕНИЯ";
            textView.setText(title);
        }
        if(position == 6) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            title = "Здесь должна быть ВОКРУГ МЕНЯ";
            textView.setText(title);
        }
        if(position == 7) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            title = "Здесь должна быть FAQ";
            textView.setText(title);
        }
        if(position == 8) {
            TextView textView = (TextView) view.findViewById(R.id.test_textview);
            title = "Здесь должен быть НАСТРОЙКИ";
            textView.setText(title);
        }

        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//
//        savedInstanceState.putString("title",title);
//        savedInstanceState.putInt("position",position);
//
//        //outState = result.saveInstanceState(outState);
//        //super.onSaveInstanceState(outState);
//    }
}
