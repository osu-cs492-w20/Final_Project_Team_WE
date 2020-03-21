package com.example.searchlol.dataclass;

import java.io.Serializable;

public class ChampionMasteryClass implements Serializable {
    public int championLevel;
    public boolean chestGranted;
    public int championPoints;
    public int championPointsSinceLastLevel;
    public int championPointsUntilNextLevel;
    public int championId;
    public long lastPlayTime;
}
