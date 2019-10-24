package com.example.lebps.Lyrics_Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lebps.ListViewAdapter;
import com.example.lebps.MainActivity;
import com.example.lebps.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MyWordListViewAdapter extends BaseAdapter{
    LayoutInflater inflater=null;
    public MyWordListViewAdapter(ArrayList<HashMap<String, String>> arrayList) {
        MainActivity.myWordDataList = arrayList;
    }

    @Override
    public int getCount() {
        return MainActivity.myWordDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView==null){
            final Context context=parent.getContext();
            if(inflater==null){
                inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView=inflater.inflate(R.layout.listivew_word,parent,false);
        }
        ((TextView)convertView.findViewById(R.id.listview_word_en)).setText(MainActivity.myWordDataList.get(position).get("en"));
        ((TextView)convertView.findViewById(R.id.listview_word_ko)).setText(MainActivity.myWordDataList.get(position).get("ko"));

        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(parent.getContext())
                        .setMessage("단어를 삭제하시겠습니까?")
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 확인시 처리 로직
                                MainActivity.myWordDataList.remove(position);
                                MyWordListViewAdapter myWordListViewAdapter=new MyWordListViewAdapter(MainActivity.myWordDataList);
                                TrackInfoActivity.myWordListView.setAdapter(myWordListViewAdapter);
                            }})
                        .show();
                return false;
            }
        });
        return convertView;
    }
}
