package com.example.searchlol.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchlol.R;
import com.example.searchlol.dataclass.SummonerClass;

import java.util.List;

public class SavedSummonerAdapter extends RecyclerView.Adapter<SavedSummonerAdapter.SavedItemViewHolder> {
    private List<SummonerClass> mNameList;
    private OnNameItemClickListener mOnNameItemClickListener;

    public interface OnNameItemClickListener {
        void onNameItemClick(SummonerClass summonerClass);
    }

    public SavedSummonerAdapter(OnNameItemClickListener clickListener) {
        mOnNameItemClickListener = clickListener;
    }

    public void updateLocationList(List<SummonerClass> nameList) {
        mNameList = nameList;
        notifyDataSetChanged();
    }

    public SummonerClass getNameAt(int position) {
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

        public void bind(SummonerClass nameClass) {
            mNameTV.setText("Summoner ID: " + nameClass.name);
        }

        @Override
        public void onClick(View v) {
            SummonerClass name = mNameList.get(getAdapterPosition());
            mOnNameItemClickListener.onNameItemClick(name);
        }
    }
}
