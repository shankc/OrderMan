package com.kaidoh.mayuukhvarshney.orderman;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WaitingListActivity extends ListActivity {

    private ArrayList<WaitingList> waitingLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_list);
        waitingLists = new ArrayList<>();

        new GetWaitingList(){
            @Override
            protected void onPostExecute(ArrayList<WaitingList> lists){
                WaitingListAdapter adapter = new WaitingListAdapter(WaitingListActivity.this,lists);
                setListAdapter(adapter);
            }
        }.execute();

    }

    class GetWaitingList extends AsyncTask<Void,Void,ArrayList<WaitingList>> {

        @Override
        protected ArrayList<WaitingList> doInBackground(Void... params) {
            String URL = Config.URL + "table/getwaitinglist";
            String content = HttpManager.GetData(URL);
            try {
                JSONArray array = new JSONArray(content);
                JSONObject obj = null;
                for(int i = 0;i<array.length();i++){
                    obj = array.getJSONObject(i);
                    WaitingList list = new WaitingList();

                    list.setId(obj.getInt("ID"));
                    list.setName(obj.getString("Name"));
                    list.setNoOfPersons(obj.getInt("NoOFPersons"));
                    waitingLists.add(list);
                }
                return waitingLists;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
