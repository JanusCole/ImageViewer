package com.example.janus.imageviewer.data.source;

import android.support.annotation.VisibleForTesting;

import com.example.janus.imageviewer.data.models.ImageSearchResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// This class implements the Pixabay API. It contains both the Pixabay GET API interface and a constant with the Base URL

public class RemoteImageService {

// Define the API's Base URL
    private String BASE_URL = "https://pixabay.com/";

// Define the actual API parameters and format
    public interface ImagesInterface {
        @GET("api/")
        Call<ImageSearchResults> getImages(
                @Query("key") String apiKey,
                @Query("q") String searchCriteria,
                @Query("page") int pageNumber);
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    @VisibleForTesting
    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

}
