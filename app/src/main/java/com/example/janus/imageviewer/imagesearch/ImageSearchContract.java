package com.example.janus.imageviewer.imagesearch;

public interface ImageSearchContract {

//  Specifies the contract between the Image Search View and the Image Search Presenter

    interface View {

        void displayImagesUI(String searchCriteria);
        void displayNotFoundMessage();
        void DisplayNetworkErrorMessage();

        void setPresenter(Presenter presenter);

    }

    interface Presenter {

        void loadImages(String searchCriteria);

    }

}
