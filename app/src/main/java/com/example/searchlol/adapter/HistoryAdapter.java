package com.example.searchlol.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.searchlol.R;
import com.example.searchlol.dataclass.MatchInfo;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<MatchInfo> mMatchInfo;

    public HistoryAdapter() {
    }

    public void updateMatchinfo(List<MatchInfo> info) {
        mMatchInfo = info;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.match_detail, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(mMatchInfo.get(position));

    }

    @Override
    public int getItemCount() {
        if (mMatchInfo != null) {
            return mMatchInfo.size();
        } else {
            return 0;
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView result;
        private TextView kda;
        private ImageView champIcon;
        private ImageView item1;
        private ImageView item2;
        private ImageView item3;
        private ImageView item4;
        private ImageView item5;
        private ImageView item6;
        private CardView linearLayout;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            result = itemView.findViewById(R.id.match_champ);
            kda = itemView.findViewById(R.id.match_kda);
            champIcon = itemView.findViewById(R.id.iv_match_champ);
            item1 = itemView.findViewById(R.id.iv_item_1);
            item2 = itemView.findViewById(R.id.iv_item_2);
            item3 = itemView.findViewById(R.id.iv_item_3);
            item4 = itemView.findViewById(R.id.iv_item_4);
            item5 = itemView.findViewById(R.id.iv_item_5);
            item6 = itemView.findViewById(R.id.iv_item_6);
            linearLayout = itemView.findViewById(R.id.match_layout);
        }

        public void bind(MatchInfo info) {
            String s, t;
            s = info.win;
            result.setText(String.format("Status: %s", s));
            t = info.kda;
            kda.setText(String.format("K/D/A: %s", t));

            int champId, itemId1, itemId2, itemId3, itemId4, itemId5, itemId6;
            itemId1 = info.item1;
            itemId2 = info.item2;
            itemId3 = info.item3;
            itemId4 = info.item4;
            itemId5 = info.item5;
            itemId6 = info.item6;
            champId = info.champ;
            Log.d("TAG", "bind champ url: " + champId);

            String champIconUrl = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + champId + ".png";
            Glide.with(champIcon.getContext()).load(champIconUrl).into(champIcon);
            Log.d("TAG", "icon URL: " + champIconUrl);

            String itemURL1 = "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/item/" + itemId1 + ".png";
            Glide.with(item1.getContext()).load(itemURL1).into(item1);

            String itemURL2 = "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/item/" + itemId2 + ".png";
            Glide.with(item2.getContext()).load(itemURL2).into(item2);

            String itemURL3 = "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/item/" + itemId3 + ".png";
            Glide.with(item3.getContext()).load(itemURL3).into(item3);

            String itemURL4 = "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/item/" + itemId4 + ".png";
            Glide.with(item4.getContext()).load(itemURL4).into(item4);

            String itemURL5 = "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/item/" + itemId5 + ".png";
            Glide.with(item5.getContext()).load(itemURL5).into(item5);

            String itemURL6 = "https://ddragon.leagueoflegends.com/cdn/10.6.1/img/item/" + itemId6 + ".png";
            Glide.with(item6.getContext()).load(itemURL6).into(item6);

            if (s.equals("Win")) {
                linearLayout.setCardBackgroundColor(Color.argb(255, 53, 113, 242));
            } else {
                linearLayout.setCardBackgroundColor(Color.argb(255, 240, 98, 98));

            }

        }
    }
}
