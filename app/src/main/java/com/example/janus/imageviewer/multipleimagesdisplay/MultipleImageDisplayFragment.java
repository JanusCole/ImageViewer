package com.example.janus.imageviewer.multipleimagesdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.janus.imageviewer.R;
import com.example.janus.imageviewer.singleimagedisplay.SingleImageDisplayActivity;
import com.example.janus.imageviewer.util.DisplayFormattedMessages;

import java.util.ArrayList;
import java.util.List;

// This is the View that displays multiple images.

public class MultipleImageDisplayFragment extends Fragment implements MultipleImageDisplayContract.View, ImagesRecyclerViewAdapter.OnItemClickedListener {

    private MultipleImageDisplayContract.Presenter multipleImagesDisplayPresenter;

    private RecyclerView imagesRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.multiple_images_display_fragment, container, false);

    // Get the display fields from the layout
        imagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.multipleImagesDisplayFragment_RecyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.image_grid_columns));
        imagesRecyclerView.setLayoutManager(gridLayoutManager);
        imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter(new ArrayList<String>(), getContext(), this);
        imagesRecyclerView.setAdapter(imagesRecyclerViewAdapter);

    // For smooth scrolling. When the last image in the adapter is being displayed, call the Presenter to load another
    // page of data.
        imagesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if ((gridLayoutManager.findLastVisibleItemPosition() + 1) >= imagesRecyclerViewAdapter.getItemCount()) {
                    multipleImagesDisplayPresenter.loadImages();
                }
            }
        });

    // Call the Presenter to load the first page of images
        multipleImagesDisplayPresenter.loadImages();

        Button returnButton = (Button) rootView.findViewById(R.id.multipleImagesDisplayReturn_Button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturnButtonClicked();
            }
        });

        return rootView;
    }

    // Set the Presenter for this View
    @Override
    public void setPresenter(MultipleImageDisplayContract.Presenter imageSearchPresenter) {
        this.multipleImagesDisplayPresenter = imageSearchPresenter;
    }

    // When new data is retrieved by the Presenter, add the new images to the recyclerview adapter
    @Override
    public void displayImages(List<String> imageURLs) {
        imagesRecyclerViewAdapter.addImageURLs(imageURLs);
    }

    // Transfer control to the single image view for displaying one image. The single image view takes an image ID
    // as an integer as an Extra in the Intent.
    @Override
    public void displaySingleImageUI(int imageID) {
        Intent singleImageDisplayActivityIntent = new Intent(getContext(), SingleImageDisplayActivity.class);
        singleImageDisplayActivityIntent.putExtra(SingleImageDisplayActivity.SINGLE_IMAGE, imageID);
        startActivity(singleImageDisplayActivityIntent);
    }

    // Display an error message if no images are available
    @Override
    public void DisplayNetworkErrorMessage() {
        DisplayFormattedMessages.displayErrorMessageAlertDialog(getString(R.string.network_error_message), getActivity(), getContext());
    }

    // When the user selects a single image for display, pass the image index from the adapter List to the Presenter
    @Override
    public void onItemClicked(View view, int imageIndex) {
        multipleImagesDisplayPresenter.displayOneImage(imageIndex);
    }

    // Back button to leave the view
    @Override
    public void finishActivity() {
    getActivity().finish();
}

    private void onReturnButtonClicked() {
        multipleImagesDisplayPresenter.exitView();
    }

}
