package com.example.janus.imageviewer.singleimagedisplay;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.janus.imageviewer.R;
import com.example.janus.imageviewer.data.source.ImageServiceInterface;
import com.example.janus.imageviewer.data.source.RemoteImageAPI;
import com.example.janus.imageviewer.data.source.ImageService;
import com.example.janus.imageviewer.data.source.RemoteImageDataSource;

// This is the Activity for the Single Image Display. It gets an image ID as an integer passed through the
// through the Intent and, in turn, passes that value on to the Presenter.

// It creates the fragment and then creates a Presenter, passing it a repository, the fragment as the View
// and the imageID for display

public class SingleImageDisplayActivity extends AppCompatActivity {

    public static final String SINGLE_IMAGE = "single_image_id";

    SingleImageDisplayContract.Presenter singleImageDisplayPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_display_activity);

    // Get the requested image ID
        int imageForDisplay = (int) getIntent().getSerializableExtra(SINGLE_IMAGE);

    // Create and dispay the multiple image display fragment view
        SingleImageDisplayFragment singleImageDisplayFragment = new SingleImageDisplayFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.singleImageDisplayFrameLaout, singleImageDisplayFragment);
        fragmentTransaction.commit();

    // Create the presenter and pass it a repository
        RemoteImageAPI imagesAPI = new RemoteImageAPI();
        ImageServiceInterface remoteImagesSource = new RemoteImageDataSource(imagesAPI);
        ImageService imagesService = ImageService.getInstance(remoteImagesSource);
        singleImageDisplayPresenter = new SingleImageDisplayPresenter(singleImageDisplayFragment, imagesService, imageForDisplay, getApplicationContext());

    }
}
