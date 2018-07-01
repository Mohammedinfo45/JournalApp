package com.example.android.journalapp.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.journalapp.R;
import com.example.android.journalapp.database.model.Diary;

import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;


public class DiariesAdapter extends RecyclerView.Adapter<DiariesAdapter.MyViewHolder> {

    private Context context;
    private List<Diary> diariesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView desc;
//        public TextView note;
//        public TextView image;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            desc = view.findViewById(R.id.desc);
//            note=view.findViewById(R.id.note);
//            image=view.findViewById(R.id.image_diary);
            dot=view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);

        }
    }


    public DiariesAdapter(Context context, List<Diary> notesList) {
        this.context = context;
        this.diariesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dairy_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Diary diary = diariesList.get(position);

        holder.desc.setText(diary.getDesc());
//        holder.note.setText(diary.getDiary());
//        holder.image.setText(diary.getImage());
      //  Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(diary.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return diariesList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-06-29 00:15:42
     * Output: June 29
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}