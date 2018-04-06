package com.example.janus.imageviewer.imagesearch;

import com.example.janus.imageviewer.data.models.ImageDescription;
import com.example.janus.imageviewer.data.source.ImageRepository;
import com.example.janus.imageviewer.data.source.Repository;

import java.util.List;

/*
 This is the Presenter class for the view that searches for images. It takes a search string and loads corresponding images from a repository.
 If it successfully finds images, then it tells the View to pass control to the multiple images display view. Otherwise it tells the search View to display
  an error.
*/

public class ImageSearchPresenter implements ImageSearchContract.Presenter {

    private ImageSearchContract.View imageSearchView;
    private ImageRepository imagesService;

    public ImageSearchPresenter(ImageRepository imagesService, ImageSearchContract.View imageSearchView) {
        this.imagesService = imagesService;
        this.imageSearchView = imageSearchView;

        imageSearchView.setPresenter(this);
    }

    public void loadImages(final String searchCriteria) {
        // Call the repository and pass it the search criteria entered by the user.
        imagesService.getImages(searchCriteria, 1, new Repository.ImagesSearchCallback() {
            @Override
            public void onImagesFound(List<ImageDescription> returnedImages) {
                // If the List of results has size zero, then no images were found
                if (returnedImages.size() == 0) {
                    imageSearchView.displayNotFoundMessage();
                } else {
                // Otherwise...return the search criteria to be passed to the multiple images display
                    imageSearchView.displayImagesUI(searchCriteria);
                }
            }

            // If no images are found, show a message
            @Override
            public void endOfDataReached() {
                imageSearchView.displayNotFoundMessage();
            }

            // If no data is available at all, show an error message
            @Override
            public void onNetworkError() {
                imageSearchView.DisplayNetworkErrorMessage();
            }
        });
    }
}
