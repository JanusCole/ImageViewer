package com.example.janus.imageviewer.data.source;

import com.example.janus.imageviewer.BuildConfig;
import com.example.janus.imageviewer.data.models.ImageSearchResults;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// This class calls a REST API using Retrofit.

public class RemoteImageDataSource implements ImageDataSource {

// This implements the remote data source REST API
    RemoteImageAPI remoteImageService;

    public RemoteImageDataSource(RemoteImageAPI remoteImageService) {
        this.remoteImageService = remoteImageService;
    }

    public void getImages (String searchCriteria, int pageNumber, final ImageSearchCallback imageSearchCallback) {

// Format the search criteria string for query
        String formattedSearchCriteria = searchCriteria.replace(" ", "+");

// Create the Retrofit client
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(remoteImageService.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        RemoteImageAPI.ImagesInterface imagesAPIClient = retrofit.create(RemoteImageAPI.ImagesInterface.class);

        Call<ImageSearchResults> imageSearchCall = imagesAPIClient.getImages(BuildConfig.APIKEY,formattedSearchCriteria, pageNumber);

// Call enqueue to get the results and define a callback
        imageSearchCall.enqueue(new Callback<ImageSearchResults>() {
            @Override
            public void onResponse(Call<ImageSearchResults> call, Response<ImageSearchResults> response) {

                if (response.isSuccessful()) {
                    imageSearchCallback.onImagesFound(response.body().getHits(), response.body().getTotal());
                } else {
                    imageSearchCallback.onNetworkError();
                }
            }

            @Override
            public void onFailure(Call<ImageSearchResults> call, Throwable t) {
                imageSearchCallback.onNetworkError();
             }
        });


    }


}
