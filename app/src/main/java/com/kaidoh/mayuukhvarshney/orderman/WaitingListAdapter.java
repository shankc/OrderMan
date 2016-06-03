package com.kaidoh.mayuukhvarshney.orderman;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramesh on 6/2/2016.
 */
public class WaitingListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<WaitingList> waitingList;

    public WaitingListAdapter(Context context,ArrayList<WaitingList> objects){
        //super(context,resource,objects);

        this.context=context;
        this.waitingList = objects;
    }

    @Override
    public int getCount() {
        return waitingList.size();
    }

    @Override
    public Object getItem(int position) {
        return waitingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.waiting_list,parent,false);

        WaitingList waitlist = waitingList.get(position);

        TextView snotv = (TextView) view.findViewById(R.id.snotextView);
        TextView nametv = (TextView) view.findViewById(R.id.nametextView);
        TextView noptv = (TextView) view.findViewById(R.id.noptextView);

        snotv.setText(waitlist.getId().toString());
        nametv.setText(waitlist.getName());
        noptv.setText(waitlist.getNoOfPersons().toString());

        return view;
    }
}
