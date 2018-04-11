package com.example.janus.imageviewer.multipleimagesdisplay;

import com.example.janus.imageviewer.data.models.ImageDescription;
import com.example.janus.imageviewer.data.source.ImageService;
import com.example.janus.imageviewer.data.source.ImageServiceInterface;

import java.util.ArrayList;
import java.util.List;

/*
 This is the Presenter class for the view that displays multiple images. It takes a search string and loads corresponding images from a repository.
 It then passes the images as a List to the View for display. When a user selects a single image for display,the Presenter looks up the corresponding
  image ID and passes it back to the View to be transferred to the single image display view.
*/

public class MultipleImageDisplayPresenter implements MultipleImageDisplayContract.Presenter {

    private MultipleImageDisplayContract.View multipleImagesDisplayView;
    private ImageService imagesService;
    private String searchCriteria;

    // Save the current collection of ImageDescriptions. This will be used to get the image ID when a specific image
    // is selected for display
    List <ImageDescription> resultImages = new ArrayList<>();

    // This Presenter supports pagination. Set the current page to zero and increment it whenever data is retrieved
    private int currentPage = 0;

    // Only load pages one at a time
    boolean loadingImages = false;

    public MultipleImageDisplayPresenter(ImageService imagesService, MultipleImageDisplayContract.View multipleImagesDisplayView) {
        this.imagesService = imagesService;
        this.multipleImagesDisplayView = multipleImagesDisplayView;

        multipleImagesDisplayView.setPresenter(this);
    }

    // Receives the search criteria for finding images
    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public void loadImages() {

    // Only retrieve one page at a time
        if (!loadingImages) {

            loadingImages = true;

    // Call the repository for the next page of data
            imagesService.getImages(searchCriteria, ++currentPage, new ImageServiceInterface.ImagesSearchCallback() {
                @Override
                public void onImagesFound(List<ImageDescription> returnedImages) {

    // Save the results to a List to be used when the View requests an image ID
                    resultImages.addAll(returnedImages);

    // Create a List of just image URLs for display
                    List<String> photoURLs = new ArrayList<>();

                    for (ImageDescription resultImage : returnedImages) {
                        photoURLs.add(resultImage.getWebformatURL());
                    }

    // Tell the View to display the List of images
                    multipleImagesDisplayView.displayImages(photoURLs);
                    loadingImages = false;
                }

    // No more images are available
                @Override
                public void endOfDataReached() {
                    loadingImages = false;
                }

    // Tell the View to display an error message if no data could be retrieved
                @Override
                public void onNetworkError() {
                    multipleImagesDisplayView.DisplayNetworkErrorMessage();
                    loadingImages = false;
                }
            });
        }
    }

    // Get the image ID for the image that was selected in the View
    @Override
    public void displayOneImage(int imageIndex) {
        multipleImagesDisplayView.displaySingleImageUI(resultImages.get(imageIndex).getId());
    }

    // Tell the View to finish and pop back
    @Override
    public void exitView() {
        multipleImagesDisplayView.finishActivity();
    }
}
