package com.example.nitjsrstudenthelper.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nitjsrstudenthelper.R;
import com.example.nitjsrstudenthelper.models.ChildNoteItem;
import com.example.nitjsrstudenthelper.utils.FileDownloader;
import com.example.nitjsrstudenthelper.utils.pathUtil;

import java.io.File;
import java.io.IOException;
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
        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    initiateDownload(holder.downloadBtn, holder.progressBar,
                            childItemList.get(position).getFileName(), childItemList.get(position).getUri());
                }catch (Exception e){
                    Log.d("bug", e+"");
                }
            }
        });
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

    private void initiateDownload(ImageView downloadBtn, ProgressBar progressBar, String fileName, String uri) {
        Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show();
        DownloadFile downloadFile= (DownloadFile) new DownloadFile();
        downloadFile.setDownloadBtn(downloadBtn);
        downloadFile.setProgressBar(progressBar);
        downloadFile.execute(uri, fileName);
        progressBar.setVisibility(View.VISIBLE);
        downloadBtn.setVisibility(View.GONE);

    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        ProgressBar bar;
        ImageView downloadBtn;

        public void setProgressBar(ProgressBar bar) {
            this.bar = bar;
        }

        public void setDownloadBtn(ImageView downloadBtn) {
            this.downloadBtn = downloadBtn;
        }
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];
            pathUtil.createFleFolder();

            String path= pathUtil.rootDir+"/"+fileName+".pdf";
            File pdfFile = new File(path);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                Log.e("bug", e+"");
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if(downloadBtn!=null && bar!=null){
                downloadBtn.setVisibility(View.INVISIBLE);
                bar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(bar!=null){
                bar.setVisibility(View.GONE);
            }
        }
    }

}
