package com.example.searchlol.utils;

import android.net.Uri;
import android.util.Log;

import com.example.searchlol.histroy.MatchInfo;
import com.example.searchlol.histroy.MatchReferenceDto;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HistoryUtils {
    private final static String HISTORY_1_URL = "https://";
    private final static String HISTORY_2_URL = "api.riotgames.com/lol/match/v4/matchlists/by-account/";
    private final static String HISTORY_END_INDEX = "endIndex";
    private final static String HISTORY_BEGIN_INDEX = "beginIndex";
    private final static String HISTORY_KEY = "api_key";
    private final static String KEY = "RGAPI-cbab7df3-8f08-41ab-8b12-e6a246a09224";
    public static String mREGION="NA1.";
    private final static String MATCH_BASE_URL = "https://na1.api.riotgames.com/lol/match/v4/matches/";

    private static String accId;
    private static int partId;
    static class MatchRepo {
        public ArrayList<MatchReferenceDto> matches;

    }

    static class MatchDto
    {
        TeamStatsDto[] teams;
        ParticipantDto[]  participants;
        ParticipantIdentityDto[] participantIdentities;

    }
    static class TeamStatsDto
    {
        int teamId;
        String win;
    }
    static class ParticipantDto
    {
        ParticipantStatsDto  stats;
        int championId;
    }

    static class ParticipantStatsDto
    {
        boolean win;
        int kills;
        int deaths;
        int assists;
        int participantId;
        int item1;
        int item2;
        int item3;
        int item4;
        int item5;
        int item6;

    }

   static class  ParticipantIdentityDto{
        int participantId;
        PlayerDto player;

   }

   static class PlayerDto{
        String accountId;
   }

    public static String buildHistoryListSearchURL(String id) {
        accId=id;
        return Uri.parse(HISTORY_1_URL+mREGION+HISTORY_2_URL).buildUpon()
                .appendPath(id)
                .appendQueryParameter(HISTORY_END_INDEX, "20")
                .appendQueryParameter(HISTORY_BEGIN_INDEX, "0")
                .appendQueryParameter(HISTORY_KEY, KEY)
                .build()
                .toString();
    }

    public static String buildOneMatchURL(long id) {
        return Uri.parse(HISTORY_1_URL + mREGION + "api.riotgames.com/lol/match/v4/matches/").buildUpon()
                .appendPath(String.valueOf(id))
                .appendQueryParameter(HISTORY_KEY, KEY)
                .build()
                .toString();
    }

    public static ArrayList<MatchReferenceDto> parseHistoryListResults(String json) {
        Gson gson = new Gson();
        MatchRepo results = gson.fromJson(json, MatchRepo.class);
        if (results != null) {
            return results.matches;
        } else {
            return null;
        }
    }

    public static MatchInfo parseOneMatchResults(String json) {
        Gson gson = new Gson();
        MatchDto results = gson.fromJson(json, MatchDto.class);
        MatchInfo matchInfo = new MatchInfo();
        for (ParticipantIdentityDto each: results.participantIdentities){


            if(each.player.accountId.equals(accId)){
                partId=each.participantId;
                Log.d("Utils-id", String.valueOf(partId));
                partId--;
                break;
            }
        }
        if(results.participants[partId].stats.win){
            matchInfo.win="Win";
        }else  {
            matchInfo.win="Loss";
        }
        matchInfo.kda= String.valueOf(results.participants[partId].stats.kills)+"/"+String.valueOf(results.participants[partId].stats.deaths)+"/"+String.valueOf(results.participants[partId].stats.assists);
        Log.d("Utils-kda", matchInfo.kda);

        matchInfo.champ=results.participants[partId].championId;
        matchInfo.item1=results.participants[partId].stats.item1;
        matchInfo.item2=results.participants[partId].stats.item2;
        matchInfo.item3=results.participants[partId].stats.item3;
        matchInfo.item4=results.participants[partId].stats.item4;
        matchInfo.item5=results.participants[partId].stats.item5;
        matchInfo.item6=results.participants[partId].stats.item6;


        if (results != null) {
            return matchInfo;
        } else {
            return null;
        }
    }
}
