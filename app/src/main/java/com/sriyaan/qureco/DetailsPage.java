package com.sriyaan.qureco;

import android.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.sriyaan.util.url_dump.isTimeBetweenTwoTime;

public class DetailsPage extends AppCompatActivity {

    String user_id, hcp_id;
    SharedPreferences prefs;
    Context context;
    ImageView topimage;
    TextView tvname, tvaddress, tvreviews_count, tvlikes, tvopennow, tvopentimings, tvcall, tvchat, tvfollow, tvdeals, tvamenities, tvspecialities, tvservices, tvavailability, tvlocation, tvabout, tvequipments, tvnavigate,tvsocial;

    String name, address, reviews, likes, opentimings;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    ImageView back;
    String cat_id;
    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
    LinearLayout deals,amenities,specialities,services,availability,location,about,equipments,social,writereview,follow;
    ImageView one,two,three,four,five;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        context = DetailsPage.this;
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        user_id = prefs.getString("cust_id", "");
        cat_id = getIntent().getStringExtra("value");
        hcp_id = getIntent().getStringExtra("hcp_id");

        init();
        setFonts();

        new AsyncClass().execute();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsPage.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsPage.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsPage.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsPage.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsPage.this,MyAccount.class);
                startActivity(i);
            }
        });
    }

    public void init() {
        deals = (LinearLayout) findViewById(R.id.deals);
        amenities = (LinearLayout) findViewById(R.id.amenities);
        specialities = (LinearLayout) findViewById(R.id.specialities);
        services = (LinearLayout) findViewById(R.id.services);
        availability = (LinearLayout) findViewById(R.id.availability);
        location = (LinearLayout) findViewById(R.id.location);
        about = (LinearLayout) findViewById(R.id.about);
        equipments = (LinearLayout) findViewById(R.id.equipments);
        social = (LinearLayout) findViewById(R.id.social);
        writereview = (LinearLayout) findViewById(R.id.writereview);
        follow = (LinearLayout)     findViewById(R.id.follow);

        topimage = (ImageView) findViewById(R.id.topimage);
        back    = (ImageView) findViewById(R.id.back);
        tvname = (TextView) findViewById(R.id.tvname);
        tvaddress = (TextView) findViewById(R.id.address);
        tvreviews_count = (TextView) findViewById(R.id.reviews_count);
        tvlikes = (TextView) findViewById(R.id.likes);
        tvopennow = (TextView) findViewById(R.id.opennow);
        tvopentimings = (TextView) findViewById(R.id.opentimings);

        tvcall = (TextView) findViewById(R.id.tvcall);
        tvchat = (TextView) findViewById(R.id.tvchat);
        tvfollow = (TextView) findViewById(R.id.tvfollow);
        tvdeals = (TextView) findViewById(R.id.tvdeals);
        tvamenities = (TextView) findViewById(R.id.tvamenities);
        tvspecialities = (TextView) findViewById(R.id.tvspecialities);
        tvservices = (TextView) findViewById(R.id.tvservices);
        tvavailability = (TextView) findViewById(R.id.tvavailability);
        tvlocation = (TextView) findViewById(R.id.tvlocation);
        tvabout = (TextView) findViewById(R.id.tvabout);
        tvequipments = (TextView) findViewById(R.id.tvequipments);
        tvnavigate = (TextView) findViewById(R.id.navigate);
        tvsocial    = (TextView) findViewById(R.id.tvsocial);

        tvHome          = (TextView)        findViewById(R.id.tvHome);
        tvNotification  = (TextView)        findViewById(R.id.tvNotification);
        tvChat          = (TextView)        findViewById(R.id.tvChat);
        tvFavourites    = (TextView)        findViewById(R.id.tvFavourites);
        tvAccounts      = (TextView)        findViewById(R.id.tvAccounts);
        llhome          = (LinearLayout)    findViewById(R.id.homell);
        llnotification  = (LinearLayout)    findViewById(R.id.notificationll);
        llchat          = (LinearLayout)    findViewById(R.id.chatll);
        llfavorites     = (LinearLayout)    findViewById(R.id.favouritesll);
        llacounts       = (LinearLayout)    findViewById(R.id.accountsll);

        one             = (ImageView)       findViewById(R.id.star1);
        two             = (ImageView)       findViewById(R.id.star2);
        three           = (ImageView)       findViewById(R.id.star3);
        four            = (ImageView)       findViewById(R.id.star4);
        five            = (ImageView)       findViewById(R.id.star5);

    }

    public void setFonts() {
        tvname.setTypeface(tf);
        tvaddress.setTypeface(tf);
        tvreviews_count.setTypeface(tf);
        tvlikes.setTypeface(tf);
        tvopennow.setTypeface(tf);
        tvopentimings.setTypeface(tf);
        tvcall.setTypeface(tf);
        tvchat.setTypeface(tf);
        tvfollow.setTypeface(tf);
        tvdeals.setTypeface(tf);
        tvamenities.setTypeface(tf);
        tvspecialities.setTypeface(tf);
        tvservices.setTypeface(tf);
        tvavailability.setTypeface(tf);
        tvlocation.setTypeface(tf);
        tvabout.setTypeface(tf);
        tvequipments.setTypeface(tf);
        tvsocial.setTypeface(tf);
        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);;
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);
    }

    public class AsyncClass extends AsyncTask<Void, Void, Void> {
        String json_values = "";
        ArrayList<String> arr_frm,arr_to,arr_note;
        ArrayList<String> arr_am,arr_sp,arr_eq,arr_soc;
        String is_following,like_count, followers_count, rating, refer_friend_points,
                location_contacts, licence_no, year_of_establishment, pincode,
                geo_longi, geo_lat, state, city, Locality, landmark, location_address, location_name, from_time, to_time, service_name,service_description, photo_path = "",hcp_name
                ,hcp_description,hcp_experience,hcp_language_known,hcp_education,note;
        String adoctor_name,adoctor_description,acharges,afrom_time,ato_time,anote,aavail_blood,aqty,
                apathlab__title,ahag_batch_name,ahag_batch_type,ahag_batch_trainer
                ,aseats_open,aspecialist,adescription,aabout;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arr_am = new ArrayList<>();
            arr_sp = new ArrayList<>();
            arr_eq = new ArrayList<>();
            arr_soc = new ArrayList<>();
            arr_frm = new ArrayList<>();
            arr_to = new ArrayList<>();
            arr_note = new ArrayList<>();

            url_dump.startprogress("Fetching from server", "Please wait",DetailsPage.this,true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                json_values = url_dump.getDetails(user_id, hcp_id);
                JSONArray mainarray = new JSONArray(json_values);
                String code = mainarray.getString(0);
                String message = mainarray.getString(1);
                JSONArray firstarray = mainarray.getJSONArray(2);
                for (int f = 0; f < firstarray.length(); f++) {
                    JSONObject fobject = firstarray.getJSONObject(f);

                    JSONObject service = fobject.getJSONObject("services");
                    String hs_oid = service.getString("hs_oid");
                    String hl_oid = service.getString("hl_oid");
                    String hcp_user_oid = service.getString("hcp_user_oid");
                    String hcp_cat_oid = service.getString("hcp_cat_oid");
                    service_name = service.getString("service_name");
                    service_description = service.getString("description");

                    JSONArray location = fobject.getJSONArray("location");
                    for (int l = 0; l < location.length(); l++) {
                        JSONObject lobject = location.getJSONObject(l);

                        try {
                            location_name = lobject.getString("location_name");
                            location_address = lobject.getString("location_address");
                            landmark = lobject.getString("landmark");
                            Locality = lobject.getString("Locality");
                            city = lobject.getString("city");
                            state = lobject.getString("state");
                            geo_lat = lobject.getString("geo_lat");
                            geo_longi = lobject.getString("geo_longi");
                            pincode = lobject.getString("pincode");
                            year_of_establishment = lobject.getString("year_of_establishment");
                            licence_no = lobject.getString("licence_no");
                            location_contacts = lobject.getString("location_contacts");
                        } catch (Exception e) {
                            e.printStackTrace();
                            url_dump.dismissprogress();
                        }

                        JSONArray location_time = lobject.getJSONArray("location_time");
                        for (int lt = 0; lt < location_time.length(); lt++) {
                            try {
                                JSONObject ob = location_time.getJSONObject(lt);
                                from_time = ob.getString("from_time");
                                to_time = ob.getString("to_time");
                                note = ob.getString("note");

                                arr_frm.add(from_time);
                                arr_to.add(to_time);
                                arr_note.add(note);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        JSONArray amenities = lobject.getJSONArray("amenities");
                        for (int am = 0; am < amenities.length(); am++) {
                            try {
                                JSONObject amob = amenities.getJSONObject(am);
                                String amenities_name = amob.getString("amenities_name");
                                arr_am.add(amenities_name);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        JSONArray photo_gallery = lobject.getJSONArray("photo_gallery");
                        for (int pg = 0; pg < photo_gallery.length(); pg++) {
                            try {
                                JSONObject pgob = photo_gallery.getJSONObject(pg);
                                photo_path = pgob.getString("photo_path");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        JSONArray availability = lobject.getJSONArray("availability");
                        for (int av = 0; av < availability.length(); av++) {
                            try {
                                JSONObject avob = availability.getJSONObject(av);

                                if(cat_id.equals("1"))
                                {
                                    //Clinics
                                    adoctor_name = avob.getString("doctor_name");
                                    adoctor_description = avob.getString("doctor_description");
                                    acharges = avob.getString("charges");
                                    afrom_time = avob.getString("from_time");
                                    ato_time = avob.getString("to_time");
                                    anote = avob.getString("note");
                                }
                                if(cat_id.equals("2"))
                                {
                                    adoctor_name = avob.getString("doctor_name");
                                    adoctor_description = avob.getString("doctor_description");
                                    acharges = avob.getString("charges");
                                    afrom_time = avob.getString("from_time");
                                    ato_time = avob.getString("to_time");
                                    anote = avob.getString("note");
                                }
                                if(cat_id.equals("3"))
                                {
                                    apathlab__title = avob.getString("pathlab__title");
                                    adescription = avob.getString("description");
                                    acharges = avob.getString("charges");
                                }
                                if(cat_id.equals("4"))
                                {
                                    ahag_batch_name = avob.getString("hag_batch_name");
                                    ahag_batch_type = avob.getString("hag_batch_type");
                                    ahag_batch_trainer = avob.getString("hag_batch_trainer");
                                    afrom_time = avob.getString("from_time");
                                    ato_time = avob.getString("to_time");
                                    aseats_open = avob.getString("seats_open");
                                    acharges = avob.getString("charges");
                                }
                                if(cat_id.equals("5"))
                                {
                                    aavail_blood = avob.getString("avail_blood");
                                    aqty = avob.getString("qty");
                                    acharges = avob.getString("charges");
                                }
                                if(cat_id.equals("6"))
                                {
                                    aspecialist = avob.getString("specialist");
                                    adescription = avob.getString("description");
                                    aabout = avob.getString("about");
                                    afrom_time = avob.getString("from_time");
                                    ato_time = avob.getString("to_time");
                                    acharges = avob.getString("charges");
                                }
                                if(cat_id.equals("7"))
                                {
                                    afrom_time = avob.getString("from_time");
                                    ato_time = avob.getString("to_time");
                                    anote = avob.getString("note");
                                }
                                if(cat_id.equals("8"))
                                {
                                    adoctor_name = avob.getString("doctor_name");
                                    adoctor_description = avob.getString("doctor_description");
                                    acharges = avob.getString("charges");
                                    afrom_time = avob.getString("from_time");
                                    ato_time = avob.getString("to_time");
                                    anote = avob.getString("note");
                                }
                                if(cat_id.equals("9"))
                                {
                                    aavail_blood = avob.getString("avail_blood");
                                    aqty = avob.getString("qty");
                                    acharges = avob.getString("charges");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        JSONArray about = lobject.getJSONArray("about");
                        for (int ab = 0; ab < about.length(); ab++) {
                            try {
                                JSONObject abob = about.getJSONObject(ab);
                                hcp_name = abob.getString("hcp_name");
                                hcp_description = abob.getString("hcp_description");
                                hcp_experience = abob.getString("hcp_experience");
                                hcp_language_known = abob.getString("hcp_language_known");
                                hcp_education = abob.getString("hcp_education");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        JSONArray known_for = lobject.getJSONArray("known_for");
                        for (int kn = 0; kn < known_for.length(); kn++) {
                            try {
                                JSONObject knob = known_for.getJSONObject(kn);
                                String bkf_name = knob.getString("bkf_name");
                                String bkf_description = knob.getString("bkf_description");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        JSONArray equipments = lobject.getJSONArray("equipments");
                        for (int eq = 0; eq < equipments.length(); eq++) {
                            try {
                                JSONObject eqob = equipments.getJSONObject(eq);
                                String equip_name = eqob.getString("equip_name");
                                String equip_desc = eqob.getString("equip_desc");
                                arr_eq.add(equip_name+": "+equip_desc);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        JSONArray speciality = lobject.getJSONArray("speciality");
                        for (int sp = 0; sp < speciality.length(); sp++) {
                            try {
                                JSONObject spob = speciality.getJSONObject(sp);
                                String speciality_name = spob.getString("speciality_name");
                                String speciality_description = spob.getString("speciality_description");
                                arr_sp.add(""+speciality_name+": "+speciality_description);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        JSONArray social = lobject.getJSONArray("social_links");
                        for (int so = 0; so < social.length(); so++) {
                            try {
                                JSONObject soob = social.getJSONObject(so);
                                String social_type = soob.getString("social_type");
                                String social_link = soob.getString("social_link");
                                arr_soc.add("\u2022 "+ social_type +": "+social_link);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        JSONArray deals = lobject.getJSONArray("deals");
                        for (int de = 0; de < deals.length(); de++) {
                            try {
                                JSONObject soob = deals.getJSONObject(de);
                                String offer_caption = soob.getString("offer_caption");
                                String send_to = soob.getString("send_to");
                                String deal_image = soob.getString("deal_image");
                                String from_date = soob.getString("from_date");
                                String to_date = soob.getString("to_date");
                                String creation_date = soob.getString("creation_date");
                                String offer_flat = soob.getString("offer_flat");
                                String offer_discount = soob.getString("offer_discount");
                                String noti_email = soob.getString("noti_email");
                                String frequency = soob.getString("frequency");
                                String status = soob.getString("status");
                                String approve_status = soob.getString("approve_status");
                                String description = soob.getString("description");
                                String terms_condition = soob.getString("terms_condition");
                                String push_date = soob.getString("push_date");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        try {
                            like_count = lobject.getString("like_count");
                            followers_count = lobject.getString("followers_count");
                            rating = lobject.getString("rating_value");
                            reviews = lobject.getString("rating_count");
                            refer_friend_points = lobject.getString("refer_friend_points");
                            is_following    = lobject.getString("is_following");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                url_dump.dismissprogress();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            url_dump.dismissprogress();



            try {
                if (!photo_path.equals("")) {
                    Picasso.with(context).load(photo_path).placeholder(R.drawable.hospc).into(topimage);
                }

                tvname.setText(service_name);
                tvaddress.setText(location_name+", "+city);
                tvreviews_count.setText(reviews+" Reviews");
                tvlikes.setText(like_count + " Likes");
                tvnavigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + geo_lat + "," + geo_longi));
                        startActivity(intent);
                    }
                });
                tvcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(location_contacts.contains(","))
                        {
                            List<String> cont_list = Arrays.asList(location_contacts.split(","));
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                            LayoutInflater inflater = getLayoutInflater();
                            View convertView = (View) inflater.inflate(R.layout.amenities, null);
                            alertDialog.setView(convertView);
                            ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                            TextView tv = (TextView) convertView.findViewById(R.id.text);
                            tv.setText("Contact");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, cont_list);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:"+parent.getItemAtPosition(position)));
                                    startActivity(intent);
                                }
                            });
                            alertDialog.show();
                        }
                        else{
                            ArrayList<String> val = new ArrayList<String>();
                            val.add(location_contacts);
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                            LayoutInflater inflater = getLayoutInflater();
                            View convertView = (View) inflater.inflate(R.layout.amenities, null);
                            alertDialog.setView(convertView);
                            ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                            TextView tv = (TextView) convertView.findViewById(R.id.text);
                            tv.setText("Contact");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, val);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:"+parent.getItemAtPosition(position)));
                                    startActivity(intent);
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
                amenities.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("Amenities");
                        if(arr_am.size()==0)
                        {
                            arr_am.add("N.A.");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arr_am);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });
                specialities.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        tv.setText("Specialities");
                        if(arr_sp.size()==0)
                        {
                            arr_sp.add("N.A.");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arr_sp);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });
                services.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<String> arrr = new ArrayList<String>();
                        arrr.add(service_name+" - " + service_description);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("Services");
                        if(arrr.size()==0)
                        {
                            arrr.add("N.A.");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arrr);
                        lv.setAdapter(adapter);
                        alertDialog.show();

                    }
                });
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> arrr = new ArrayList<String>();
                        arrr.add(" Location Name: "+location_name);
                        arrr.add(" Location Address: "+location_address);
                        arrr.add(" Landmarks: "+landmark);
                        arrr.add(" Locality: "+Locality);
                        arrr.add(" City: "+city);


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("Location & Address");

                        if(arrr.size()==0)
                        {
                            arrr.add("N.A.");
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arrr);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });
                about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<String> arrr = new ArrayList<String>();

                        arrr.add(" Name: "+ hcp_name);
                        arrr.add(" Description: "+hcp_description);
                        arrr.add(" Experience: "+hcp_experience);
                        arrr.add(" Language Known: "+hcp_language_known);
                        arrr.add(" Education: "+hcp_education);


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("About");
                        if(arrr.size()==0)
                        {
                            arrr.add("N.A.");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arrr);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });
                equipments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        tv.setText("Equipments");
                        if(arr_eq.size()==0)
                        {
                            arr_eq.add("N.A.");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arr_eq);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });
                social.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        tv.setText("Social");
                        if(arr_soc.size()==0)
                        {
                            arr_soc.add("N.A.");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arr_soc);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });

                writereview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DetailsPage.this,ReviewPage.class);
                        startActivity(i);
                    }
                });
                availability.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<String> arrr = new ArrayList<String>();
                        if(cat_id.equals("1"))
                        {
                            //Clinics
                            arrr.add(" Name: "+ adoctor_name);
                            arrr.add(" Name: "+ adoctor_name);
                            arrr.add(" Description: "+ adoctor_description);
                            arrr.add(" Charges: "+ acharges);
                            arrr.add(" From Time: "+ afrom_time);
                            arrr.add(" To Time: "+ ato_time);
                            arrr.add(" Note: "+ anote);
                        }
                        if(cat_id.equals("2"))
                        {
                            arrr.add(" Name: "+ adoctor_name);
                            arrr.add(" Description: "+ adoctor_description);
                            arrr.add(" Charges: "+ acharges);
                            arrr.add(" From Time: "+ afrom_time);
                            arrr.add(" To Time: "+ ato_time);
                            arrr.add(" Note: "+ anote);
                        }
                        if(cat_id.equals("3"))
                        {
                            arrr.add(" Pathlab name: "+ apathlab__title);
                            arrr.add(" Description: "+ adescription);
                            arrr.add(" Charges: "+ acharges);
                        }
                        if(cat_id.equals("4"))
                        {
                            arrr.add(" Batch Name: "+ ahag_batch_name);
                            arrr.add(" Batch Type: "+ ahag_batch_type);
                            arrr.add(" Batch Trainer: "+ ahag_batch_trainer);
                            arrr.add(" From Time: "+ afrom_time);
                            arrr.add(" To Time: "+ ato_time);
                            arrr.add(" Seats Open: "+ aseats_open);
                            arrr.add(" Charges: "+ acharges);
                        }
                        if(cat_id.equals("5"))
                        {
                            arrr.add(" Available Blood: "+ aavail_blood);
                            arrr.add(" Quantity: "+ aqty);
                            arrr.add(" Charges: "+ acharges);
                        }
                        if(cat_id.equals("6"))
                        {
                            arrr.add(" Specialists: "+ aspecialist);
                            arrr.add(" Description: "+ adescription);
                            arrr.add(" About: "+ aabout);
                            arrr.add(" From Time: "+ afrom_time);
                            arrr.add(" To Time: "+ ato_time);
                            arrr.add(" Charges: "+ acharges);
                        }
                        if(cat_id.equals("7"))
                        {
                            arrr.add(" From Time: "+ afrom_time);
                            arrr.add(" To Time: "+ ato_time);
                            arrr.add(" Note: "+ anote);
                        }
                        if(cat_id.equals("8"))
                        {
                            arrr.add(" Name: "+ adoctor_name);
                            arrr.add(" Description: "+ adoctor_description);
                            arrr.add(" Charges: "+ acharges);
                            arrr.add(" From Time: "+ afrom_time);
                            arrr.add(" To Time: "+ ato_time);
                            arrr.add(" Note: "+ anote);
                        }
                        if(cat_id.equals("9"))
                        {
                            arrr.add(" Available Blood: "+ aavail_blood);
                            arrr.add(" Quantity: "+ aqty);
                            arrr.add(" Charges: "+ acharges);
                        }
                        if(arrr.size()==0)
                        {
                            arrr.add("N.A.");
                        }
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.amenities, null);
                        alertDialog.setView(convertView);
                        ListView lv = (ListView) convertView.findViewById(R.id.list_amenities);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("Availability");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arrr);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });

                deals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(DetailsPage.this,DealsOffers.class);
                        i.putExtra("value",cat_id);
                        i.putExtra("hcp_id",hcp_id);
                        startActivity(i);
                    }
                });

                if(is_following.equals("1"))
                {
                    Log.d("console","following");
                    tvfollow.setText("Unfollow");
                    follow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                            alertBuilder.setCancelable(true);
                            alertBuilder.setTitle("Alert");
                            alertBuilder.setMessage("Do you really want to unfollow this HCP");
                            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(DialogInterface dialog, int which) {
                                    new UnFollowAsync().execute();
                                }
                            });
                            alertBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = alertBuilder.create();
                            alert.show();

                        }
                    });
                }
                else{
                    Log.d("console","Follow");
                    tvfollow.setText("Follow");
                    follow.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new FollowAsync().execute();
                        }
                    });
                }
                String str_rating = rating;
                try {
                    double value = Double.parseDouble(str_rating);
                    int int_value = (int) Math.ceil(value);
                    str_rating = String.valueOf(int_value);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                if(str_rating.equals("1"))
                {
                    one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    two.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                    three.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                    four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                    five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                }
                else if(str_rating.equals("2"))
                {
                    one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    three.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                    four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                    five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                }
                else if(str_rating.equals("3"))
                {
                    one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                    five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                }
                else if(str_rating.equals("4"))
                {
                    one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    four.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                }
                else if(str_rating.equals("5"))
                {
                    one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    four.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                    five.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                }

                for(int i = 0;i< arr_note.size();i++)
                {
                    from_time = arr_frm.get(i);
                    to_time = arr_to.get(i);
                    note = arr_note.get(i);
                    Log.d("comimg here","values");
                    if(!from_time.equals("null") && !to_time.equals("null"))
                    {
                        Log.d("comimg here","inside if");
                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => "+c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                        String formattedDate = df.format(c.getTime());
                        Log.d("now",formattedDate);

                            Log.d("comimg here",""+note);
                            if(isTimeBetweenTwoTime(from_time,to_time,formattedDate))
                            {
                                tvopennow.setText("Open: ");
                                tvopennow.setTextColor(getResources().getColor(R.color.green));
                                break;
                            }
                            else {
                                tvopennow.setText("Closed: ");
                                tvopennow.setTextColor(getResources().getColor(R.color.red));
                            }
                    }
                }
                from_time = from_time.substring(0, from_time.length() - 3);
                to_time = to_time.substring(0, to_time.length() - 3);
                tvopentimings.setText("" + from_time + " to " + to_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addValue(String message,LinearLayout layout)
    {
        TextView valueTV = new TextView(context);
        valueTV.setText(message);
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ((LinearLayout) layout).addView(valueTV);
    }
    public class FollowAsync extends AsyncTask<Void,Void,Void>
    {
        public String json_values;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                json_values = url_dump.sendFollow(user_id, hcp_id);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //["HCPC1800","Success",1]
            try{
                JSONArray array= new JSONArray(json_values);
                String code = array.getString(0);
                String message = array.getString(1);
                if(code.equals("HCPC1800"))
                {
                    Toast.makeText(DetailsPage.this, ""+message, Toast.LENGTH_SHORT).show();
                    new AsyncClass().execute();
                }
                else{
                    Toast.makeText(DetailsPage.this, ""+message, Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e)
            {

            }

        }
    }
    public class UnFollowAsync extends AsyncTask<Void,Void,Void>
    {
        public String json_values;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                json_values = url_dump.sendUnFollow(user_id, hcp_id);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //["HCPC1800","Success",1]
            try{
                JSONArray array= new JSONArray(json_values);
                String code = array.getString(0);
                String message = array.getString(1);
                if(code.equals("HCPC3200"))
                {
                    Toast.makeText(DetailsPage.this, ""+message, Toast.LENGTH_SHORT).show();
                    new AsyncClass().execute();
                }
                else{
                    Toast.makeText(DetailsPage.this, ""+message, Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e)
            {

            }

        }
    }
}
