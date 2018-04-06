package com.example.janus.imageviewer.singleimagedisplay;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.janus.imageviewer.R;
import com.example.janus.imageviewer.util.DisplayFormattedMessages;
import com.example.janus.imageviewer.util.SquareImageView;

// This is the View that displays a single image.

public class SingleImageDisplayFragment extends Fragment implements SingleImageDisplayContract.View {

    private SingleImageDisplayContract.Presenter singleImageDisplayPresenter;

    SquareImageView imageForDisplay;
    TextView postedBy;
    TextView favorites;
    TextView likes;
    TextView comments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.single_image_display_fragment, container, false);

    // Get the display fields from the layout
        imageForDisplay = (SquareImageView) rootView.findViewById(R.id.singleImage_ImageView);
        postedBy = (TextView) rootView.findViewById(R.id.postedBy_TextView);
        favorites = (TextView) rootView.findViewById(R.id.favorites_TextView);
        likes = (TextView) rootView.findViewById(R.id.likes_TextView);
        comments = (TextView) rootView.findViewById(R.id.comments_TextView);

        Button returnButton = (Button) rootView.findViewById(R.id.singleImageDisplayReturn_Button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturnButtonClicked();
            }
        });

    // Call the Presenter to obtain the image information and bitmap
        singleImageDisplayPresenter.loadImage();

        return rootView;
    }

    // Set the Presenter for this View
    @Override
    public void setPresenter(SingleImageDisplayContract.Presenter imageSearchPresenter) {
        this.singleImageDisplayPresenter = imageSearchPresenter;
    }


    // Display image fields as passed by the Presenter
    @Override
    public void displayImage(Bitmap singleImage) {
        imageForDisplay.setImageBitmap(singleImage);
    }

    @Override
    public void displayPostedBy(String postedBy) {
        this.postedBy.setText(postedBy);
    }

    @Override
    public void displayFavorites(String favorites) {
        this.favorites.setText(favorites);
    }

    @Override
    public void displayLikes(String likes) {
        this.likes.setText(likes);
    }

    @Override
    public void displayComments(String comments) {
        this.comments.setText(comments);
    }

    // Back button to leave the view
    private void onReturnButtonClicked() {
        singleImageDisplayPresenter.exitView();
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    // Error message in case the image is not available
    @Override
    public void displayImageNotFoundMessage() {
        DisplayFormattedMessages.displayErrorMessageAlertDialog(getString(R.string.not_found_message), getActivity(), getContext());
    }
}
