package com.example.lebps.DictionaryAPI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetMean extends AsyncTask<String,String,String> {
    String en_word=null;
    String clientID="JVDmBuFFTBhp3SwBnyq4",clientSecret="1Bq_7L_ZRN";
    ProgressDialog progressDialog=null;
    boolean useProgress;
    public static String convertedString=null;
    public GetMean(String string, Context context,boolean useProgress){
        en_word=string;
        this.useProgress=useProgress;
        if(useProgress){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("단어를 번역 중입니다.");
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://openapi.naver.com/v1/search/encyc.json?key=JVDmBuFFTBhp3SwBnyq4?query="+en_word);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(useProgress){
            progressDialog.dismiss();
        }
        convertedString=s;
    }
}
