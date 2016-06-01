package com.kaidoh.mayuukhvarshney.orderman;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;
import com.kaidoh.mayuukhvarshney.orderman.dummy.DummyContent;
import java.util.ArrayList;
/**
 * A fragment representing a single MenuList detail screen.
 * This fragment is either contained in a {@link MenuListListActivity}
 * in two-pane mode (on tablets) or a {@link MenuListDetailActivity}
 * on handsets.
 */
public class MenuListDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    ArrayList<String>CONTENT;//{"Dosa","Idly","Sambar","Masala Dosa"};
   ArrayList<Integer>ICONS;//{R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image};
    private ImageAdapter adapter;
    GridView menuGrid;
    protected int mPhotoSize, mPhotoSpacing;
    public static final String ARG_ITEM_ID = "item_id";
    TableLayout table;

    TableRow tr_head;
    TextView label_name,label_intime,label_outtime;

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuListDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_and_order, container, false);
CONTENT= new ArrayList<>();
        ICONS= new ArrayList<>();
        CONTENT.add("Idly");
        CONTENT.add("Dosa");
        CONTENT.add("Sambar");
        CONTENT.add("Vada");
        for(int i=0;i<4;i++)
        {
            ICONS.add(R.mipmap.black_image);
        }
        // Show the dummy content as text in a TextView.
        mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);
        adapter= new ImageAdapter(getActivity(),ICONS,CONTENT);
        menuGrid= (GridView) rootView.findViewById(R.id.grid_view1);
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
        table = (TableLayout) rootView.findViewById(R.id.main_table);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        tr_head = new TableRow(getActivity());

        tr_head.setId(R.id.worker_table);
        tr_head.setBackgroundColor(Color.parseColor("#4D7AF9"));
        tr_head.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        label_name = new TextView(getActivity());
        label_name.setId(R.id.worker_name);
        label_name.setText("Quantity");
        label_name.setTextSize(12);
        label_name.setTextColor(Color.BLACK);
        label_name.setPadding(15, 15, 15, 15);
        tr_head.addView(label_name);// add the column to the table row here

        label_intime = new TextView(getActivity());
        label_intime.setId(R.id.worket_out);// define id that must be unique
        label_intime.setText("Item-Name");
         label_intime.setTextSize(12);
        label_intime.setTextColor(Color.BLACK); // set the color
        label_intime.setPadding(15, 15, 5, 15); // set the padding (if required)
        tr_head.addView(label_intime);

        table.addView(tr_head, new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));


        int count =0;
        for(int i=0;i<10;i++)
        {

            String Name=Integer.toString(i);
            String Intime = Integer.toString(i+10);
            String OutTime=Integer.toString(i + 1000);
// Create the table row
            TableRow tr = new TableRow(getActivity());
            tr.setId(100 + count);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

            TextView names = new TextView(getActivity());
            names.setId(200 + count);
            names.setText(Name);
            names.setPadding(55,45,45,45);
            names.setTextColor(Color.BLACK);
            tr.addView(names);

            TextView in_time= new TextView(getActivity());
            in_time.setId(200 + count);
            in_time.setText((Intime));
            in_time.setPadding(55,45,45,45);
            in_time.setTextColor(Color.BLACK);
            tr.addView(in_time);

            table.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            count++;
        }


        //if (mItem != null) {
          //  ((TextView) rootView.findViewById(R.id.menulist_detail)).setText(mItem.details);
        //.
    //}

        return rootView;
    }
}
