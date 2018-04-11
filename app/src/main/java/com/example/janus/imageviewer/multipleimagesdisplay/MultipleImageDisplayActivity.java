package com.example.janus.imageviewer.multipleimagesdisplay;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.janus.imageviewer.R;
import com.example.janus.imageviewer.data.source.RemoteImageAPI;
import com.example.janus.imageviewer.data.source.ImageService;
import com.example.janus.imageviewer.data.source.RemoteImageDataSource;
import com.example.janus.imageviewer.data.source.ImageDataSource;

// This is the Activity for the Multiple Image Display. It gets search term as a String passed through the
// through the Intent and, in turn, passes that value on to the Presenter.

// It creates the fragment and then creates a Presenter, passing it a repository, the fragment as the View
// and the search criteria for display

public class MultipleImageDisplayActivity extends AppCompatActivity {

    public static final String IMAGE_SEARCH_CRITERIA = "image_search_criteria";

    MultipleImageDisplayContract.Presenter multipleImagesDisplayPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_images_display_activity);

    // Get the image search criteria
        String searchCriteria = getIntent().getStringExtra(IMAGE_SEARCH_CRITERIA);

    // Create and dispay the multiple image display fragment view
        MultipleImageDisplayFragment multipleImagesDisplayFragment = new MultipleImageDisplayFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.multipleImagesDisplayFrameLaout, multipleImagesDisplayFragment);
        fragmentTransaction.commit();

    // Create the presenter and pass it a repository

        RemoteImageAPI imagesAPI = new RemoteImageAPI();
        ImageDataSource remoteImagesSource = new RemoteImageDataSource(imagesAPI);
        ImageService imagesService = ImageService.getInstance(remoteImagesSource);
        multipleImagesDisplayPresenter = new MultipleImageDisplayPresenter(imagesService, multipleImagesDisplayFragment);

        multipleImagesDisplayPresenter.setSearchCriteria(searchCriteria);

    }
}
