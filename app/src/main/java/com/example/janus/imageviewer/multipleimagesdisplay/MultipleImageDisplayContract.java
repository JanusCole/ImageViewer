package com.example.janus.imageviewer.multipleimagesdisplay;

import java.util.List;

public interface MultipleImageDisplayContract {

//  Specifies the contract between the Multiple Image View and the Multiple Image Presenter

    interface View {

        void displayImages(List<String> mageURLs);
        void displaySingleImageUI(int imageID);
        void DisplayNetworkErrorMessage();
        void setPresenter(Presenter presenter);
        void finishActivity();

    }

    interface Presenter {

        void loadImages();
        void displayOneImage(int imageIndex);
        void setSearchCriteria(String searchCritera);
        void exitView();
    }

}
