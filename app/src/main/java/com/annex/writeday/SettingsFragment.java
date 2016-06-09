package com.annex.writeday;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.annex.writeday.apiservices.PersistentCookieStore;
import com.annex.writeday.apiservices.model.User;
import com.annex.writeday.apiservices.service.APIService;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmQuery;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private String email ;
    private String phone ;
    private String image ;
    private String nickname ;
    private String name ;
    private String password ;

    static final int GALLERY_REQUEST = 1;
    private ImageView imageView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);

        final TextView newEmail =(TextView) v.findViewById(R.id.input_newEmail);
        final TextView newPhone =(TextView) v.findViewById(R.id.input_newPhone);
        final TextView newNickname =(TextView) v.findViewById(R.id.input_newNickname);
        final TextView newName =(TextView) v.findViewById(R.id.input_newName);
        final TextView newPassword =(TextView) v.findViewById(R.id.input_newPassword);


        imageView = (ImageView) v.findViewById(R.id.imageView2);

        Button button2 = (Button) v.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                imageView.setImageURI(null);
            }
        });


        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = newEmail.getText().toString();
                phone = newPhone.getText().toString();
                nickname = newNickname.getText().toString();
                name = newName.getText().toString();
                password = newPassword.getText().toString();


                if (!email.isEmpty()) {
                    Log.e("email", email);
                    newEmail();
                } else
                    Log.e("email", "пусто");

                if (!phone.isEmpty()) {
                    Log.e("phone", phone);
                    newPhone();
                } else
                    Log.e("phone", "пусто");

                if (!nickname.isEmpty()) {
                    Log.e("nickname", nickname);
                    newNickname();
                } else
                    Log.e("nickname", "пусто");

                if (!name.isEmpty()) {
                    Log.e("name", name);
                    newName();
                } else
                    Log.e("name", "пусто");

                if (!password.isEmpty()) {
                    Log.e("password", password);
                    newPassword();
                } else
                    Log.e("password", "пусто");
            }
        });


        return v;
    }

    //получение абсолютного пути к файлу
    private String getImagePath(Intent data) {
        Uri path = data.getData();

        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getActivity().getContentResolver().query(path,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return picturePath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        getImagePath(imageReturnedIntent);
        Log.e("getImagePath", "` " + getImagePath(imageReturnedIntent));

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == -1/*RESULT_OK */){
                    Uri selectedImage = imageReturnedIntent.getData();
                    Log.e("String.valueOf(fileUri)", "` " + selectedImage);

                    uploadFile(getImagePath(imageReturnedIntent));
                    imageView.setImageURI(selectedImage);
                }
        }
    }



    private void newEmail(){
        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.newEmail(email);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {

                        Realm realm = Realm.getInstance(getActivity().getApplicationContext());
                        RealmQuery<User> query = realm.where(User.class);
                        User results = query.findFirst();

                        realm.beginTransaction();
                        results.setEmail(email);
                        realm.commitTransaction();

                        PersistentCookieStore cookieStore = new PersistentCookieStore(getActivity().getApplicationContext());
                        cookieStore.removeAll();

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

    private void newPhone(){
        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.newPhone(phone);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {

                        Realm realm = Realm.getInstance(getActivity().getApplicationContext());
                        RealmQuery<User> query = realm.where(User.class);
                        User results = query.findFirst();

                        realm.beginTransaction();
                        results.setPhone(phone);
                        realm.commitTransaction();

                        PersistentCookieStore cookieStore = new PersistentCookieStore(getActivity().getApplicationContext());
                        cookieStore.removeAll();

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

    private void newNickname(){
        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.newNickname(nickname);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {

                        Realm realm = Realm.getInstance(getActivity().getApplicationContext());
                        RealmQuery<User> query = realm.where(User.class);
                        User results = query.findFirst();

                        realm.beginTransaction();
                        results.setNickname(nickname);
                        realm.commitTransaction();

                        PersistentCookieStore cookieStore = new PersistentCookieStore(getActivity().getApplicationContext());
                        cookieStore.removeAll();

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

    private void newName(){
        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.newUsername(name);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {

                        Realm realm = Realm.getInstance(getActivity().getApplicationContext());
                        RealmQuery<User> query = realm.where(User.class);
                        User results = query.findFirst();

                        realm.beginTransaction();
                        results.setUsername(name);
                        realm.commitTransaction();

                        PersistentCookieStore cookieStore = new PersistentCookieStore(getActivity().getApplicationContext());
                        cookieStore.removeAll();

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

    private void newPassword(){
        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        Call<ResponseBody> result = service.newPassword(password);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        Log.e("response", "" + response.isSuccessful());

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

    private void uploadFile(String fileUri) {

        final MyApplication mApplication = (MyApplication) getActivity().getApplicationContext();
        APIService service = mApplication.getRestClient();

        File file = new File(fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // finally, execute the request
        Call<ResponseBody> result = service.upload(body);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.e("response", "" + response.isSuccessful());

                    } catch (Exception e) {
                        Log.e("Failed 2", "" + e.getMessage());
                    }
                } else {
                    Log.e("Failed 2", "1");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}
