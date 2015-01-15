package com.example.hckd;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;


public class Message extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        TextView factLabel = (TextView) findViewById(R.id.textarea);
      //  factLabel.setMovementMethod(ScrollingMovementMethod.getInstance());
       //ScrollView sv=(ScrollView) findViewById(R.id.scrollview);
        //sv.scrollTo(0,  0);
        String email=getIntent().getStringExtra("Email");
        new RetrieveFeedTask(factLabel).execute(email);
        //factLabel.setText("test");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

/*
class RetrieveFeedTask extends AsyncTask<String, Void, Wrapper> {
    TextView fact;

    String name;
   String desp;
    String a;

    int i;
    int j;

    public RetrieveFeedTask(TextView factLabel) {
        this.fact = factLabel;
    }

    //int id_count=0;

    @Override
    protected Wrapper doInBackground(String... email) {
        StringBuilder sb = new StringBuilder();
        StringBuilder namesb = new StringBuilder();
      // StringBuilder despsb=new StringBuilder();

        Wrapper w = new Wrapper();
        if (email != null) {
            try {

                String str1 = "https://www.haveibeenpwned.com/api/v2/breachedaccount/" + email[0];
                */
/*HttpUriRequest request = new HttpGet(str1);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(request);
                String resp = EntityUtils.toString(response.getEntity());*//*

                URL url = new URL(str1);
                SSLContext context = SSLContext.getInstance("Default");

                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setSSLSocketFactory(context.getSocketFactory());
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("response", sb.toString());

                String resp = sb.toString();

                JSONArray marray = new JSONArray(resp);

                if (resp != null) {
                    for (int i = 0; i < marray.length(); i++) {


                        try {


                            JSONObject jObj = marray.getJSONObject(i);
                            name = jObj.getString("Name");
                            desp = jObj.getString("Description");

                            //    Log.d("Id_count", Integer.toString(id_count));
                           // namesb.append(name + "\n" + desp + "\n\n");
                            namesb.append(name + "<br>" + desp + "<br><br>");
                         //   namesb.append(name + "\n");
                           // despsb.append(desp + "\n");
                            Log.d("Name", name);
                           //String a=namesb.toString();
                          //  String b=despsb.toString();
                            //  Log.d("Description", desp);
                           // despsb.append(desp + "\n");
                            Log.d("i", Integer.toString(i));
                            j = i;



                            w.j = j + 1;
                            w.data = namesb;


                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    //  id_count++;
                }




               */
/* try {
                    fact.setText(sb.toString());
                }
                catch (Exception e) {
                }
*//*

                //List messages = new ArrayList();
                //messages=readJsonStream(in);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                Log.d("Your are safe!", "No Breach :) :)");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //    for(int k=0;k>i;i--) {


        // Log.d("Name", name);

        //   w.id_count=id_count;

        //}
        return w;
        // return name;
    }

    protected void onPostExecute(Wrapper w) {
        // TODO: check this.exception
        // TODO: do something with the feed

        if (w.data != null) {
   //  for (int j = 0; j <= i ; j++) {
         // try {

            fact.setText("Oh no! - HCKD " + "\n" + "Hacked on " + w.j +  " site" + "\n\n" + Html.fromHtml(w.data.toString()));


            //  }
            //catch(Exception e){}

     //      }

        }
        else {

            fact.setText("You are safe! No Breach :) :)");
        }
    }

}

*/

  /*  public List readJsonStream(InputStream in) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);

        } finally {
            reader.close();
        }

    }

    public List readMessagesArray(JsonReader reader) throws IOException {
        List messages = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public android.os.Message readMessage(JsonReader reader) throws IOException {
        String text = null;
        String desp = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Name")) {
                text = reader.nextString();
                try {
                    // fact.setText(text);
                }
                catch(Exception e)
                {}
                Log.d("Name", text);
            }
            else if(name.equals("Description")){
                desp=reader.nextString();
                Log.d("Description",desp);
            }

            else {
                reader.skipValue();
            }

        }

        reader.endObject();
        return new android.os.Message();
    }

*/



