package com.example.janus.imageviewer.data.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Immutable model class for an Image Search results.
 */

public class ImageSearchResults {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("totalHits")
    @Expose
    private Integer totalHits;
    @SerializedName("hits")
    @Expose
    private List<ImageDescription> hits = null;

    public Integer getTotal() {
        return total;
    }

    public Integer getTotalHits() {
        return totalHits;
    }

    public List<ImageDescription> getHits() {
        return hits;
    }

}
