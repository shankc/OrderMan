package com.kaidoh.mayuukhvarshney.orderman;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mayuukhvarshney on 03/06/16.
 */
public class WaitingListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    ArrayList<Waiting> WaitingList;
    MyAdapter adapter;
    RecyclerView rec;
    ProgressWheel prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_list_nav);
WaitingList = new ArrayList<>();
        prog = (ProgressWheel) findViewById(R.id.progress_wheel);
        rec= (RecyclerView) findViewById(R.id.waiting_list);
        prog.spin();
        rec.setVisibility(View.INVISIBLE);
        new GetWaitingList(){
            @Override
        protected void onPostExecute(ArrayList<Waiting>array){
                super.onPostExecute(array);
                prog.stopSpinning();
                prog.setVisibility(View.INVISIBLE);
                rec.setVisibility(View.VISIBLE);
                View recyclerView = findViewById(R.id.waiting_list);
                assert recyclerView != null;
                setupRecyclerView((RecyclerView) recyclerView);
            }
        }.execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                WaitingListActivity.this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);



        if (drawer != null) {
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        } else {
            Log.d("MainMenu", "the drawer has a probelm");
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(WaitingListActivity.this);


    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new MyAdapter(WaitingList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    public class MyAdapter
            extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final ArrayList<Waiting> mValues;

        public MyAdapter(ArrayList<Waiting> items) {
            this.mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.waiting_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position).getName();
            //  holder.mIdView.setText(mValues.get(position).id);

            Log.d("Waitinglist", " the number of =>" + mValues.get(position).getNoPersons());
            String temp = Integer.toString(mValues.get(position).getNoPersons());
            holder.mContentView.setText(temp);
            holder.mNumber.setText(mValues.get(position).getName());


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  {

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView mContentView,mNumber;

            public String mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.waiting_names);
                mNumber= (TextView) view.findViewById(R.id.waiting_numbers);

            }


        }
    }

    class GetWaitingList extends AsyncTask<Void,Void,ArrayList<Waiting>>{

        @Override
        protected ArrayList<Waiting> doInBackground(Void... params) {
            String URL = Config.URL+"table/getwaitinglist";
            String content = HttpManager.GetData(URL);
            try
            {
                JSONArray array = new JSONArray(content);
                JSONObject obj= null;
                for(int i=0;i<array.length();i++){
                    obj= array.getJSONObject(i);
                    Waiting wait = new Waiting();
                    wait.setId(obj.getInt("ID"));
                    wait.setName(obj.getString("Name"));
                    wait.setNoPersons(obj.getInt("NoOFPersons"));

                    WaitingList.add(wait);
                }
                return WaitingList;

            }
            catch(JSONException e){
                e.printStackTrace();
                return null;
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(item.getItemId()==R.id.nav_home){
            Intent intent = new Intent(this, MainMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;

    }
}

