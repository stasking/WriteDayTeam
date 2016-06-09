package com.annex.writeday.journals;

import android.content.Context;
import android.util.Log;

import com.annex.writeday.apiservices.model.User;
import java.util.ArrayList;

public class Journal {
    private Context context;
    private String journalId;
    private User user;

    private String mTitle;
    private String mDescription;
    private String mImage;
    private String mDate;

    private String full_directory;
    private String image_category;
    private String full_image;
    private String miniature;


    /*public Journal(Context context*//*, String journalId*//*, String journalName, User user*//*, ArrayList<String> subscribers*//**//*,
                   ArrayList<String> images*//*) {
        this.context = context;
        this.journalId = journalId;
        this.mTitle = journalName;
        this.user = user;
        this.subscribers = subscribers;
        //setImages(images, imageView);

        // TODO remove stub
        mDescription = "The quick brown fox jumps over the lazy dog. Pack my box with five dozen liquor jugs. " +
                "The five boxing wizards jump quickly. How vexingly quick daft zebras jump!";
    }*/

    public Journal() {
    }
    public Journal(Context context, String journalName, String full_directory, String image_category,
                   String full_image, String miniature, String date, String id){
        this.context = context;
        this.mTitle = journalName;
        this.mImage = "http://hosting.legendstem.ru:8080/images/" + full_directory + miniature;
        this.full_directory = full_directory;
        this.image_category = image_category;
        this.full_image = full_image;
        this.miniature = miniature;
        this.mDate = date;
        this.journalId = id;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getDate() {
        return mDate;
    }


    public String getDescription() {
        return mDescription;
    }

    public String getURLImage() {
        return mImage;
    }

    public String getJournalId() {
        return journalId;
    }

    public User getUser() {
        return user;
    }



    /*public ArrayList<String> getSubscribers() {
        return subscribers;
    }



    /*public static class Builder {

        private String mTitle;
        private String mDescription;
        private Drawable mImage;

        public Builder title(String title) {
            mTitle = title;
            return this;
        }

        public Builder description(String description) {
            mDescription = description;
            return this;
        }

        public Builder image(Drawable image) {
            mImage = image;
            return this;
        }

        public Journal build() {
            Journal journal = new Journal();
            journal.mTitle = mTitle;
            journal.mDescription = mDescription;
            journal.mImage = mImage;
            return journal;
        }
    }*/
}