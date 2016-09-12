package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

    LinearLayout deals,amenities,specialities,services,availability,location,about,equipments,social,writereview;
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
    }

    public class AsyncClass extends AsyncTask<Void, Void, Void> {
        String json_values = "";
        ArrayList<String> arr_am,arr_sp,arr_eq,arr_soc;
        String like_count, followers_count, rating, refer_friend_points,
                location_contacts, licence_no, year_of_establishment, pincode,
                geo_longi, geo_lat, state, city, Locality, landmark, location_address, location_name, from_time, to_time, service_name,service_description, photo_path = "",hcp_name
                ,hcp_description,hcp_experience,hcp_language_known,hcp_education;
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
                        }

                        JSONArray location_time = lobject.getJSONArray("location_time");
                        for (int lt = 0; lt < location_time.length(); lt++) {
                            try {
                                JSONObject ob = location_time.getJSONObject(lt);
                                from_time = ob.getString("from_time");
                                to_time = ob.getString("to_time");
                                String note = ob.getString("note");
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
                                arr_eq.add("\u2022 "+equip_name+": "+equip_desc);
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
                                arr_sp.add("\u2022 "+speciality_name+": "+speciality_description);
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
                                String hdo_oid = soob.getString("hdo_oid");
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
                            rating = lobject.getString("rating");
                            refer_friend_points = lobject.getString("refer_friend_points");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                if (!photo_path.equals("")) {
                    Picasso.with(context).load(photo_path).placeholder(R.drawable.hospc).into(topimage);
                }

                tvname.setText(service_name);
                tvaddress.setText(location_address);
                tvreviews_count.setText("0 Reviews");
                tvlikes.setText(like_count + " Likes");
                tvopentimings.setText("" + from_time + " to " + to_time);
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
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+location_contacts));
                        startActivity(intent);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arr_sp);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });
                services.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.service_layout, null);
                        alertDialog.setView(convertView);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        TextView tv1 = (TextView) convertView.findViewById(R.id.textname);
                        tv.setText("Services");
                        tv1.setText("\u2022 "+service_name+" - " + service_description);
                        alertDialog.show();
                    }
                });
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.location, null);
                        alertDialog.setView(convertView);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("Location & Address");
                        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.info);
                        layout.removeAllViews();
                        addValue("\u2022 "+" Location Name: "+location_name,layout);
                        addValue("\u2022 "+" Location Address: "+location_address,layout);
                        addValue("\u2022 "+" Landmarks: "+landmark,layout);
                        addValue("\u2022 "+" Locality: "+Locality,layout);
                        addValue("\u2022 "+" City: "+city,layout);
                        addValue("\u2022 "+" State: "+state,layout);
                        addValue("\u2022 "+" Pincode: "+pincode,layout);
                        addValue("\u2022 "+" Location Contacts: "+location_contacts,layout);
                        addValue("\u2022 "+" Year of Estb: "+year_of_establishment,layout);
                        addValue("\u2022 "+" From time : "+from_time,layout);
                        addValue("\u2022 "+" To time: "+to_time,layout);
                        alertDialog.show();

                    }
                });
                about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.location, null);
                        alertDialog.setView(convertView);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("About ");
                        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.info);
                        layout.removeAllViews();
                        addValue("\u2022 "+" HCP Name: "+ hcp_name,layout);
                        addValue("\u2022 "+" HCP Description: "+hcp_description,layout);
                        addValue("\u2022 "+" HCP Experience: "+hcp_experience,layout);
                        addValue("\u2022 "+" Language Known: "+hcp_language_known,layout);
                        addValue("\u2022 "+" HCP Education: "+hcp_education,layout);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsPage.this, android.R.layout.simple_list_item_1, arr_soc);
                        lv.setAdapter(adapter);
                        alertDialog.show();
                    }
                });

                availability.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View convertView = (View) inflater.inflate(R.layout.location, null);
                        alertDialog.setView(convertView);
                        TextView tv = (TextView) convertView.findViewById(R.id.text);
                        tv.setText("Availability ");
                        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.info);
                        layout.removeAllViews();

                        if(cat_id.equals("1"))
                        {
                            //Clinics
                            addValue("\u2022 "+" Name: "+ adoctor_name,layout);
                            addValue("\u2022 "+" Description: "+ adoctor_description,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                            addValue("\u2022 "+" From Time: "+ afrom_time,layout);
                            addValue("\u2022 "+" To Time: "+ ato_time,layout);
                            addValue("\u2022 "+" Note: "+ anote,layout);
                        }
                        if(cat_id.equals("2"))
                        {
                            addValue("\u2022 "+" Name: "+ adoctor_name,layout);
                            addValue("\u2022 "+" Description: "+ adoctor_description,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                            addValue("\u2022 "+" From Time: "+ afrom_time,layout);
                            addValue("\u2022 "+" To Time: "+ ato_time,layout);
                            addValue("\u2022 "+" Note: "+ anote,layout);
                        }
                        if(cat_id.equals("3"))
                        {
                            addValue("\u2022 "+" Pathlab name: "+ apathlab__title,layout);
                            addValue("\u2022 "+" Description: "+ adescription,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                        }
                        if(cat_id.equals("4"))
                        {
                            addValue("\u2022 "+" Batch Name: "+ ahag_batch_name,layout);
                            addValue("\u2022 "+" Batch Type: "+ ahag_batch_type,layout);
                            addValue("\u2022 "+" Batch Trainer: "+ ahag_batch_trainer,layout);
                            addValue("\u2022 "+" From Time: "+ afrom_time,layout);
                            addValue("\u2022 "+" To Time: "+ ato_time,layout);
                            addValue("\u2022 "+" Seats Open: "+ aseats_open,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                        }
                        if(cat_id.equals("5"))
                        {
                            addValue("\u2022 "+" Available Blood: "+ aavail_blood,layout);
                            addValue("\u2022 "+" Quantity: "+ aqty,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                        }
                        if(cat_id.equals("6"))
                        {
                            addValue("\u2022 "+" Specialists: "+ aspecialist,layout);
                            addValue("\u2022 "+" Description: "+ adescription,layout);
                            addValue("\u2022 "+" About: "+ aabout,layout);
                            addValue("\u2022 "+" From Time: "+ afrom_time,layout);
                            addValue("\u2022 "+" To Time: "+ ato_time,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                        }
                        if(cat_id.equals("7"))
                        {
                            addValue("\u2022 "+" From Time: "+ afrom_time,layout);
                            addValue("\u2022 "+" To Time: "+ ato_time,layout);
                            addValue("\u2022 "+" Note: "+ anote,layout);
                        }
                        if(cat_id.equals("8"))
                        {
                            addValue("\u2022 "+" Name: "+ adoctor_name,layout);
                            addValue("\u2022 "+" Description: "+ adoctor_description,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                            addValue("\u2022 "+" From Time: "+ afrom_time,layout);
                            addValue("\u2022 "+" To Time: "+ ato_time,layout);
                            addValue("\u2022 "+" Note: "+ anote,layout);
                        }
                        if(cat_id.equals("9"))
                        {
                            addValue("\u2022 "+" Available Blood: "+ aavail_blood,layout);
                            addValue("\u2022 "+" Quantity: "+ aqty,layout);
                            addValue("\u2022 "+" Charges: "+ acharges,layout);
                        }

                        alertDialog.show();
                    }
                });

                deals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            } catch (Exception e) {

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
}
