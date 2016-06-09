package com.annex.writeday.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annex.writeday.MyApplication;
import com.annex.writeday.R;
import com.annex.writeday.apiservices.model.User;

public class ProfileInfoFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_info, container, false);

        //TextView email =(TextView) v.findViewById(R.id.email);
        //TextView phone =(TextView) v.findViewById(R.id.phone);
        TextView name =(TextView) v.findViewById(R.id.name);
        //TextView nickname =(TextView) v.findViewById(R.id.nickname);

        MyApplication application = (MyApplication) getActivity().getApplicationContext();
        User user = application.getLocalUser(getActivity().getApplicationContext());

       /*email.setText(user.getEmail());
        phone.setText(user.getPhone());*/
        name.setText(user.getUsername());
        //nickname.setText(user.getNickname());
        return v;
    }

}
