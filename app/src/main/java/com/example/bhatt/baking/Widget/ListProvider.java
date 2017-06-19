package com.example.bhatt.baking.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bhatt.baking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by bhatt on 19-06-2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
    private Context context = null;
    private int appWidgetId;

    InputStream inputStream;
    String JSON;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }



    private void populateListItem() {


//        for (int i = 0; i < 10; i++) {
//            ListItem listItem = new ListItem();
//            listItem.heading = "Heading" + i;
//            listItem.content = i + " This is the content of the app widget listview.Nice content though";
//            listItemList.add(listItem);
//        }

        getinputStream();
        try {
            getjsondata();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getinputStream(){
        inputStream = context.getResources().openRawResource(R.raw.bakingappjson);
        try {
            JSON = readFromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder builder = new StringBuilder();

        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader readre = new BufferedReader(inputStreamReader);
            String line = readre.readLine();
            while (line != null){
                builder.append(line);
                line = readre.readLine();
            }
        }
        return builder.toString();
    }

    private void getjsondata() throws JSONException {

        JSONArray jsonarray = new JSONArray(JSON);

        if (jsonarray.length()>0){

            int id = WidgetProviderConfigureActivity.JSONID;

            JSONObject getdata = jsonarray.getJSONObject(id);

            JSONArray ingredients = getdata.getJSONArray("ingredients");

            for (int i=0;ingredients.length()>i;i++){

                ListItem listItem = new ListItem();

                JSONObject currentobj = ingredients.getJSONObject(i);

                String mes = currentobj.getString("measure");
                int qua = currentobj.getInt("quantity");


                listItem.heading = currentobj.getString("ingredient");
                listItem.content = String.valueOf(qua)+mes;

//                    listItem.content = currentobj.getString("measure");

                Log.e("heading",currentobj.getString("ingredient"));
                Log.e("heading",currentobj.getString("measure"));


                listItemList.add(listItem);
            }


        }
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.list_row);
        ListItem listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.heading, listItem.heading);
        remoteView.setTextViewText(R.id.content, listItem.content);

        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

}


