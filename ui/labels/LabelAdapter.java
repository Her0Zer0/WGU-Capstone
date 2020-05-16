package com.slyfoxdevelopment.example2.ui.labels;

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
import com.slyfoxdevelopment.example2.data.Label;

import java.util.List;

import static com.slyfoxdevelopment.example2.utils.Constants.LABEL_ID_KEY;
import static com.slyfoxdevelopment.example2.utils.Constants.LABEL_IS_NEW;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {

    private final List< Label > mLabels;
    private final Context mContext;

    public LabelAdapter(List< Label > mLabels, Context mContext) {
        this.mLabels = mLabels;
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
        final Label label = mLabels.get(position);
        holder.label_list_color.setBackgroundColor(Integer.parseInt(label.getColor()));
        holder.label_list_title.setText(label.getTitle());
        holder.label_list_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: set data for edit in intent
                Intent intent = new Intent(mContext.getApplicationContext(), LabelEdit.class);
                intent.putExtra(LABEL_ID_KEY, label.getId());
                intent.putExtra(LABEL_IS_NEW, false);
                mContext.startActivity(intent);
                ((Activity)view.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mLabels != null){
            return mLabels.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView label_list_title, label_list_color;
        ImageButton label_list_edit_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label_list_edit_btn = itemView.findViewById(R.id.label_list_edit_btn);
            label_list_title = itemView.findViewById(R.id.label_list_title);
            label_list_color = itemView.findViewById(R.id.label_list_color);

        }
    }
}
