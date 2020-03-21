package com.example.searchlol.summoner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchlol.R;
import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.data.SummonerRepo;

import java.util.List;

import static com.example.searchlol.MainActivity.minput;
import static com.example.searchlol.summoner.ChampionDetailActivity.TAG;

public class SavedSummonerAdapter extends RecyclerView.Adapter<SavedSummonerAdapter.SavedItemViewHolder> {
    private List<SummonerRepo> mNameList;
    private OnNameItemClickListener mOnNameItemClickListener;
    private static String mDisplay="";
    private SummonerDetailActivity mAct;

    public interface OnNameItemClickListener {
        void onNameItemClick(SummonerRepo summonerClass);
    }

    public SavedSummonerAdapter(OnNameItemClickListener clickListener) {
        mOnNameItemClickListener = clickListener;
    }

    public void updateLocationList(List<SummonerRepo> nameList) {
        mNameList = nameList;
        notifyDataSetChanged();
    }

    public SummonerRepo getNameAt(int position) {
        return mNameList.get(position);
    }

    @Override
    public SavedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.saved_item, parent, false);
        return new SavedItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SavedItemViewHolder holder, int position) {
        holder.bind(mNameList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mNameList != null) {
            return mNameList.size();
        } else {
            return 0;
        }
    }

    class SavedItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTV;

        public SavedItemViewHolder(View itemView) {
            super(itemView);
            mNameTV = itemView.findViewById(R.id.sl_item);
            itemView.setOnClickListener(this);
        }

        public void bind(SummonerRepo nameClass) {
            String championName = null;
            mAct = new SummonerDetailActivity();
            mDisplay =mAct.parseData();
            Log.d(TAG,"Display: "+ mDisplay);
            mNameTV.setText("Summoner ID: "+ mDisplay);
        }

        @Override
        public void onClick(View v) {
            SummonerRepo name = mNameList.get(getAdapterPosition());
            mOnNameItemClickListener.onNameItemClick(name);
        }
    }
}
