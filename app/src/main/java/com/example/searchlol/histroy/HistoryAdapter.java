package com.example.searchlol.histroy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchlol.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private List<MatchInfo> mMatchinfo;

    public HistoryAdapter(){
    }

    public void updateMatchinfo(List<MatchInfo> info){
        mMatchinfo=info;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.match_detail, parent, false);
        return new HistoryViewHolder(itemView);      }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(mMatchinfo.get(position));

    }

    @Override
    public int getItemCount() {
        if (mMatchinfo != null) {
            return mMatchinfo.size();
        } else {
            return 0;
        }    }

    class HistoryViewHolder  extends RecyclerView.ViewHolder  {

        private TextView champ;
        private TextView kda;

        public HistoryViewHolder(View itemView){
            super(itemView);
            champ =itemView.findViewById(R.id.match_champ);
            kda =itemView.findViewById(R.id.match_kda);
        }

        public void bind(MatchInfo info){
            String s,t;
            s= info.win ;
            champ.setText(s);
            t=info.kda;
            kda.setText(t);
        }
    }
}
