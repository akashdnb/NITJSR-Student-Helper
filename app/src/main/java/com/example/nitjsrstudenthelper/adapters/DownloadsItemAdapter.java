package com.example.nitjsrstudenthelper.adapters;

import android.app.AlertDialog;
import android.app.UiAutomation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitjsrstudenthelper.R;
import com.example.nitjsrstudenthelper.activities.DownloadsActivity;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;
import com.example.nitjsrstudenthelper.models.NoteItem;

import java.io.File;
import java.util.List;

public class DownloadsItemAdapter extends RecyclerView.Adapter<DownloadsItemAdapter.viewHolder> {
    private Context context;
    private List<ChildNoteItem> itemList;

    public DownloadsItemAdapter(Context context, List<ChildNoteItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public DownloadsItemAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_notes_item, parent, false);
        return new DownloadsItemAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadsItemAdapter.viewHolder holder, int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.fileName.setText(itemList.get(position).getFileName());
        holder.size.setText(itemList.get(position).getSize());
        holder.progressBar.setVisibility(View.GONE);
        holder.downloadBtn.setImageResource(R.drawable.ic_delete);

        holder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDF(itemList.get(position).getUri());
            }
        });

        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
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

    private void handleClick(int position) {

        AlertDialog dialog= new AlertDialog.Builder(context)
                .setTitle("Delete file!!")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file= new File(itemList.get(position).getUri());
                        boolean delete= file.delete();
                        if(delete){
                            Toast.makeText(context, "Deleted!!", Toast.LENGTH_SHORT).show();
                            ((DownloadsActivity)context).setUpRecyclerView();
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void openPDF(String uri) {
        try {
            File file = new File(uri);
            Uri uri1 = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri1, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(context, "Unable to open  pdf file!!", Toast.LENGTH_SHORT).show();
            Log.d("bug", e+"");
        }

    }
}
