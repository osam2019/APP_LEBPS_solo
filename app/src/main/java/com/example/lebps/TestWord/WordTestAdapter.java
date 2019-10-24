package com.example.lebps.TestWord;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lebps.MainActivity;
import com.example.lebps.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class WordTestAdapter extends BaseAdapter {
    LayoutInflater inflater;
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static View convertViewCopy;
    public static int[] ids;
    public static int iter;
    public static Button button;
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        if(convertView==null){
            final Context context=parent.getContext();
            if(inflater==null){
                inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

        }
        Random random=new Random();
        convertView=inflater.inflate(R.layout.mywordtest_activity,parent,false);
        convertViewCopy=convertView;
        ArrayList<Integer> list=getFourNumberList();
        ids=new int[]{R.id.mywordtest_button1,R.id.mywordtest_button2,R.id.mywordtest_button3,R.id.mywordtest_button4};

        int correctNumLoc=random.nextInt(4);
        ((TextView)convertView.findViewById(R.id.mywordtest_textView)).setText(MainActivity.myWordDataList.get(list.get(correctNumLoc)).get("en"));

        for(int i=0;i<4;i++){
            iter=i;
            button=((Button)convertView.findViewById(ids[i]));
            button.setText(MainActivity.myWordDataList.get(list.get(i)).get("ko"));
            if(i==correctNumLoc){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(parent.getContext(),"정답입니다!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(parent.getContext(),"오답입니다!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        return convertView;
    }
    public static ArrayList<Integer> getFourNumberList(){
        ArrayList<Integer> arrayList=new ArrayList<Integer>();
        Random random=new Random();
        int randomInt=random.nextInt(MainActivity.myWordDataList.size());
        arrayList.add(randomInt);
        for(int i=0;i<3;i++){
            while (arrayList.indexOf(randomInt)!=-1){
                randomInt=random.nextInt(MainActivity.myWordDataList.size());
            }
            arrayList.add(randomInt);
        }
        return arrayList;
    }
}
