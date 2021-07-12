package com.bitpolarity.spotifytestapp;

import android.content.SharedPreferences;
import android.util.Log;

import com.bitpolarity.spotifytestapp.DB_Related.fbase_bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DB_Handler {

    //////////////////////////////== INIT ==//////////////////////////////////////////////

    String username;
    int status ;
    Date date;
    String time ;
    SimpleDateFormat formatter;
    final String LOG = "db_handler";
    SharedPreferences prefs;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    final String ROOT = "Users";
    DatabaseReference ROOTPATH;

    ////////////////////////////////== INIT ==////////////////////////////////////////////////////////////////////////

    public DB_Handler(){
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
    }


    /////////////////////////////////////////-------BASIC IO--------/////////////////////////////////


    String getTime(Date date){


        String datetime = formatter.format(date);
        return datetime;

    }
    public void setStatus(int status){
        //fbase_bundle bundle = new fbase_bundle(status);
        date = new Date();
        time = getTime(date);

        ref.child(ROOT).child(username).child("STATUS").setValue(status);
        ref.child(ROOT).child(username).child("LA").setValue(time);

        if (status ==1)
        {
            Log.v(LOG, "User Status: Online");
            Log.v(LOG,"DB_STATUS > Session initiated at "+time);
        }
        else{
         Log.v(LOG, "User Status: Offline");
         Log.v(LOG,"DB_STATUS > Session ended at "+time);
        }





    }

   public void setUsername(String username){
        this.username = username;
       ROOTPATH =ref.child(ROOT).child(username);

   }

   String getUsername(){
        return username;

   }

    /////////////////////////////////////////-------BASIC IO--------/////////////////////////////////





    /////////////////////////////////////////------SONG DETAIL SECTION---------/////////////////////////////////


     void setSong_Details(String trackID,String posterURL, String artistName, String albumName, String trackName, String trackLength ){

         if (artistName == null) artistName = "Loading";
         else if (albumName==null) albumName = "Loading";
         else if (trackName==null) trackName = "Loading";
         else if (trackLength == null) trackLength = "0";

         fbase_bundle bundle = new fbase_bundle(trackID,posterURL,artistName.replace(",",""),albumName.replace(",",""),trackName.replace(",",""),trackLength);
         ROOTPATH.child("SD").setValue(bundle);

     }

     void setSong_PlaybackDetails(boolean isplaying){
        if (isplaying) ROOTPATH.child("isPlaying").setValue(isplaying);
        else ROOTPATH.child("isPlaying").setValue(isplaying);

     }



}
