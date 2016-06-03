package com.kaidoh.mayuukhvarshney.orderman;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<MenuItems>CONTENT;//{"Dosa","Idly","Sambar","Masala Dosa"};
   ArrayList<Bitmap>ICONS;//{R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image,R.mipmap.black_image};
    private ItemsAdapter adapter;
    GridView menuGrid;
    protected int mPhotoSize, mPhotoSpacing;
    int quantiy=0;
   EditText quantity_entered;
    public static final String ARG_ITEM_ID = "item_id";
private String ID;
    String ItemName="";
    View rootView;
    int count =0;
    TableRow tr_head;
    TextView label_name,label_intime,label_outtime;
    int sum=0;
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
          //  mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Bundle bundle = MenuListDetailFragment.this.getArguments();
            ID=bundle.getString(ARG_ITEM_ID);
            Log.d("MenuListFrag","the ID recvied is "+ID);
            Activity activity = this.getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.menu_and_order, container, false);
CONTENT= new ArrayList<>();
        ICONS= new ArrayList<>();

sum=0;
        // Show the dummy content as text in a TextView.
        mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);//
        mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);
     new GetMenuCardItems(){
         @Override
        protected void onPostExecute(ArrayList<MenuItems> array){
             super.onPostExecute(array);

             adapter= new ItemsAdapter(getActivity(),ICONS,CONTENT);
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

             menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                     LayoutInflater factory = LayoutInflater.from(getActivity());
                     final View deleteDialogView = factory.inflate(
                             R.layout.quantity_dialog, null);
                     final AlertDialog deleteDialog = new AlertDialog.Builder(getActivity()).create();
                     deleteDialog.setView(deleteDialogView);
                     deleteDialog.setTitle("Enter the Quantity?");
                     quantity_entered= (EditText) deleteDialogView.findViewById(R.id.qnt_text);
                     deleteDialogView.findViewById(R.id.dialogButtonOK).setOnClickListener(new View.OnClickListener() {

                         @Override
                         public void onClick(View v) {
                             //your business logic
                             String temp = quantity_entered.getText().toString();
                             Log.d("MenuListFragment"," the quanitty tentered"+temp);
                             quantiy=Integer.parseInt(temp);
                             ItemName=CONTENT.get(position).getItemName();
                             sum+=(quantiy*CONTENT.get(position).getPrice());
                             deleteDialog.dismiss();
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     TableRow tr = new TableRow(getActivity());
                                     tr.setId(100 + count);
                                     tr.setLayoutParams(new TableLayout.LayoutParams(
                                             TableLayout.LayoutParams.WRAP_CONTENT,
                                             TableLayout.LayoutParams.WRAP_CONTENT));

                                     TextView names = new TextView(getActivity());
                                     names.setId(200 + count);
                                     names.setText(Integer.toString(quantiy));
                                     names.setPadding(55, 45, 45, 45);
                                     names.setTextColor(Color.BLACK);
                                     tr.addView(names);

                                     TextView in_time= new TextView(getActivity());
                                     in_time.setId(200 + count);
                                     in_time.setText((ItemName));
                                     in_time.setPadding(55, 45, 45, 45);
                                     in_time.setTextColor(Color.BLACK);
                                     tr.addView(in_time);

                                     ((MenuListListActivity)getActivity()).table.addView(tr, new TableLayout.LayoutParams(
                                             TableLayout.LayoutParams.WRAP_CONTENT,
                                             TableLayout.LayoutParams.WRAP_CONTENT));
                                     count++;
                                     ((MenuListListActivity)getActivity()).AmountToBePayed.setText(Integer.toString(sum));
                                 }
                             });

                         }
                     });
                     deleteDialog.show();
                 }
             });


         }
     }.execute();

        //if (mItem != null) {
          //  ((TextView) rootView.findViewById(R.id.menulist_detail)).setText(mItem.details);
        //.
    //}

        return rootView;
    }

    class GetMenuCardItems extends AsyncTask<Void,Void,ArrayList<MenuItems>>{

        @Override
        protected ArrayList<MenuItems> doInBackground(Void... params) {
            String URL = Config.URL+"menu/GetMenuItems/"+ID;
            String content = HttpManager.GetData(URL);
            try{
                JSONObject object = new JSONObject(content);
                JSONArray array = object.getJSONArray("products");
                JSONObject obj=null;
                for(int i=0;i<array.length();i++){
                    MenuItems items = new MenuItems();
                    obj=array.getJSONObject(i);
                    items.setId(obj.getString("productsku"));
                    items.setCategoryId(obj.getString("categoryid"));
                    items.setItemName(obj.getString("productname"));
                    items.setItemDescription(obj.getString("productdesc"));
                    items.setImage(obj.getString("image"));
                    String temp = obj.getString("productprice");
                    items.setPrice(Float.parseFloat(temp));
                    Log.d("MenuListDetailFRagment"," the float is "+temp);
                    CONTENT.add(items);
                    ICONS.add(decodeBase64(obj.getString("image"))); // add a bitmap here !
                }
                return CONTENT;

            }
            catch(JSONException e){
                e.printStackTrace();
                return null;
            }

        }
    }


    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

    }
}
