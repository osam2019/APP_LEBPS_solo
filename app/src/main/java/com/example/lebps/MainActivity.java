package com.example.lebps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lebps.TestWord.WordTestActivity;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    public Button button;
    public EditText nameEditText,artistEditText;
    public static Track track;
    public static ListView listView;
    public static ArrayList<HashMap<String,Object>> data_list;
    public static ListViewAdapter adapter;
    public static List<Track> tracks;
    public static ProgressDialog asyncDialog ;
    public static ArrayList<HashMap<String,String >> myWordDataList;

    public static AsyncTask<String,String,String> asyncTask;
    public static Context mainCopyContext;
    public static Bitmap icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainCopyContext=this;
        button=(Button)findViewById(R.id.main_button);
        nameEditText=(EditText)findViewById(R.id.main_trackName);
        artistEditText=(EditText)findViewById(R.id.main_artistName);
        listView=(ListView)findViewById(R.id.main_listView);
        asyncDialog = new ProgressDialog(this);
        myWordDataList=new ArrayList<HashMap<String, String>>();
        Drawable d = getResources().getDrawable(R.drawable.ic_play_circle_outline_black_24dp);
        icon=drawableToBitmap(d);
        Bitmap resized = null;
        resized = Bitmap.createScaledBitmap(icon, 200, 200, true);
        icon=resized;


        //리스트뷰
        data_list=new ArrayList<HashMap<String,Object>>();
        adapter=new ListViewAdapter(data_list);


    }
    public static TrackData data=null;

    /**
     * 트랙을 검색하여 업데이트
     * 트랙명을 비우고 아티스트만 입력할 시 추천 10개 업로드
     * @param trackname
     * @param artist
     */
    public static String trackname=null;
    public  static String artistname=null;
    public void getData(final String trackname,final  String artist){
        MainActivity.trackname=trackname;
        MainActivity.artistname=artist;
        //memory leak solution
        int mSomeMemberVariable=123;
        // Track Search [ Fuzzy ]
        new getContentAsyncTask(this).execute();
    }
    public static Bitmap converArt =null;
    public void onCilck(View v){
        String name=nameEditText.getText().toString();
        String artist=artistEditText.getText().toString();
        getData(name,artist);

    }
    private static class getContentAsyncTask extends AsyncTask<String, String, String>{
        private WeakReference<MainActivity> activityReference;
        private Context context;
        public getContentAsyncTask(MainActivity context){
            activityReference=new WeakReference<>(context);
            this.context=context.getApplicationContext();
        }
        protected void onPreExecute() {
            super.onPreExecute();
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage(mainCopyContext.getResources().getString(R.string.loading1));
            asyncDialog.show();

        }
        @Override
        protected String doInBackground(String... strings){
            try {
                URL url = new URL("https://source.unsplash.com/300x300/?music,song");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                converArt = BitmapFactory.decodeStream(input);
                converArt=getRoundedCornerBitmap(converArt,50);
                //드랙명이 입력되지 않은경우
                String apiKey = "0e2d22cb7560a3cb586c4537ce1971f7";
                final MusixMatch musixMatch = new MusixMatch(apiKey);
                if(trackname==null||trackname.equals("")){
                    tracks = musixMatch.searchTracks("", MainActivity.artistname, "", 10, 10, true);
                }
                else{
                    track = musixMatch.getMatchingTrack(trackname, artistname);
                    data = track.getTrack();

                }
            } catch (MusixMatchException e) {
                e.printStackTrace();
                return "fail";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "good";
        }
        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            if(!result.equals("fail")){
                if(trackname==null||trackname.equals("")){
                    for (Track trk : tracks){
                        HashMap<String,Object> hashMap=new HashMap<String, Object>();
                        hashMap.put("image", converArt);
                        hashMap.put("name",trk.getTrack().getTrackName());
                        hashMap.put("artist",trk.getTrack().getArtistName());
                        hashMap.put("album",trk.getTrack().getAlbumName());
                        hashMap.put("data",trk.getTrack());
                        data_list.add(hashMap);
                        adapter=new ListViewAdapter(data_list);
                        listView.setAdapter(adapter);
                    }
                }
                else{
                    HashMap<String,Object> hashMap=new HashMap<String, Object>();
                    hashMap.put("image", converArt);
                    hashMap.put("name",data.getTrackName());
                    hashMap.put("artist",data.getArtistName());
                    hashMap.put("album",data.getAlbumName());
                    hashMap.put("data",data);
                    data_list.add(hashMap);
                    adapter=new ListViewAdapter(data_list);
                    listView.setAdapter(adapter);
                }
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // YOur Request code here?
                    if(result.equals("fail")){
                        Toast.makeText(context,mainCopyContext.getResources().getString(R.string.error),Toast.LENGTH_SHORT).show();
                    }
                }
            }, 1000);

            asyncDialog.dismiss();
        }
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        Bitmap newBitmap = Bitmap.createBitmap(output.getWidth(), output.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(newBitmap);
        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(125);
        canvas1.drawBitmap(output, 0, 0, alphaPaint);

        overlay(newBitmap,icon);

        return newBitmap;
    }
    public static void overlay(Bitmap bitmap, Bitmap overlay) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(overlay, bitmap.getWidth()/2-overlay.getWidth()/2, bitmap.getHeight()/2-overlay.getHeight()/2, paint);
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public void wordButtonClick(View v){
        if(data_list.size()<10){
            Toast.makeText(getApplicationContext(),mainCopyContext.getResources().getString(R.string.fillMywordbook),Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(this, WordTestActivity.class);
            startActivity(intent);
        }
    }
}
//git : 소스+readMe파일 발표PPT가아니라 구글드라이브:소개문서, 시연영상 마감기한은 내일까지