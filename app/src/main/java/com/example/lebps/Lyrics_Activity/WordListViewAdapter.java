package com.example.lebps.Lyrics_Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lebps.MainActivity;
import com.example.lebps.R;

import java.util.ArrayList;
import java.util.HashMap;

public class WordListViewAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> data=null;
    LayoutInflater inflater=null;
    public WordListViewAdapter(ArrayList<HashMap<String, String>> data){
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        if(view==null){
            final Context context=viewGroup.getContext();
            if(inflater==null){
                inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            view=inflater.inflate(R.layout.listivew_word,viewGroup,false);
        }
        ((TextView)view.findViewById(R.id.listview_word_en)).setText(data.get(i).get("en"));
        ((TextView)view.findViewById(R.id.listview_word_ko)).setText(data.get(i).get("ko"));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.myWordDataList.add(data.get(i));
                MyWordListViewAdapter myWordListViewAdapter=new MyWordListViewAdapter(MainActivity.myWordDataList);
                TrackInfoActivity.myWordListView.setAdapter(myWordListViewAdapter);
                Toast.makeText(viewGroup.getContext(),"단어장에 추가되었습니다",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
