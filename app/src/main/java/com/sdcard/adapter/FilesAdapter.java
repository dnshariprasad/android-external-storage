package com.sdcard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdcard.R;

import java.io.File;
import java.util.List;

/**
 * Created by Hari on 8/2/16.
 */
public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.BasicHolder> {
    private Context context;
    private List<File> files;

    public FilesAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
    }

    public void notify(List<File> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @Override
    public FilesAdapter.BasicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.row_file, parent, false);
        BasicHolder basicHolder = new BasicHolder(view);
        return basicHolder;
    }

    @Override
    public void onBindViewHolder(FilesAdapter.BasicHolder holder, int position) {
        holder.tv_title.setText(files.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class BasicHolder extends RecyclerView.ViewHolder {
        TextView tv_title;

        public BasicHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
