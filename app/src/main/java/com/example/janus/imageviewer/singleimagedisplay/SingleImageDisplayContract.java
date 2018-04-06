package com.example.janus.imageviewer.singleimagedisplay;

import android.graphics.Bitmap;

public interface SingleImageDisplayContract {

//  Specifies the contract between the Single Image View and the Single Image Presenter

    interface View {

        void displayImage(Bitmap singleImage);
        void finishActivity();
        void displayPostedBy(String postedBy);
        void displayFavorites(String favorites);
        void displayLikes(String likes);
        void displayComments(String comments);
        void displayImageNotFoundMessage();

        void setPresenter(Presenter presenter);

    }

    interface Presenter {

        void loadImage();
        void exitView();

    }

}
