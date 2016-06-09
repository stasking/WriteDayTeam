package com.annex.writeday.apiservices.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    //---------------------------------------[Регистрация]----------------------------------------//
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> createUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nickname") String nickname,
            @Field("key")      String key);


    //
    //---------------------------------------[Авторизация]----------------------------------------//
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginUser1(
            @Field("username") String username,
            @Field("password") String password,
            @Field("remember-me") String rememberMe);

    //
    //---------------------------------------[Авторизация]----------------------------------------//
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginUser(
            @Field("username") String username,
            @Field("password") String password);

    //---------------------------------------[Добавить пост]--------------------------------------//
    @FormUrlEncoded
    @POST("artticle/add")
    Call<ResponseBody> artticleAdd(
            @Field("title") String title,
            @Field("body") String body,
            @Field("id") String id);

    //

    //---------------------------------------[Email]----------------------------------------//
    @FormUrlEncoded
    @POST("user/update/email")
    Call<ResponseBody> newEmail(
            @Field("email") String email);
    //

    //---------------------------------------[Phone]----------------------------------------//
    @FormUrlEncoded
    @POST("user/update/phone")
    Call<ResponseBody> newPhone(
            @Field("phone") String phone);
    //

    //---------------------------------------[Nickname]----------------------------------------//
    @FormUrlEncoded
    @POST("user/update/nickname")
    Call<ResponseBody> newNickname(
            @Field("nickname") String nickname);
    //

    //---------------------------------------[Image]----------------------------------------//
    @Multipart
    @POST("user/update/image")
    Call<ResponseBody> upload(@Part MultipartBody.Part file);


    //---------------------------------------[Password]----------------------------------------//
    @FormUrlEncoded
    @POST("user/update/password")
    Call<ResponseBody> newPassword(
            @Field("password") String password);
    //

    //---------------------------------------[Username]----------------------------------------//
    @FormUrlEncoded
    @POST("user/update/username")
    Call<ResponseBody> newUsername(
            @Field("username") String username);
    //

    //---------------------------------------[Профиль]--------------------------------------------//
    @GET("profile")
    Call<ResponseBody> userProfile();
    //
    //---------------------------------------[Выход]----------------------------------------------//
    @GET("logout")
    Call<ResponseBody> logoutUser();
    //
    //---------------------------------------[Список журналов]------------------------------------//
    @GET("journal/all")
    Call<ResponseBody> journalAll();
    //
    //---------------------------------------[Список статей журнала]------------------------------//
    @GET("journal/{id}/articles")
    Call<ResponseBody> journalArticles(
            @Path("id")String id);
    //

    //---------------------------------------[Статья]------------------------------//
    @GET("article/id/{id}")
    Call<ResponseBody> loadArticle(
            @Path("id")String id);
    //
}