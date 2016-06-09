package com.annex.writeday.journals;


import android.content.Context;

public class JournalArticles {
    private Context context;
    private String articleID;
    private String articleBody;
    private String articleTitle;
    private String articleImages;
    private String articleArticleLikes;
    private String articleDate;
    private String articleArticleComments;
    private String mImageJournal;

    public JournalArticles(Context context,String articleID,String articleBody,String articleTitle,
                           String articleImages,String articleArticleLikes,String articleDate,
                           String articleArticleComments,String full_directory,String miniature) {
        this.context = context;
        this.articleID = articleID;
        this.articleBody = articleBody;
        this.articleTitle = articleTitle;
        this.articleImages = articleImages;
        this.articleArticleLikes = articleArticleLikes;
        this.articleDate = articleDate;
        this.articleArticleComments = articleArticleComments;

        this.mImageJournal = "http://hosting.legendstem.ru:8080/images/" + full_directory + miniature;
    }

    public String getArticleID() {
        return articleID;
    }
    public String getArticleTitle() {
        return articleTitle;
    }
    public String getURLImage() {
        return mImageJournal;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public String getArticleDescription() {
        return articleBody;
    }

    public String getArticleURLImage() {
        return articleImages;
    }


}
