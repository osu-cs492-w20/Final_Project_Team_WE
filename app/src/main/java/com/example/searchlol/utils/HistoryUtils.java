package com.example.searchlol.utils;

import android.net.Uri;
import android.util.Log;

import com.example.searchlol.histroy.MatchInfo;
import com.example.searchlol.histroy.MatchReferenceDto;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HistoryUtils {
    private final static String HISTORY_BASE_URL = "https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/";
    private final static String HISTORY_END_INDEX = "endIndex";
    private final static String HISTORY_BEGIN_INDEX = "beginIndex";
    private final static String HISTORY_KEY = "api_key";
    private final static String KEY = "RGAPI-c6dd02e7-edf7-4880-b0ce-3ec9b6b60a15";

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
    }

    static class ParticipantStatsDto
    {
        boolean win;
        int kills;
        int deaths;
        int assists;
        int participantId;
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
        return Uri.parse(HISTORY_BASE_URL).buildUpon()
                .appendPath(id)
                .appendQueryParameter(HISTORY_END_INDEX, "10")
                .appendQueryParameter(HISTORY_BEGIN_INDEX, "0")
                .appendQueryParameter(HISTORY_KEY, KEY)
                .build()
                .toString();
    }

    public static String buildOneMatchURL(long id) {
        return Uri.parse(MATCH_BASE_URL).buildUpon()
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

        if (results != null) {
            return matchInfo;
        } else {
            return null;
        }
    }
}
