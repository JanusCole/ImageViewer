package com.example.janus.imageviewer.data.source;

import com.example.janus.imageviewer.data.models.ImageSearchResults;

// Interface for data access

public interface ImageDataSource {

    interface RemoteImagesSearchCallback {

        void onImagesFound(ImageSearchResults returnedImages);
        void onNetworkError();

    }

    void getImages(String searchCriteria, int pageNumber, RemoteImagesSearchCallback remoteImagesSearchCallback);
}
