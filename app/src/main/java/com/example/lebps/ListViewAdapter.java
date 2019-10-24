package com.example.lebps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lebps.Lyrics_Activity.TrackInfoActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {
    LayoutInflater inflater=null;
    private ArrayList<HashMap<String,Object>> data_list=null;

    public ListViewAdapter(ArrayList<HashMap<String,Object>> data_list){
        this.data_list=data_list;
    }
    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
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
            convertView=inflater.inflate(R.layout.listview_item,parent,false);
        }
        ImageView imageView=(ImageView)convertView.findViewById(R.id.listView_image);
        TextView nameTextView=(TextView)convertView.findViewById(R.id.listView_title);
        TextView artistTextview=(TextView)convertView.findViewById(R.id.listView_artist);
        //TextView albumTextView=(TextView)convertView.findViewById(R.id.listview_album);
        String name=(String) data_list.get(position).get("name");
        if(name.length()>25){
            name=name.substring(0,24)+"...";
        }
        imageView.setImageBitmap((Bitmap) data_list.get(position).get("image"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");
                intent.putExtra("query",(String) MainActivity.data_list.get(position).get("name"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.getContext().startActivity(intent);

            }
        });
        //독창성, 발전가능성, 완성도, 개발문서 존재
        nameTextView.setText(name);
        artistTextview.setText((String)data_list.get(position).get("artist"));
        //albumTextView.setText((String)data_list.get(position).get("album"));

        //클릭리스너 지정 -> 가사페이지로 이동
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.mainCopyContext, TrackInfoActivity.class);
                intent.putExtra("pos",position);
                MainActivity.mainCopyContext.startActivity(intent);
                Toast.makeText(MainActivity.mainCopyContext,MainActivity.mainCopyContext.getResources().getString(R.string.loading2),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
