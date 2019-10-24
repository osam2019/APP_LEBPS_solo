package com.example.lebps.TestWord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.lebps.MainActivity;
import com.example.lebps.R;

import java.util.ArrayList;
import java.util.HashMap;

public class WordTestActivity extends AppCompatActivity {
    public ListView listView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
        listView=(ListView)findViewById(R.id.wordtest_listview);
        //get data
        WordTestAdapter wordTestAdapter=new WordTestAdapter();
        listView.setAdapter(wordTestAdapter);
    }
}
