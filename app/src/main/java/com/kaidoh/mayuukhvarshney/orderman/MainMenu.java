package com.kaidoh.mayuukhvarshney.orderman;

/**
 * Created by mayuukhvarshney on 01/06/16.
 *
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kaidoh.mayuukhvarshney.orderman.R.id;
import static com.kaidoh.mayuukhvarshney.orderman.R.layout;
import static com.kaidoh.mayuukhvarshney.orderman.R.string;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Tables> CONTENT;//{"Dosa","Idly","Sambar","Masala Dosa"};
    ArrayList<Integer>ICONS;
    //{R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image};
    private ImageAdapter adapter;
    GridView menuGrid;
    Dialog dialog;
    EditText Capicity;
    String quantity_text="";
    protected int mPhotoSize, mPhotoSpacing;
    @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(layout.navigation_main);
        menuGrid= (GridView) findViewById(id.grid_view_menu);

        mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);

        CONTENT= new ArrayList<>();
        ICONS= new ArrayList<>();
        new GetTables(){
            @Override
            protected void onPostExecute(ArrayList<Tables> content){
                super.onPostExecute(content);
                adapter= new ImageAdapter(MainMenu.this,ICONS,CONTENT);
                menuGrid.setAdapter(adapter);



                menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        if (CONTENT.get(position).getStatus()) {
                            Tables table = new Tables();
                            table = CONTENT.get(position);
                            Intent intent = new Intent(MainMenu.this, MenuListListActivity.class);
                            intent.putExtra("TableId", table.getId());
                            //intent.putExtra("TableCapacity", quantity_text);
                            startActivity(intent);
                        }

                        else {
                            LayoutInflater factory = LayoutInflater.from(MainMenu.this);
                            final View deleteDialogView = factory.inflate(
                                    layout.quantity_dialog, null);
                            final AlertDialog deleteDialog = new AlertDialog.Builder(MainMenu.this).create();
                            deleteDialog.setView(deleteDialogView);
                            deleteDialog.setTitle("Number of Persons?");
                            Capicity = (EditText) deleteDialogView.findViewById(R.id.qnt_text);

                            deleteDialogView.findViewById(R.id.dialogButtonOK).setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    //your business logic
                                    quantity_text = Capicity.getText().toString();
                                    deleteDialog.dismiss();
                                    Tables table = new Tables();
                                    table = CONTENT.get(position);
                                    Intent intent = new Intent(MainMenu.this, MenuListListActivity.class);
                                    intent.putExtra("TableId", table.getId());
                                    intent.putExtra("TableCapacity", quantity_text);
                                    startActivity(intent);
                                }
                            });
                            deleteDialog.show();

                        }
                    }
                });
               // menuGrid.setAdapter(adapter);
                menuGrid.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (adapter.getNumColumns() == 0) {
                            final int numColumns = (int) Math.floor(menuGrid.getWidth() / (mPhotoSize + mPhotoSpacing));
                            if (numColumns > 0) {
                                final int columnWidth = (menuGrid.getWidth() / numColumns);
                                adapter.setNumColumns(numColumns);
                                adapter.setItemHeight(columnWidth);


                            }
                        }
                    }
                });
            }

        }.execute();





      /*  menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    LayoutInflater factory = LayoutInflater.from(MainMenu.this);
                    final View deleteDialogView = factory.inflate(
                            layout.quantity_dialog, null);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(MainMenu.this).create();
                    deleteDialog.setView(deleteDialogView);
                    deleteDialog.setTitle("Number of Persons?");
                    deleteDialogView.findViewById(R.id.dialogButtonOK).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            //your business logic
                            deleteDialog.dismiss();
                            Intent intent = new Intent(MainMenu.this, MenuListListActivity.class);
                            startActivity(intent);
                        }
                    });
                    deleteDialog.show();
                }
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                MainMenu.this, drawer, null, string.navigation_drawer_open, string.navigation_drawer_close);


       if(drawer!=null)
       {
        drawer.setDrawerListener(toggle);
        toggle.syncState();}
        else
        {
            Log.d("MainMenu","the drawer has a probelm");
        }

        NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainMenu.this);


       /* menuGrid.setAdapter(adapter);
        menuGrid.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (adapter.getNumColumns() == 0) {
                    final int numColumns = (int) Math.floor(menuGrid.getWidth() / (mPhotoSize + mPhotoSpacing));
                    if (numColumns > 0) {
                        final int columnWidth = (menuGrid.getWidth() / numColumns);
                        adapter.setNumColumns(numColumns);
                        adapter.setItemHeight(columnWidth);


                    }
                }
            }
        });*/
    }
    class GetTables extends AsyncTask<Void,Void,ArrayList<Tables>>{

        @Override
        protected ArrayList<Tables>doInBackground(Void... params) {

            String URL = Config.URL+"Table/GetTables";
            String content = HttpManager.GetData(URL);
            Log.d("MainMEnu", "the content is " + content);
            try {
                JSONObject object = new JSONObject(content);
                JSONArray array = object.getJSONArray("Tables");
                JSONObject o = null;
                for(int i=0;i<array.length();i++){
                    Tables table = new Tables();
                    o =array.getJSONObject(i);

                    table.setCapacity(o.getString("capacity"));
                    table.setId(o.getString("id"));
                    table.setName(o.getString("name"));
                    table.setStatus(o.getBoolean("status"));
                    SharedPreferences datastore= getSharedPreferences("DataStorage", 0);
                    SharedPreferences.Editor editor=datastore.edit();
                    CONTENT.add(table);
                    if(table.getStatus()){
                        ICONS.add(R.mipmap.red_image);
                    }
                    else
                    {
                        ICONS.add(R.mipmap.default_green);
                    }
                  //  ICONS.add(R.mipmap.default_green);
                    Log.d("MainMenu","the array is "+CONTENT.get(i));


                }
                return CONTENT;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return CONTENT;
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
        if(item.getItemId()== id.nav_waiting){
            Intent intent = new Intent(this,WaitingListActivity.class);
            startActivity(intent );
        }
        return true;

    }
}
