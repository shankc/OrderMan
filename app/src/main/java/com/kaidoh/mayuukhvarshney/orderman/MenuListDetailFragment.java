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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;


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
    Float Itemprice;
    String ItemID="";
    public static final String ARG_ITEM_ID = "item_id";
private String ID;
    String ItemName="";
    View rootView;
    int count =0;
    TableRow tr_head;
    TextView label_name,label_intime,label_outtime;
    int sum=0;
  JSONArray ItemArray;
    GridView grid ;
    ProgressWheel prog;
    JSONObject SettleOrder;
    HashMap<String,Float> PriceStorage;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
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
            Log.d("MenuListFrag", "the ID recvied is " + ID);
            Activity activity = this.getActivity();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).build();
            ImageLoader.getInstance().init(config);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.menu_and_order, container, false);
CONTENT= new ArrayList<>();
        ICONS= new ArrayList<>();
       ItemArray= new JSONArray();
        PriceStorage = new HashMap();
        SettleOrder = new JSONObject();

sum=((MenuListListActivity)getActivity()).TotalAmount;
       // grid = (GridView) rootView.findViewById(R.id.grid_view1);
        //prog= (ProgressWheel) rootView.findViewById(R.id.progress_wheel);

        //prog.spin();
        // Show the dummy content as text in a TextView.
        mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);//
        mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);

     new GetMenuCardItems(){
         @Override
        protected void onPostExecute(ArrayList<MenuItems> array){
             super.onPostExecute(array);
             //prog.stopSpinning();
            // prog.setVisibility(View.INVISIBLE);
            // grid.setVisibility(View.VISIBLE);
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

                            // JSONArray array = new JSONArray();
                             JSONObject obj = new JSONObject();

                             try{
                                 float amt =0;
                                  amt = CONTENT.get(position).getPrice()*quantiy;

                                 obj.put("productid", CONTENT.get(position).getId());

                                 obj.put("name", CONTENT.get(position).getItemName());

                                 obj.put("quantity", quantiy);

                                 obj.put("price", CONTENT.get(position).getPrice());

                                 obj.put("amount",amt);
                             }
                             catch(JSONException e ){
                                 e.printStackTrace();
                             }


                             ((MenuListListActivity)getActivity()).orderItems.put(obj); //settle order JSON array

                             ItemArray.put(obj); // place order JSONarray
                             deleteDialog.dismiss();
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     final TableRow tr = new TableRow(getActivity());
                                     tr.setId(100 + count);
                                     tr.setLayoutParams(new TableLayout.LayoutParams(
                                             TableLayout.LayoutParams.WRAP_CONTENT,
                                             TableLayout.LayoutParams.WRAP_CONTENT));

                                   final  TextView names = new TextView(getActivity());
                                     names.setId(200 + count);
                                     names.setText(Integer.toString(quantiy));
                                     names.setPadding(55, 45, 45, 45);
                                     names.setTextColor(Color.BLACK);
                                     tr.addView(names);

                                    final TextView in_time= new TextView(getActivity());
                                     in_time.setId(200 + count);
                                     in_time.setText((ItemName));
                                     in_time.setPadding(55, 45, 45, 45);
                                     in_time.setTextColor(Color.BLACK);
                                     tr.addView(in_time);

                                    final ImageView cancel= new ImageView(getActivity());
                                     cancel.setId(200+count);
                                     cancel.setImageResource(android.R.drawable.ic_delete);
                                     cancel.setPadding(55,35,45,30);
                                     tr.addView(cancel);

                                     cancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             tr.removeView(cancel);
                                             tr.removeView(in_time);
                                             tr.removeView(names);
                                             sum-=(Integer.parseInt(names.getText().toString())*PriceStorage.get(in_time.getText().toString()));
                                             Log.d("MenuListDetailFragment","the new sum => "+sum+" "+in_time.getText().toString());
                                             ((MenuListListActivity)getActivity()).AmountToBePayed.setText(Integer.toString(sum));
                                         }
                                     });

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

        ((MenuListListActivity)getActivity()).SendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();

                String tableid= ((MenuListListActivity)getActivity()).ID;
                try{
                object.put("tableid",tableid);
                  object.put("orderdetails",ItemArray);
                    object.put("Total",sum);
                    if(((MenuListListActivity)getActivity()).BillDetails.size()==0){
                        object.put("orderid",0);
                    }
                    else
                    {
                        object.put("orderid",((MenuListListActivity)getActivity()).BillDetails.get(0).getOrderId());
                    }
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                String postJson= object.toString();
                new SendOrderPost(postJson).execute();
            }
        });

        ((MenuListListActivity)getActivity()).SettleOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fragmentplace "," the button clicked");
                JSONObject object = new JSONObject();
                String tableid= ((MenuListListActivity)getActivity()).ID;
                try{
                    object.put("tableid",tableid);
                    object.put("orderdetails",((MenuListListActivity)getActivity()).orderItems);
                    object.put("Total",sum);
                    if(((MenuListListActivity)getActivity()).BillDetails.size()==0){
                        object.put("orderid",0);
                    }
                    else
                    {
                        object.put("orderid",((MenuListListActivity)getActivity()).BillDetails.get(0).getOrderId());
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }

                String postJSon = object.toString();
                new SettleOrderPost(postJSon).execute();
            }
        });


        return rootView;
    }

    class SendOrderPost extends AsyncTask<Void,Void,Boolean>{

        String json ="";

        protected SendOrderPost(String j){
            this.json=j;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String URL = Config.URL+"order/postorder";
            String content =HttpManager.PostObject(URL, json);
            Log.d("MenuListFragment","the posted respnse "+content);
            Boolean result;
            try {
                JSONObject object = new JSONObject(content);
                result = object.getBoolean("Result");

                return result;
            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return false;
        }
        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            if(result){
                Toast.makeText(getActivity(),"The Order Was Placed Successfully :)",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(),"Error Has Occurred :(",Toast.LENGTH_SHORT).show();
            }
        }
    }


    class SettleOrderPost extends AsyncTask<Void,Void,Boolean>{
        String json ="";
protected  SettleOrderPost(String j){this.json = j;}
        @Override
        protected Boolean doInBackground(Void... params) {

            String URL = Config.URL+"order/postfinalorder";
            String content = HttpManager.PostObject(URL,json);
            Log.d("MenuListFragment", "the posted response " + content);
            Boolean result;
            try {
                JSONObject object = new JSONObject(content);
                result = object.getBoolean("Result");

                return result;
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        return false;
        }
        @Override
            protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            if(result){
                Toast.makeText(getActivity(),"The Order Settled Successfully :)",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(),"Error Has Occurred :(",Toast.LENGTH_SHORT).show();
            }
        }

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
                    //items.setImage(obj.getString("image"));

                    String ImageURL="http://simsapi.perennialcode.com"+obj.getString("imageurl");
                    Log.d("MenuDetailFragment"," the url is => "+ImageURL);
                    items.setImageURL(ImageURL);
                    String temp = obj.getString("productprice");

                    items.setPrice(Float.parseFloat(temp));
                    CONTENT.add(items);
                    //Picasso.with(getActivity()).load(items.getImageURL()).into();
                    PriceStorage.put(items.getItemName(),items.getPrice());
                   // ICONS.add(decodeBase64(obj.getString("image"))); // add a bitmap here !
                    String tem = items.getItemName();

                    /*switch(tem){
                        case "Coffee":
                            ICONS.add(R.mipmap.coffee);
                            break;
                        case "Harbel Tea":
                            ICONS.add(R.mipmap.harbel_tea);
                            break;
                        case "Iced Tea":
                            ICONS.add(R.mipmap.iced_tea);
                            break;
                        case "Juice":
                            ICONS.add(R.mipmap.juice);
                            break;
                        case "Large Drinks":
                            ICONS.add(R.mipmap.large_drinks);
                            break;
                        case "Milk":
                            ICONS.add(R.mipmap.milk);
                            break;
                        case "Soft Drink":
                            ICONS.add(R.mipmap.soft_drink);
                            break;
                        case "Tea":
                            ICONS.add(R.mipmap.tea);
                            break;
                    }*/


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
