package com.example.janus.imageviewer.singleimagedisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.janus.imageviewer.data.models.ImageDescription;
import com.example.janus.imageviewer.data.source.ImageService;
import com.example.janus.imageviewer.data.source.ImageServiceInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class SingleImageDisplayPresenter implements SingleImageDisplayContract.Presenter {

    private SingleImageDisplayContract.View singleImageDisplayView;
    private int imageIDToDisplay;
    private ImageService imagesService;
    private Context context;

// TODO Find a more elegant way to fix Picasso's weak Target reference issue
    private Target singleDisplayImage;

/*
 This is the Presenter class for the view that displays a single image. It takes an image Id and checks the repository to get
 the corresponding ImageDescription object. It then passses the image URL and fields like user, comments, likes, etc to the view for display
*/

    public SingleImageDisplayPresenter(SingleImageDisplayContract.View singleImageDisplayView, ImageService imagesService, int imageIDToDisplay, Context context) {
        this.singleImageDisplayView = singleImageDisplayView;
        this.imageIDToDisplay = imageIDToDisplay;
        this.imagesService = imagesService;
        this.context = context;

        this.singleImageDisplayView.setPresenter(this);
    }

    // Go the the repository and request the specific ImageDescription object using the imageID.
    // When it is returned, call the view to display the data fields and call Picasso to get the image bitmap
    @Override
    public void loadImage() {

        imagesService.getImage(imageIDToDisplay, new ImageServiceInterface.ImageSearchCallback() {
            @Override
            public void onImageFound(ImageDescription returnedImage) {
                loadImageFromPicasso (returnedImage);

                singleImageDisplayView.displayPostedBy(returnedImage.getUser());
                singleImageDisplayView.displayFavorites(returnedImage.getFavorites().toString());
                singleImageDisplayView.displayLikes(returnedImage.getLikes().toString());
                singleImageDisplayView.displayComments(returnedImage.getComments().toString());

            }

            @Override
            public void onImageNotFound() {
                singleImageDisplayView.displayImageNotFoundMessage();
            }
        });

    }

    // Get the image bitmap from the image URL using Picasso. Since Picasso is async, specify a Target object
    // with a callback. Then load it to the view. Note that Picasso maintains a weak reference to the Target.
    // So if it takes too long, the Target can get garbage collected. To circumvent this, the Target is declared up
    // above.

    private void loadImageFromPicasso (ImageDescription imageToDisplay) {

        singleDisplayImage = new Target () {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                singleImageDisplayView.displayImage(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                singleImageDisplayView.displayImageNotFoundMessage();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(context).load(imageToDisplay.getWebformatURL()).into(singleDisplayImage);

    }

    // Tell the view to finish and pop back
    @Override
    public void exitView() {
        singleImageDisplayView.finishActivity();
    }
}
