package com.example.janus.imageviewer.imagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.janus.imageviewer.multipleimagesdisplay.MultipleImageDisplayActivity;
import com.example.janus.imageviewer.R;
import com.example.janus.imageviewer.util.DisplayFormattedMessages;

// This is the View that displays the input text field for searching images.

public class ImageSearchFragment extends Fragment implements ImageSearchContract.View {

    private ImageSearchContract.Presenter imageSearchPresenter;

    EditText imageSearchCriteria;
    Button imageSearchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.image_search_fragment, container, false);

        // Get the display fields from the layout
        imageSearchCriteria = (EditText) rootView.findViewById(R.id.imageSearchCriteriaEditText);
        imageSearchButton = (Button) rootView.findViewById(R.id.imageSearchButton);

        // Set the search button to call the Presenter with the search criteria when clicked
        imageSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImages(imageSearchCriteria.getText().toString());
            }
        });

        return rootView;
    }

    // When the user clicks the Search button, call the Presenter to perform the search on the text in the search field
    private void searchImages (String searchCriteria) {
        imageSearchPresenter.loadImages(searchCriteria);
    }

    // Set the Presenter for this View
    @Override
    public void setPresenter(ImageSearchContract.Presenter imageSearchPresenter) {
        this.imageSearchPresenter = imageSearchPresenter;
    }

    // If results were found, pass control to the multiple images display view. Pass the search criteria as an Extra
    // in the Intenet so the multiple image view can retrieve the data from the repository
    @Override
    public void displayImagesUI(String searchCriteria) {
        Intent multipleImagesDisplayActivityIntent = new Intent(getContext(), MultipleImageDisplayActivity.class);
        multipleImagesDisplayActivityIntent.putExtra(MultipleImageDisplayActivity.IMAGE_SEARCH_CRITERIA, searchCriteria);
        startActivity(multipleImagesDisplayActivityIntent);
    }

    // If no images satisfy the user's search criteria, display a message
    @Override
    public void displayNotFoundMessage() {
        DisplayFormattedMessages.displayErrorMessageAlertDialog(getString(R.string.not_found_message), getActivity(), getContext());
    }

    // If no images are available at all, display an error message
    @Override
    public void DisplayNetworkErrorMessage() {
        DisplayFormattedMessages.displayErrorMessageAlertDialog(getString(R.string.network_error_message), getActivity(), getContext());
    }

}
