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

public class SummonerSearchAdapter extends RecyclerView.Adapter<SummonerSearchAdapter.SearchResultViewHolder> {
    private List<SummonerClass> mSearchResultsList;
    private OnSearchResultClickListener mResultClickListener;

    public interface OnSearchResultClickListener {
        void onSearchResultClicked(SummonerClass repo);
    }

    public SummonerSearchAdapter(OnSearchResultClickListener listener) {
        mResultClickListener = listener;
    }

    public void updateSearchResults(List<SummonerClass> searchResultsList) {
        mSearchResultsList = searchResultsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSearchResultsList != null) {
            return mSearchResultsList.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_summoner_detail, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.bind(mSearchResultsList.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mSearchResultTV;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.tv_summoner_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mResultClickListener.onSearchResultClicked(
                            mSearchResultsList.get(getAdapterPosition())
                    );
                }
            });
        }

        void bind(SummonerClass repo) {
            mSearchResultTV.setText(repo.name);
        }
    }
}
