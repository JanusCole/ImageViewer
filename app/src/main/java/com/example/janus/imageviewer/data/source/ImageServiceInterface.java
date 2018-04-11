package com.example.janus.imageviewer.data.source;

import com.example.janus.imageviewer.data.models.ImageDescription;

import java.util.List;

// Main entry point for accessing images. It supports single and multiple image searches

public interface ImageServiceInterface {


    interface ImagesSearchCallback {

        void onImagesFound(List<ImageDescription> returnedImages, int imageCount);
        void endOfDataReached();
        void onNetworkError();

    }

    interface ImageSearchCallback {

        void onImageFound(ImageDescription returnedImage);
        void onImageNotFound();

    }


    void getImages(String searchCriteria, int pageNumber, ImagesSearchCallback imagesSearchCallback);
    void getImage(int imageID, ImageSearchCallback imagesSearchCallback);
}
