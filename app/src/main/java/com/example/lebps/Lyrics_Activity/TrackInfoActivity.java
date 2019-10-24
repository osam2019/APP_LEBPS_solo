package com.example.lebps.Lyrics_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lebps.MainActivity;
import com.example.lebps.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1.model.AnalyzeSyntaxRequest;
import com.google.api.services.language.v1.model.AnalyzeSyntaxResponse;
import com.google.api.services.language.v1.model.Document;
import com.google.api.services.language.v1.model.Features;
import com.google.api.services.language.v1.model.Token;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.track.TrackData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TrackInfoActivity extends AppCompatActivity {
    public ImageView imageView;
    public TextView nameTextview,artistTextView;
    public String originLyrics;
    protected ProgressDialog asyncDialog ;
    public NaturalML naturalML;
    public static Context trackInfoContext;
    public static ListView verbListView,nounListView;
    public static ListView myWordListView;

    /**
     * NM variable
     * @param savedInstanceState
     */
    public String lyrics;
    public ArrayList<String> verbList, nounList;
    public Document document;
    public CloudNaturalLanguage naturalLanguageService;
    public static int flag=1;
    public ArrayList<HashMap<String,String>> list1,list2;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_info);
        /**
         * 기본 데이터 로드
         */

        imageView=(ImageView)findViewById(R.id.TrackInfo_image);
        nameTextview=(TextView)findViewById(R.id.TrackInfo_trackName);
        artistTextView=(TextView)findViewById(R.id.TrackInfo_artistName);
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("단어장을 생성중입니다....");
        asyncDialog.show();
        trackInfoContext=this;
        verbListView=(ListView)findViewById(R.id.book_verb_listview);
        nounListView=(ListView)findViewById(R.id.book_noun_listview);
        myWordListView=(ListView)findViewById(R.id.myWordListView);

        Intent intent=new Intent(this.getIntent());
        final int position=intent.getIntExtra("pos",0);
        imageView.setImageBitmap((Bitmap)MainActivity.data_list.get(position).get("image"));
        String name=(String) MainActivity.data_list.get(position).get("name");
        if(name.length()>25){
            name=name.substring(0,24)+"...";
        }
        nameTextview.setText(name);
        artistTextView.setText((String)MainActivity.data_list.get(position).get("artist"));
        /**
         * 유튜브 인텐트 설정
         */
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");
                intent.putExtra("query",(String) MainActivity.data_list.get(position).get("name"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        /**
         * 탭 호스트 설정
         */
        setFirstTabHost();

        /**
         * 콘텐츠 로딩 초기 설정
         */
        final TextView lyricsTextView=(TextView)findViewById(R.id.trackInfo_lyrics_textView);
        final TrackData trackData=(TrackData)MainActivity.data_list.get(position).get("data");
        String apiKey = "0e2d22cb7560a3cb586c4537ce1971f7";
        final MusixMatch musixMatch = new MusixMatch(apiKey);
        /**
         * 나만의 단어장 데이터 로드
         */
        MyWordListViewAdapter myWordListViewAdapter=new MyWordListViewAdapter(MainActivity.myWordDataList);
        myWordListView.setAdapter(myWordListViewAdapter);
        /**
         * 콘텐츠 로딩
         */
        final AsyncTask<String,String,String> asyncTask=new AsyncTask<String, String, String>() {
            protected void onPreExecute() {
                super.onPreExecute();
                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage(MainActivity.mainCopyContext.getResources().getString(R.string.loading1));
                asyncDialog.show();
            }
            @Override
            protected String doInBackground(String... strings){
                try {
                    //원본 가사 설정
                    Lyrics lyrics=musixMatch.getLyrics(trackData.getTrackId());
                    lyricsTextView.setText(lyrics.getLyricsBody());
                    originLyrics=lyrics.getLyricsBody();
                    String[] list=originLyrics.split("\n");
                    String result="";

                    for(String word : list){
                        result=result+"\n"+translateLyrics(word);
                    }
                    /**
                     * 가사 번역 저장
                     */
                    ((TextView)findViewById(R.id.trackInfo_translation_textView)).setText(result);

                } catch (MusixMatchException e) {
                    e.printStackTrace();
                    return "fail";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "good";
            }
            @Override
            protected void onPostExecute(final String result) {
                super.onPostExecute(result);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // YOur Request code here?
                        asyncDialog.dismiss();
                        if(result.equals("fail")){
                            Toast.makeText(getApplicationContext(),"가사 로딩 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1000);

            }
        };
        try {
            asyncTask.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 자연어 처리
         */
        verbList=new ArrayList<String>();
        nounList =new ArrayList<String>();

        naturalLanguageService =new CloudNaturalLanguage.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null
        ).setCloudNaturalLanguageRequestInitializer(
                new CloudNaturalLanguageRequestInitializer("AIzaSyBD892_HnlgvRvAlq7rUawEncPad85rD1A")
        ).build();

        document=new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("en");
        document.setContent(originLyrics);


        AsyncTask<Object, Void, AnalyzeSyntaxResponse> asyncTask1=new AsyncTask<Object, Void, AnalyzeSyntaxResponse>() {
            private WeakReference<TrackInfoActivity> activityWeakReference;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected AnalyzeSyntaxResponse doInBackground(Object... params) {
                AnalyzeSyntaxResponse response = null;
                try {
                    AnalyzeSyntaxRequest request=new AnalyzeSyntaxRequest();
                    request.setDocument(document);
                    response = naturalLanguageService.documents().analyzeSyntax(request).execute();
                    /**
                     * 단어 정렬
                     */
                    if (response != null) {
                        for(Token token : response.getTokens()){
                            if(token.getPartOfSpeech().getTag().equals("VERB")&&token.getText().getContent().length()>3){
                                verbList.add(token.getText().getContent());

                            }
                            if(token.getPartOfSpeech().getTag().equals("NOUN")&&token.getText().getContent().length()>3){
                                nounList.add(token.getText().getContent());
                            }
                        }
                        //중복제거
                        HashSet<String> hashSet=new HashSet<String>(verbList);
                        verbList=new ArrayList<String>(hashSet);

                        HashSet<String> hashSet1=new HashSet<String>(nounList);
                        nounList=new ArrayList<String>(hashSet1);
                    }
                    /**
                     * 단어 번역 및 저장
                     */
                    list1=new ArrayList<HashMap<String, String>>();
                    for(int i=0;i<verbList.size();i++){
                        HashMap<String,String> hashMap1=new HashMap<String, String>();
                        hashMap1.put("en",verbList.get(i));
                        hashMap1.put("ko",translateLyrics(verbList.get(i)));
                        list1.add(hashMap1);
                    }
                    list2=new ArrayList<HashMap<String, String>>();
                    for(int i=0;i<nounList.size();i++){
                        HashMap<String,String> hashMap1=new HashMap<String, String>();
                        hashMap1.put("en",nounList.get(i));
                        hashMap1.put("ko",translateLyrics(nounList.get(i)));
                        list2.add(hashMap1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            }
            @Override
            protected void onPostExecute(final AnalyzeSyntaxResponse response) {
                super.onPostExecute(response);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // YOur Request code here?
                        setWord();
                        /**
                         * Flexbox 설정
                         */
                        FlexboxLayout flexboxLayout=(FlexboxLayout)findViewById(R.id.flexbox);
                        LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        for(Token token : response.getTokens()){
                            View item=inflater.inflate(R.layout.flexbox_item,null);
                            ((TextView)item.findViewById(R.id.flexbox_item_en)).setText(token.getText().getContent());
                            ((TextView)item.findViewById(R.id.flexbox_item_syntax)).setText(token.getPartOfSpeech().getTag());
                            flexboxLayout.addView(item);

                        }
                    }
                }, 1000);
            }
        };
        try {
            asyncTask1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String result=null;

    /**
     * 가사 번역 후 텍스트뷰에 세팅
     * @param originLyrics
     * @return
     * @throws Exception
     */
    public String translateLyrics(String originLyrics) throws Exception{
        Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyBD892_HnlgvRvAlq7rUawEncPad85rD1A").build().getService();
        Translation translation=translate.translate(originLyrics,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage(Locale.getDefault().getLanguage()));
        String result=translation.getTranslatedText();
        return result;
    }
    public void setFirstTabHost(){
        // "Tab Spec" 태그(Tag)를 가진 TabSpec 객체 생성.
        TabHost tabHost=(TabHost)findViewById(R.id.tabHostt);
        TabHost.TabSpec ts = tabHost.newTabSpec("Tab Spec") ;

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHostt) ;
        tabHost1.setup() ;

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.content1) ;
        ts1.setIndicator(MainActivity.mainCopyContext.getResources().getString(R.string.song)) ;
        tabHost1.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.content2) ;
        ts2.setIndicator(MainActivity.mainCopyContext.getResources().getString(R.string.translate)) ;
        tabHost1.addTab(ts2) ;

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.content3) ;
        ts3.setIndicator(MainActivity.mainCopyContext.getResources().getString(R.string.wordbook)) ;
        tabHost1.addTab(ts3) ;

        // 네 번째 TAB
        TabHost.TabSpec ts5=tabHost1.newTabSpec("Tab Spec 5");
        ts5.setContent(R.id.content5);
        ts5.setIndicator(MainActivity.mainCopyContext.getResources().getString(R.string.Syntax));
        tabHost1.addTab(ts5);

        // 다섯 번째 TAB
        TabHost.TabSpec ts4=tabHost1.newTabSpec("Tab Spec 4");
        ts4.setContent(R.id.myWordListView);
        ts4.setIndicator(MainActivity.mainCopyContext.getResources().getString(R.string.myworldbook));
        tabHost1.addTab(ts4);
    }
    public void setWord(){
        String[] keys={"en","ko"};
        int[] ids={R.id.listview_word_en,R.id.listview_word_ko};
        WordListViewAdapter wordListViewAdapter=new WordListViewAdapter(list1);
        verbListView.setAdapter(wordListViewAdapter);
        verbListView.smoothScrollBy(0,0);

        WordListViewAdapter wordListViewAdapter1=new WordListViewAdapter(list2);
        nounListView.setAdapter(wordListViewAdapter1);
        nounListView.smoothScrollBy(0,0);
        asyncDialog.dismiss();
    }
}
