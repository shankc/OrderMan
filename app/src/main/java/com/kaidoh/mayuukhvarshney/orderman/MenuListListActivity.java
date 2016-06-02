package com.kaidoh.mayuukhvarshney.orderman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuListListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ArrayList<MenuCat> MenuList;
    TableLayout table;

    TableRow tr_head;
    TextView label_name,label_intime,label_outtime,AmountToBePayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_navi_drawer);
  MenuList= new ArrayList<>();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle(getTitle());
        AmountToBePayed= (TextView) findViewById(R.id.amount);

new GetMenuCategories(){
    @Override
protected void onPostExecute(ArrayList<MenuCat >array){
        super.onPostExecute(array);
        View recyclerView = findViewById(R.id.menulist_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        if (findViewById(R.id.menulist_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        // insert table view
        table = (TableLayout) findViewById(R.id.main_table);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        tr_head = new TableRow(MenuListListActivity.this);

        tr_head.setId(R.id.worker_table);
        tr_head.setBackgroundColor(Color.parseColor("#4D7AF9"));
        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        label_name = new TextView(MenuListListActivity.this);
        label_name.setId(R.id.worker_name);
        label_name.setText("Quantity");
        label_name.setTextSize(12);
        label_name.setTextColor(Color.BLACK);
        label_name.setPadding(15, 15, 15, 15);
        tr_head.addView(label_name);// add the column to the table row here

        label_intime = new TextView(MenuListListActivity.this);
        label_intime.setId(R.id.worket_out);// define id that must be unique
        label_intime.setText("Item-Name");
        label_intime.setTextSize(12);
        label_intime.setTextColor(Color.BLACK); // set the color
        label_intime.setPadding(15, 15, 5, 15); // set the padding (if required)
        tr_head.addView(label_intime);

        table.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


       /* int count =0;
        for(int i=0;i<10;i++)
        {

            String Name=Integer.toString(i);
            String Intime = Integer.toString(i+10);
            String OutTime=Integer.toString(i + 1000);
// Create the table row
            TableRow tr = new TableRow(MenuListListActivity.this);
            tr.setId(100 + count);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            TextView names = new TextView(MenuListListActivity.this);
            names.setId(200 + count);
            names.setText(Name);
            names.setPadding(55,45,45,45);
            names.setTextColor(Color.BLACK);
            tr.addView(names);

            TextView in_time= new TextView(MenuListListActivity.this);
            in_time.setId(200 + count);
            in_time.setText((Intime));
            in_time.setPadding(55,45,45,45);
            in_time.setTextColor(Color.BLACK);
            tr.addView(in_time);

            table.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            count++;
        }*/
    }
}.execute();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                MenuListListActivity.this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if(drawer!=null)
        {
            drawer.setDrawerListener(toggle);
            toggle.syncState();}
        else
        {
            Log.d("MainMenu", "the drawer has a probelm");
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MenuListListActivity.this);
    }

    class GetMenuCategories extends AsyncTask<Void,Void,ArrayList<MenuCat>>{

        @Override
        protected ArrayList<MenuCat> doInBackground(Void... params) {
            String URL = Config.URL+"menu/GetMenuCategories";

            String content = HttpManager.GetData(URL);
            try{
                JSONObject object = new JSONObject(content);
                JSONArray array = object.getJSONArray("categories");
                Log.d("MenuListActivity","the array => "+array);
                JSONObject obj =null;
                for(int i=0;i<array.length();i++){
                    MenuCat menu = new MenuCat();
                    obj=array.getJSONObject(i);
                    menu.setName(obj.getString("name"));
                    menu.setId(obj.getString("id"));
                    menu.setStatus(obj.getBoolean("status"));
                    MenuList.add(menu);
                }
                return MenuList;

            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(MenuList));
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<MenuCat> mValues;

        public SimpleItemRecyclerViewAdapter(ArrayList<MenuCat> items) {
           this.mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menulist_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position).getName();
          //  holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MenuListDetailFragment.ARG_ITEM_ID, mValues.get(position).getId());
                        MenuListDetailFragment fragment = new MenuListDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.menulist_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MenuListDetailActivity.class);
                        intent.putExtra(MenuListDetailFragment.ARG_ITEM_ID, holder.mItem);

                        context.startActivity(intent);
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
            public final TextView mIdView;
            public final TextView mContentView;
            public String mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
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
        return true;

    }


}
