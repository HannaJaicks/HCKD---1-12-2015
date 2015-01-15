package com.example.hckd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;


public class MainActivity extends Activity{

    LinearLayout layoutofpopup;
    PopupWindow popupwindow;
    TextView popuptext;

   private EditText Email;
    //private EditText Password;
    private Button checkmail;
    private TextView heading;
    private TextView subheading;
    private TextView breachoccurrence;
    private TextView tv1;
    String htmltext;
    private  ScrollView scv;
    private Button insidepopupbutton;
    //private Button signup;
    //private static final String TAG = "kinvey ping";
    //String Currentuser;


    private void setupVariables() {

        Email = (EditText) findViewById(R.id.email);

        //Password = (EditText) findViewById(R.id.password);
       // scv=(ScrollView) findViewById(R.id.sv);
        checkmail = (Button) findViewById(R.id.check);

        heading = (TextView) findViewById(R.id.head);
        subheading=(TextView) findViewById(R.id.subhead);
        breachoccurrence=(TextView)   findViewById(R.id.occbreach);

       // tv1= (TextView) findViewById(R.id.tv);
        // signup = (Button) findViewById(R.id.signup);
        //htmltext="<a href=\\\"http:\\/\\/stricture-group.com\\/files\\/adobe-top100.txt\\\" target=\\\"_blank\\\">many were quickly resolved back to plain text<\\/a>";




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupVariables();


        //breachoccurrence.setText("A \"breach\" is an incident where a hacker illegally obtains data from a vulnerable system, usually by exploiting weaknesses in the software. All the data in the site comes from website breaches which have been made publicly available." + "\n\n\n\n\n" + "35" + "\n" + "hacked websites" + "\n\n" + "175,280,108" + "\n" + "hacked accounts");
        //tv1.setText(Html.fromHtml(htmltext).toString());
        // heading.setText("HCKD?");
        //   checkmail.setOnClickListener(new View.OnClickListener()


        checkmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                if (view.getId() == R.id.email)

                {*/
                    String email = Email.getText().toString();
                    Log.e("Hello", email);



                    init();
                    new RetrieveFeedTask(popuptext).execute(email);

                    popupInit();
                    popupwindow.showAsDropDown(checkmail, 0, 0);




            }
        });
        /*popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                popupwindow.dismiss();
                // end may TODO anything else
            }
        });*/


    }
                   /*public void onClick(View view) {

            *//*  if(view.getId() == R.id.email)

                {*//*

                    String email = Email.getText().toString();
                    Log.e("Hello", email);



                init();
                new RetrieveFeedTask(popuptext).execute(email);

                popupInit();
                    popupwindow.showAsDropDown(checkmail, 0, 0);

             *//*  }
                else {
                    popupwindow.setOnDismissListener((PopupWindow.OnDismissListener) insidepopupbutton);
                }*//*







              *//*  Intent intent = new Intent(getApplicationContext(), com.example.hckd.Message.class);
                intent.putExtra("Email", email);

                startActivity(intent);
*//*

            }*/





    public void init(){
        popuptext = new TextView(this);
        insidepopupbutton= new Button(this);
        layoutofpopup=new LinearLayout(this);
        insidepopupbutton.setText("Dismiss");


        popuptext.setPadding(0, 0, 0, 20);
        layoutofpopup.setOrientation(LinearLayout.VERTICAL);
   layoutofpopup.addView(popuptext);
      layoutofpopup.addView(insidepopupbutton);

    }

    public void popupInit() {


        popupwindow = new PopupWindow(layoutofpopup, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupwindow.setContentView(layoutofpopup);


    }
}

class RetrieveFeedTask extends AsyncTask<String, Void, Wrapper> {
    TextView fact;

    String name;
    String desp;
    String a;

    int i;
    int j;

    public RetrieveFeedTask(TextView popuptext) {
        this.fact = popuptext;
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
                /*HttpUriRequest request = new HttpGet(str1);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(request);
                String resp = EntityUtils.toString(response.getEntity());*/
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


                            namesb.append(name + "<br>" + desp + "<br><br>");

                            Log.d("Name", name);

                            Log.d("i", Integer.toString(i));
                            j = i;



                            w.j = j + 1;
                            w.data = namesb;


                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

                }




            } catch (JSONException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                Log.d("Your are safe!", "No Breach :) :)");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }




        return w;

    }

    protected void onPostExecute(Wrapper w) {
        // TODO: check this.exception
        // TODO: do something with the feed

        if (w.data != null) {


            fact.setText("Oh no! - HCKD " + "\n" + "Hacked on " + w.j + " site" + "\n\n" + Html.fromHtml(w.data.toString()));



        }
        else {

            fact.setText("You are safe! No Breach :) :)");
        }
    }

}



/*
class RetrieveFeedTask extends AsyncTask {
    String email;

    public RetrieveFeedTask(String email) {
        this.email = email;
    }

    protected Object doInBackground(Object[] params) {

        if (email != null) {
            try {

                String str1 = "https://www.haveibeenpwned.com/api/v2/breachedaccount/" + email;
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
              */
/*  BufferedReader br;
                br = new BufferedReader(new InputStreamReader(in));*//*

                readJsonStream(in);

                */
/*MainActivity.this.startActivity(new Intent(MainActivity.this,
                        Message.class));
                MainActivity.this.finish();


                Intent intent = new Intent(context, Message.class);

                intent.putExtra("InputStream", in);
                startActivity(intent);*//*

              //  String inputLine;

              */
/*  while ((inputLine = br.readLine()) != null) {
                    Log.d("Response", inputLine);
                    in.close();
                }*//*




             */
/*  JSONArray marray = new JSONArray(inputLine);

               if(inputLine!=null) {
                    for (int i = 0; i < marray.length(); i++) {
                        try {


                            JSONObject jObj = marray.getJSONObject(i);
                            String nm=jObj.getString("Name");

                            Log.d("Reason",nm);

                        }
                        catch(JSONException e)
                        {
                            //e.printStackTrace();
                        }


                    }

                }*//*

               */
/* PushbackInputStream pushbackInputStream = new PushbackInputStream(in);
                int b;
                b = pushbackInputStream.read();
                if ( b == -1 ) {
                    Log.d("Your are safe", "No Breach");
                }
                pushbackInputStream.unread(b);
                return pushbackInputStream; *//*



                // Log.d("Response", resp);
            } catch (FileNotFoundException e) {
                Log.d("Your are safe", "No Breach");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }



        return null;
    }

    public List readJsonStream(InputStream in) throws IOException {

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

    public Message readMessage(JsonReader reader) throws IOException {
        String text = null;
        String desp = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Name")) {
                text = reader.nextString();
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
        return new Message();


    }
}*/
