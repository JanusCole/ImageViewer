package com.example.janus.imageviewer.multipleimagesdisplay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.janus.imageviewer.R;
import com.example.janus.imageviewer.util.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
 This is the adapter that handles the images for multiple image display. It takes an onclick listener for when the user
 selects an image for display. It also has an "add" method for adding more images to the view. It uses Picasso to load
 the image bitmaps based on the URLs in the List.
*/

public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder> {

    // This is the lst of image URLs for display
    List<String> imageURLs;
    Context context;

    OnItemClickedListener onItemClickedListener;

    public ImagesRecyclerViewAdapter(List<String> imageURLs, Context context, OnItemClickedListener onItemClickedListener) {
        this.imageURLs = imageURLs;
        this.context = context;
        this.onItemClickedListener = onItemClickedListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private SquareImageView imageForDisplay;

        public ViewHolder(View itemView) {
            super(itemView);

            imageForDisplay = (SquareImageView) itemView.findViewById(R.id.imagesRecyclerView_ImageView);

            // Passes back the index of the image in the List.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    onItemClickedListener.onItemClicked(itemView, getAdapterPosition());
                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View thisItemsView = inflater.inflate(R.layout.images_recyclerview_item,parent, false);

        return new ViewHolder(thisItemsView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(imageURLs.get(position)).into(holder.imageForDisplay);
    }

    @Override
    public int getItemCount() {
        return imageURLs.size();
    }


    // Allows for adding new item to the List o fimage URLs. This is to support pagination
    public void addImageURLs(List<String> newImageURLs) {
        imageURLs.addAll(newImageURLs);
        notifyDataSetChanged();
    }

    // Passes back the index of the image in the List.
    public interface OnItemClickedListener {
        void onItemClicked(View view, int imageIndex);
    }

}
