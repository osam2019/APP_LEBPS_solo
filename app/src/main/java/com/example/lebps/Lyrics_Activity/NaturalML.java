package com.example.lebps.Lyrics_Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.lebps.MainActivity;
import com.example.lebps.R;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NaturalML {
    public String lyrics;
    public ArrayList<String> verbList, nounList;
    public Document document;
    public CloudNaturalLanguage naturalLanguageService;
    public static int flag=1;
    public ArrayList<HashMap<String,String>> list1,list2;

    @SuppressLint("StaticFieldLeak")
    public NaturalML(String lyrics){
        this.lyrics=lyrics;
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
         document.setContent(lyrics);

        final Features features = new Features();
        features.setExtractEntities(true);
        features.setExtractDocumentSentiment(true);


        new AsyncTask<Object, Void, AnalyzeSyntaxResponse>() {
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
                            if(token.getPartOfSpeech().getTag().equals("VERB")&&token.getText().getContent().length()>2){
                                verbList.add(token.getText().getContent());

                            }
                            if(token.getPartOfSpeech().getTag().equals("NOUN")){
                                nounList.add(token.getText().getContent());
                            }
                        }
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
            protected void onPostExecute(AnalyzeSyntaxResponse response) {
                super.onPostExecute(response);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // YOur Request code here?
                        setWord();
                    }
                }, 1000);
            }
        }.execute();

    }
    /**
     * 단어장 세팅
     */
    public void setWord(){
        try {
            String[] keys={"en","ko"};
            int[] ids={R.id.listview_word_en,R.id.listview_word_ko};
            SimpleAdapter adapter=new SimpleAdapter(TrackInfoActivity.trackInfoContext,
                    list1,R.id.book_verb_listview,keys,ids);
            TrackInfoActivity.verbListView.setAdapter(adapter);

            SimpleAdapter adapter1=new SimpleAdapter(TrackInfoActivity.trackInfoContext,
                    list2,R.id.book_noun_listview,keys,ids);
            TrackInfoActivity.nounListView.setAdapter(adapter1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public String translateLyrics(String originLyrics) throws Exception{
        Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyBD892_HnlgvRvAlq7rUawEncPad85rD1A").build().getService();
        Translation translation=translate.translate(originLyrics,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage("ko"));
        String result=translation.getTranslatedText();
        return result;
    }
}
