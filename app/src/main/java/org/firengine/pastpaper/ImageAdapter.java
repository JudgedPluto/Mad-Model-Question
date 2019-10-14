package org.firengine.pastpaper;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    List<Map<String, Object>> list;

    public ImageAdapter(List<Map<String, Object>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Map<String, Object> item = list.get(position);
        holder.imageView.setImageBitmap((Bitmap) item.get(ArtistMaster.PhotographDetails.COLUMN_IMAGE));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_holder);
        }
    }
}
