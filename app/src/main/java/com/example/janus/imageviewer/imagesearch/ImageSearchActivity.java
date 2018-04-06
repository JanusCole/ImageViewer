package com.example.janus.imageviewer.imagesearch;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.janus.imageviewer.R;
import com.example.janus.imageviewer.data.source.RemoteImageService;
import com.example.janus.imageviewer.data.source.ImageRepository;
import com.example.janus.imageviewer.data.source.RemoteImageDataSource;
import com.example.janus.imageviewer.data.source.ImageDataSource;

// This is the Activity for the Image Search Display. It gets a search term from the user and performs an image search.
// If it gets any results, it passes them on the multiple image display view through an Intent aas an extra.

// It creates the fragment and then creates a Presenter, passing it a repository and the fragment as the View

public class ImageSearchActivity extends AppCompatActivity {

    ImageSearchContract.Presenter imageSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_search_activity);

    // Create and display the image search fragment view
        ImageSearchFragment imageSearchFragment = new ImageSearchFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.imageSearchFrameLaout, imageSearchFragment);
        fragmentTransaction.commit();

        // Create the presenter and pass it a repository

        RemoteImageService imagesAPI = new RemoteImageService();
        ImageDataSource remoteImagesSource = new RemoteImageDataSource(imagesAPI);
        ImageRepository imagesService = ImageRepository.getInstance(remoteImagesSource);
        imageSearchPresenter = new ImageSearchPresenter(imagesService, imageSearchFragment);

    }
}
