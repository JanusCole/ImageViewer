package com.example.janus.imageviewer.data.source;

import com.example.janus.imageviewer.data.models.ImageDescription;
import com.example.janus.imageviewer.data.models.ImageSearchResults;

import java.util.List;

// Interface for data access

public interface ImageDataSource {

    interface RemoteImagesSearchCallback {

        void onImagesFound(List<ImageDescription> returnedImages, int imageCount);
        void onNetworkError();

    }

    void getImages(String searchCriteria, int pageNumber, RemoteImagesSearchCallback remoteImagesSearchCallback);
}
