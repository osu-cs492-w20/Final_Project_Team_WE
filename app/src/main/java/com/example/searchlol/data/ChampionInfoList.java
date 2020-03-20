package com.example.searchlol.data;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class ChampionInfoList implements Serializable {
    public List<ChampionInfo> data;

    public class ChampionInfo implements Serializable {
        public String id;
        public int key;
    }
}
