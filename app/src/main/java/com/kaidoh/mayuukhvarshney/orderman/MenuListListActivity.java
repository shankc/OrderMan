package com.kaidoh.mayuukhvarshney.orderman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
    ArrayList<MenuItems> BillDetails;
    TableLayout table;
 int selected_position=0;
    TableRow tr_head;
    TextView label_name,label_intime,label_outtime,AmountToBePayed;
    FloatingActionButton SendOrder,SettleOrderButton;
    String ID=""; int NumberOfPeople=0;
    Boolean TableFull=false;
    int TotalAmount =0;
    JSONArray orderItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_navi_drawer);
  MenuList= new ArrayList<>();
        BillDetails= new ArrayList<>();
        orderItems = new JSONArray();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle(getTitle());
        AmountToBePayed= (TextView) findViewById(R.id.amount);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

            } else {
                ID = extras.getString("TableId");
               // NumberOfPeople=extras.getInt("Capacity");
                //Log.d("LeaveForm"," the regid is => "+NumberOfPeople);
            }
        } else {
            ID= (String) savedInstanceState.getSerializable("AbsentName");
            NumberOfPeople=(int)savedInstanceState.getSerializable("RegId");
        }

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
        Bundle arguments = new Bundle();
        arguments.putString(MenuListDetailFragment.ARG_ITEM_ID, MenuList.get(0).getId());

        MenuListDetailFragment fragment = new MenuListDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menulist_detail_container, fragment)
                .commit();

        // get bill beforehand if any available.



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
    }
}.execute();
        new GetBill(){
            @Override
            protected  void onPostExecute(ArrayList<MenuItems> array) {
                super.onPostExecute(array);
                int count = 0;
                int sum =0;

                if (array.size() != 0) {

                    JSONObject obj = new JSONObject();
                    for (int i = 0; i < array.size(); i++) {
                        TotalAmount+=(array.get(i).getPrice()*array.get(i).getQuantity());
                        TableRow tr = new TableRow(MenuListListActivity.this);
                        tr.setId(100 + count);
                        tr.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));

                        try{
                            obj.put("productid",BillDetails.get(i).getId());
                            obj.put("name",BillDetails.get(i).getItemName());
                            //obj.put("orderid",BillDetails.get(i).getOrderId());
                            obj.put("price",BillDetails.get(i).getPrice());
                            obj.put("quantity",BillDetails.get(i).getQuantity());
                            obj.put("Amount",BillDetails.get(i).getQuantity()*BillDetails.get(i).getPrice());

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                        TextView names = new TextView(MenuListListActivity.this);
                        names.setId(200 + count);
                        names.setText(Integer.toString(array.get(i).getQuantity()));
                        names.setPadding(55, 45, 45, 45);
                        names.setTextColor(Color.BLACK);
                        tr.addView(names);

                        TextView in_time = new TextView(MenuListListActivity.this);
                        in_time.setId(200 + count);
                        in_time.setText((array.get(i).getItemName()));
                        in_time.setPadding(55, 45, 45, 45);
                        in_time.setTextColor(Color.BLACK);
                        tr.addView(in_time);

                        table.addView(tr, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        count++;

                        orderItems.put(obj);

                    }

                    AmountToBePayed.setText(Integer.toString(TotalAmount));
                }
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

        SettleOrderButton= (FloatingActionButton) findViewById(R.id.settle_order);
        SendOrder = (FloatingActionButton) findViewById(R.id.send_order);

        SendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SettleOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



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

                if(array.length()!=0) {

                    for (int i = 0; i < array.length(); i++) {
                        MenuCat menu = new MenuCat();
                        obj = array.getJSONObject(i);
                        menu.setName(obj.getString("name"));
                        menu.setId(obj.getString("id"));
                        menu.setStatus(obj.getBoolean("status"));
                        MenuList.add(menu);
                    }

                }
                return MenuList;
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    class GetBill extends AsyncTask<Void,Void,ArrayList<MenuItems>>{

        @Override
        protected ArrayList<MenuItems> doInBackground(Void... params) {

            String URL =Config.URL+"order/getordersfortable/"+ID;
            String content = HttpManager.GetData(URL);
            try {
                JSONArray array = new JSONArray(content);
                JSONObject obj =new JSONObject();
                obj = null;
                for(int i=0;i<array.length();i++)
                {
                    MenuItems items = new MenuItems();
                    obj= array.getJSONObject(i);
                    items.setOrderId(obj.getInt("orderid"));
                    items.setItemName(obj.getString("name"));
                    String temp = obj.getString("price");
                    items.setPrice(Float.parseFloat(temp));
                    items.setId(obj.getString("productid"));
                    items.setQuantity(obj.getInt("quantity"));

                    BillDetails.add(items);



                }

                return BillDetails;

            }
            catch (JSONException e){
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

            if(selected_position==position){
                holder.mView.setBackgroundColor(Color.CYAN);
            }
            else {
                holder.mView.setBackgroundColor(Color.TRANSPARENT);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {

                        notifyItemChanged(selected_position);
                        selected_position = position;
                        notifyItemChanged(selected_position);
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

            public final TextView mContentView;
            public String mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
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
        if(item.getItemId()==R.id.nav_home)
        {
            finish();
        }
        else if(item.getItemId()==R.id.nav_waiting)
        {
Intent intent = new Intent(MenuListListActivity.this,WaitingListActivity.class);
            startActivity(intent);
        }
        return true;

    }


}
