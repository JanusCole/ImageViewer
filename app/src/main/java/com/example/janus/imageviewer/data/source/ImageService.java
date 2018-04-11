package com.example.janus.imageviewer.data.source;

import com.example.janus.imageviewer.data.models.ImageDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Concrete implementation to load images from a data source to a cache

public class ImageService implements ImageServiceInterface {

    private static ImageService INSTANCE = null;

    private ImageService imageDataSource;

// This is the image cache. It saves ImageDescription objects in a HashMap keyed on the image ID
    Map<Integer,ImageDescription> imageCache = new HashMap<>();

// These fields are used to determine if the cache can satisfy the current image request
    String lastSearchCriteria;
    int lastPageNumber = 0;
    int totalImagesAvailable = 0;

    private ImageService(ImageService imageDataSource) {
        this.imageDataSource = imageDataSource;
    }

// There an be only one
    public static ImageService getInstance (ImageService imageDataSource) {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = new ImageService(imageDataSource);
            }
        }
        return INSTANCE;
    }

    @Override
    public void getImages(final String searchCriteria, final int requestedPageNumber, final ImagesSearchCallback imagesSearchCallback) {

        // Check to see if the cache can satisfy the current request
        if (isaNewRequest(searchCriteria, requestedPageNumber)) {

            // Check to see if we have reached the end of the available data
            if (endOfDataReached(searchCriteria)) {
                imagesSearchCallback.endOfDataReached();
            } else {

        // Search the data source using the passed search criteria and a page number
                imageDataSource.getImages(searchCriteria, requestedPageNumber, new ImageService.ImagesSearchCallback() {
                    @Override
                    public void onImagesFound(List<ImageDescription> returnedImages, int imageCount) {

                        // Update the number of available images for this request
                        totalImagesAvailable = imageCount;

                        // Load the newly retrieved ImageDescription objects to the cache
                        if (!searchCriteria.equals(lastSearchCriteria)) {
                            lastSearchCriteria = searchCriteria;
                            imageCache.clear();
                        }

                        for (ImageDescription returnedImage : returnedImages) {
                            imageCache.put(returnedImage.getId(), returnedImage);
                        }

                        // Update the most recently retrieved page number
                        lastPageNumber = requestedPageNumber;

                        // Return the retrieved ImageDescription objects as a List
                        imagesSearchCallback.onImagesFound(returnedImages, imageCount);
                    }

                    @Override
                    public void endOfDataReached() {
                        imagesSearchCallback.endOfDataReached();
                    }

                    @Override
                    public void onNetworkError() {
                        imagesSearchCallback.onNetworkError();
                    }
                });
            }
        } else {

            // If the cache can satisfy the current request, then send back the cached ImageDescription objects as a List
            List<ImageDescription> resultList = new ArrayList<>();

            for (Map.Entry<Integer, ImageDescription> imageEntry : imageCache.entrySet()) {
                resultList.add(imageEntry.getValue());
            }

            imagesSearchCallback.onImagesFound(resultList, totalImagesAvailable);
        }
    }

    // Check to see if we have reached the last available image
    private boolean endOfDataReached(String searchCriteria) {
        return searchCriteria.equals(lastSearchCriteria) && (imageCache.size() >= totalImagesAvailable);
    }

    // Check to see if the cache can satisfy the current request
    private boolean isaNewRequest(String searchCriteria, int requestedPageNumber) {
        return (!searchCriteria.equals(lastSearchCriteria)) || (requestedPageNumber > lastPageNumber);
    }

    // Search the cache for a specific image using the image ID as a key
    @Override
    public void getImage(int imageID, ImageSearchCallback imageSearchCallback) {

        if (imageCache.containsKey(imageID)) {
            imageSearchCallback.onImageFound(imageCache.get(imageID));
        } else {
            imageSearchCallback.onImageNotFound();
        }

    }
}
