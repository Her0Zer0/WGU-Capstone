package com.slyfoxdevelopment.example2.ui.catitem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.example2.R;
import com.slyfoxdevelopment.example2.data.Categories;

import java.util.List;

import static com.slyfoxdevelopment.example2.utils.Constants.CATEGORY_ID_KEY;
import static com.slyfoxdevelopment.example2.utils.Constants.CATEGORY_IS_NEW;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List< Categories > mCategories;
    private final Context mContext;

    public CategoryAdapter(List< Categories > mCategories, Context mContext) {
        this.mCategories = mCategories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.label_list_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Categories category = mCategories.get(position);

        if(category != null){
            holder.cat_item_list_title.setText(category.getTitle());
            holder.cat_item_list_edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext.getApplicationContext(), CategoryEdit.class);
                    intent.putExtra(CATEGORY_ID_KEY, category.getId());
                    intent.putExtra(CATEGORY_IS_NEW, false);

                    mContext.startActivity(intent);
                    ((Activity) view.getContext()).finish();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(mCategories != null){
            return mCategories.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cat_item_list_title;
        ImageButton cat_item_list_edit_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cat_item_list_title = itemView.findViewById(R.id.label_list_title);
            cat_item_list_edit_btn = itemView.findViewById(R.id.label_list_edit_btn);
        }
    }
}
