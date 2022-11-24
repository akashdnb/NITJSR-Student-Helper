package com.example.nitjsrstudenthelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitjsrstudenthelper.R;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;

import java.util.List;

public class childNoteAdapter extends RecyclerView.Adapter<childNoteAdapter.viewHolder> {

    private List<ChildNoteItem> childItemList;
    private Context context;
    int i=1;

    public childNoteAdapter(Context context, List<ChildNoteItem> childItemList) {
        this.context= context;
        this.childItemList = childItemList;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_notes_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull childNoteAdapter.viewHolder holder, int position) {
        holder.title.setText(childItemList.get(position).getTitle());
        holder.fileName.setText(childItemList.get(position).getFileName());
        holder.size.setText(childItemList.get(position).getSize());

        //Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return childItemList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title, fileName, size;
        ImageView downloadBtn;
        ProgressBar progressBar;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title_tv);
            fileName= itemView.findViewById(R.id.file_name_tv);
            size= itemView.findViewById(R.id.size_tv);
            downloadBtn= itemView.findViewById(R.id.download_btn);
            progressBar= itemView.findViewById(R.id.downloads_pb);
        }
    }
}
