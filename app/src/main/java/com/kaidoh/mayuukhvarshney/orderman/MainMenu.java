package com.kaidoh.mayuukhvarshney.orderman;

/**
 * Created by mayuukhvarshney on 01/06/16.
 *
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.GridView;

import java.util.ArrayList;

import static com.kaidoh.mayuukhvarshney.orderman.R.dimen;
import static com.kaidoh.mayuukhvarshney.orderman.R.id;
import static com.kaidoh.mayuukhvarshney.orderman.R.layout;
import static com.kaidoh.mayuukhvarshney.orderman.R.mipmap;
import static com.kaidoh.mayuukhvarshney.orderman.R.string;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> CONTENT;//{"Dosa","Idly","Sambar","Masala Dosa"};
    ArrayList<Integer>ICONS;//{R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image};
    private ImageAdapter adapter;
    GridView menuGrid;
    Dialog dialog;
    protected int mPhotoSize, mPhotoSpacing;
    @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(layout.navigation_main);
        CONTENT= new ArrayList<>();
        ICONS= new ArrayList<>();
        CONTENT.add("TABLE 1");
        CONTENT.add("TABLE 2");
        CONTENT.add("TABLE 3");
        CONTENT.add("TABLE 4");
        CONTENT.add("TABLE 5");

        Color c = new Color();

        for(int i =0;i<CONTENT.size();i++)
        {
            ICONS.add(mipmap.default_green);
        }
        mPhotoSize = getResources().getDimensionPixelSize(dimen.photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(dimen.photo_spacing);
        adapter= new ImageAdapter(this,ICONS,CONTENT);
        menuGrid= (GridView) findViewById(id.grid_view_menu);

        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

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
        });

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


        menuGrid.setAdapter(adapter);
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
