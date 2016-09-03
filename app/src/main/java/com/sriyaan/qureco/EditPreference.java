package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sriyaan.util.url_dump.Toastthis;
import static com.sriyaan.util.url_dump.removeLastChar;

public class EditPreference extends AppCompatActivity {

    Toolbar toolbar;
    Context con;
    ArrayList<String> array_cat_name,array_cat_image,array_cat_image_selected,
            array_cat_id ;
    String response="";
    String hcp_cust_cat_id,hcp_cust_cat_name,hcp_cust_cat_image,hcp_cust_cat_image_selected,
            hcp_cust_cat_order,hcp_cust_cat_status,hcp_cust_cat_deleted,hcp_cust_cat_add_date;

    ImageView clinics,hospital,pathlab,fitness,bloodbanks,salon,pharmacy,doctor,spa;
    Button btnSave;
    int cclinics,chospital,cpathlab,cfitness,cbloodbanks,csalon,cpharmacy,cdoctor,cspa;
    Animation flipin,flipout;
    SharedPreferences prefs;
    List<String> cat;
    String strInterest="";
    String categories,user_id;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;

    TextView tvselectPreference,tvdoctor,tvclinics,tvpathlab,tvfitness,tvsalon,tvspa,tvpharmacy,tvhospital,tvbloodbank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_preference);

        init();
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        setTitle("");
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(tf);
        mTitle.setText("Edit Preference");
        setFont();
        categories = prefs.getString("cust_interests","");
        user_id = prefs.getString("cust_id","");
        Log.d("categories",categories);
        //categories = removeLastChar(categories);
        //Log.d("new categories",categories);
        cat = Arrays.asList(categories.split(","));
        for(int i=0;i<cat.size();i++)
        {
            Log.d("new categories",cat.get(i));
            LoadCategories(cat.get(i).trim()+"");
        }

        //new GetCategories().execute();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadLayout("login");
                validateRegister();
            }
        });
        flipin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_in);
        flipout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_out);
        clinics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cclinics==0)
                {
                    clinics.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            clinics.setImageDrawable(getResources().getDrawable(R.drawable.clinic_));
                            //Picasso.with(con).load(array_cat_image_selected.get(0)).into(clinics);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cclinics++;
                }
                else{
                    clinics.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            clinics.setImageDrawable(getResources().getDrawable(R.drawable.clinic));
                            //Picasso.with(con).load(array_cat_image.get(0)).into(clinics);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    cclinics--;
                }
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chospital==0)
                {
                    hospital.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            hospital.setImageDrawable(getResources().getDrawable(R.drawable.hospital_));
                            //Picasso.with(con).load(array_cat_image_selected.get(1)).into(hospital);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    chospital++;
                }
                else{
                    hospital.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            hospital.setImageDrawable(getResources().getDrawable(R.drawable.hospital));
                            //Picasso.with(con).load(array_cat_image.get(1)).into(hospital);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    chospital--;
                }
            }
        });
        pathlab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cpathlab==0)
                {
                    pathlab.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            pathlab.setImageDrawable(getResources().getDrawable(R.drawable.pathlab_));
                            //Picasso.with(con).load(array_cat_image_selected.get(2)).into(pathlab);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cpathlab++;
                }
                else{
                    pathlab.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            pathlab.setImageDrawable(getResources().getDrawable(R.drawable.pathlab));
                            //Picasso.with(con).load(array_cat_image.get(2)).into(pathlab);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    cpathlab--;
                }
            }
        });
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cfitness==0)
                {
                    fitness.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            fitness.setImageDrawable(getResources().getDrawable(R.drawable.fitness_));
                            //Picasso.with(con).load(array_cat_image_selected.get(3)).into(fitness);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cfitness++;
                }
                else{
                    fitness.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            fitness.setImageDrawable(getResources().getDrawable(R.drawable.fitness));
                            //Picasso.with(con).load(array_cat_image.get(3)).into(fitness);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    cfitness--;
                }
            }
        });
        bloodbanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cbloodbanks==0)
                {
                    bloodbanks.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            bloodbanks.setImageDrawable(getResources().getDrawable(R.drawable.bloodbank_));
                            //Picasso.with(con).load(array_cat_image_selected.get(4)).into(bloodbanks);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cbloodbanks++;
                }
                else{
                    bloodbanks.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            bloodbanks.setImageDrawable(getResources().getDrawable(R.drawable.bloodbank));
                            //Picasso.with(con).load(array_cat_image.get(4)).into(bloodbanks);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    cbloodbanks--;
                }
            }
        });
        salon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(csalon==0)
                {
                    salon.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            salon.setImageDrawable(getResources().getDrawable(R.drawable.salon_));
                            //Picasso.with(con).load(array_cat_image_selected.get(5)).into(salon);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    csalon++;
                }
                else{
                    salon.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            salon.setImageDrawable(getResources().getDrawable(R.drawable.salon));
                            //Picasso.with(con).load(array_cat_image.get(5)).into(salon);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    csalon--;
                }
            }
        });
        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cpharmacy==0)
                {
                    pharmacy.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            pharmacy.setImageDrawable(getResources().getDrawable(R.drawable.pharmacy_));
                            //Picasso.with(con).load(array_cat_image_selected.get(6)).into(pharmacy);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cpharmacy++;
                }
                else{
                    pharmacy.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            pharmacy.setImageDrawable(getResources().getDrawable(R.drawable.pharmacy));
                            //Picasso.with(con).load(array_cat_image.get(6)).into(pharmacy);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    cpharmacy--;
                }
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cdoctor==0)
                {
                    doctor.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            doctor.setImageDrawable(getResources().getDrawable(R.drawable.doctor_));
                            //Picasso.with(con).load(array_cat_image_selected.get(7)).into(doctor);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cdoctor++;
                }
                else{
                    doctor.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            doctor.setImageDrawable(getResources().getDrawable(R.drawable.doctor));
                            //Picasso.with(con).load(array_cat_image.get(7)).into(doctor);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    cdoctor--;
                }
            }
        });
        spa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cspa==0)
                {
                    spa.startAnimation(flipin);
                    flipin.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            spa.setImageDrawable(getResources().getDrawable(R.drawable.spa_));
                            //Picasso.with(con).load(array_cat_image_selected.get(8)).into(spa);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cspa++;
                }
                else{
                    spa.startAnimation(flipout);
                    flipout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            spa.setImageDrawable(getResources().getDrawable(R.drawable.spa));
                            //Picasso.with(con).load(array_cat_image.get(8)).into(spa);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    cspa--;
                }
            }
        });
    }
    public void init(){
        con                 = EditPreference.this;
        toolbar             = (Toolbar)     findViewById(R.id.toolbar);
        clinics             = (ImageView)   findViewById(R.id.clinics);
        hospital            = (ImageView)   findViewById(R.id.hospital);
        pathlab             = (ImageView)   findViewById(R.id.pathlab);
        fitness             = (ImageView)   findViewById(R.id.fitness);
        bloodbanks          = (ImageView)   findViewById(R.id.bloodbanks);
        salon               = (ImageView)   findViewById(R.id.salon);
        pharmacy            = (ImageView)   findViewById(R.id.pharmacy);
        doctor              = (ImageView)   findViewById(R.id.doctor);
        spa                 = (ImageView)   findViewById(R.id.spa);
        btnSave             = (Button)      findViewById(R.id.btnSave);

        tvselectPreference  = (TextView)    findViewById(R.id.tvselectPreference);
        tvdoctor            = (TextView)    findViewById(R.id.tvdoctor);
        tvclinics           = (TextView)    findViewById(R.id.tvclinics);
        tvpathlab           = (TextView)    findViewById(R.id.tvpathlab);
        tvfitness           = (TextView)    findViewById(R.id.tvfitness);
        tvsalon             = (TextView)    findViewById(R.id.tvsalon);
        tvspa               = (TextView)    findViewById(R.id.tvspa);
        tvpharmacy          = (TextView)    findViewById(R.id.tvpharmacy);
        tvhospital          = (TextView)    findViewById(R.id.tvhospital);
        tvbloodbank         = (TextView)    findViewById(R.id.tvbloodbank);
    }

    public void setFont()
    {
        tvselectPreference  .setTypeface(tf);
        tvdoctor            .setTypeface(tf);
        tvclinics           .setTypeface(tf);
        tvpathlab           .setTypeface(tf);
        tvfitness           .setTypeface(tf);
        tvsalon             .setTypeface(tf);
        tvspa               .setTypeface(tf);
        tvpharmacy          .setTypeface(tf);
        tvhospital          .setTypeface(tf);
        tvbloodbank         .setTypeface(tf);
        btnSave             .setTypeface(tf);
    }
    public class GetCategories extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                response = url_dump.GetCategories();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("response","Data:  "+response);
            try{
                JSONArray array = new JSONArray(response);

                array_cat_name = new ArrayList<>();
                array_cat_image = new ArrayList<>();
                array_cat_image_selected = new ArrayList<>();
                array_cat_id = new ArrayList<>();

                String code = array.getString(0);
                String message = array.getString(1);
                JSONArray internal = array.getJSONArray(2);
                for(int i=0;i<internal.length();i++)
                {
                    JSONObject object = internal.getJSONObject(i);
                    hcp_cust_cat_id =object.getString("hcp_cust_cat_id");
                    hcp_cust_cat_name =object.getString("hcp_cust_cat_name");
                    hcp_cust_cat_image =object.getString("hcp_cust_cat_image");
                    hcp_cust_cat_image_selected =object.getString("hcp_cust_cat_image_selected");
                    hcp_cust_cat_order =object.getString("hcp_cust_cat_order");
                    hcp_cust_cat_status =object.getString("hcp_cust_cat_status");
                    hcp_cust_cat_deleted =object.getString("hcp_cust_cat_deleted");
                    hcp_cust_cat_add_date =object.getString("hcp_cust_cat_add_date");

                    array_cat_name.add(hcp_cust_cat_name);
                    array_cat_image.add(hcp_cust_cat_image);
                    array_cat_image_selected.add(hcp_cust_cat_image_selected);
                    array_cat_id.add(hcp_cust_cat_id);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void LoadCategories(String values)
    {
        //int val = Integer.parseInt(values);
        //Log.d("cat_if",array_cat_id.get(0));
        if(values.equals("1"))
        {
            Log.d("Coming","1");
            //Log.d("cat_if",array_cat_image_selected.get(0));
            cclinics=1;
            clinics.setImageDrawable(getResources().getDrawable(R.drawable.clinic_));
            //Picasso.with(con).load(array_cat_image_selected.get(0)).into(clinics);
        }
        //Log.d("cat_if",array_cat_id.get(1));
        if(values.equals("2"))
        {
            Log.d("Coming","2");
            //Log.d("cat_if",array_cat_image_selected.get(1));
            chospital=1;
            hospital.setImageDrawable(getResources().getDrawable(R.drawable.hospital_));
            //Picasso.with(con).load(array_cat_image_selected.get(1)).into(hospital);
        }
        //Log.d("cat_if",array_cat_id.get(2));
        if(values.equals("3"))
        {
            Log.d("Coming","3");
            //Log.d("cat_if",array_cat_image_selected.get(2));
            cpathlab=1;
            pathlab.setImageDrawable(getResources().getDrawable(R.drawable.pathlab_));
            //Picasso.with(con).load(array_cat_image_selected.get(2)).into(pathlab);
        }
        //Log.d("cat_if",array_cat_id.get(3));
        if(values.equals("4"))
        {
            Log.d("Coming","4");
            //Log.d("cat_if",array_cat_image_selected.get(3));
            cfitness=1;
            fitness.setImageDrawable(getResources().getDrawable(R.drawable.fitness_));
            //Picasso.with(con).load(array_cat_image_selected.get(3)).into(fitness);
        }
        //Log.d("cat_if",array_cat_id.get(4));
        if(values.equals("5"))
        {
            //Log.d("cat_if",array_cat_image_selected.get(4));
            Log.d("Coming","5");
            cbloodbanks=1;
            bloodbanks.setImageDrawable(getResources().getDrawable(R.drawable.bloodbank_));
            //Picasso.with(con).load(array_cat_image_selected.get(4)).into(bloodbanks);
        }
        //Log.d("cat_if",array_cat_id.get(5));
        if(values.equals("6"))
        {
            Log.d("Coming","6");
            //Log.d("cat_if",array_cat_image_selected.get(5));
            csalon=1;
            salon.setImageDrawable(getResources().getDrawable(R.drawable.salon_));
            //Picasso.with(con).load(array_cat_image_selected.get(5)).into(salon);
        }
        //Log.d("cat_if",array_cat_id.get(6));
        if(values.equals("7"))
        {
            Log.d("Coming","7");
            //Log.d("cat_if",array_cat_image_selected.get(6));
            cpharmacy=1;
            pharmacy.setImageDrawable(getResources().getDrawable(R.drawable.pharmacy_));
            //Picasso.with(con).load(array_cat_image_selected.get(6)).into(pharmacy);
        }
        //Log.d("cat_if",array_cat_id.get(7));
        if(values.equals("8"))
        {
            Log.d("Coming","8");
            //Log.d("cat_if",array_cat_image_selected.get(7));
            cdoctor=1;
            doctor.setImageDrawable(getResources().getDrawable(R.drawable.doctor_));
            //Picasso.with(con).load(array_cat_image_selected.get(7)).into(doctor);
        }
        //Log.d("cat_if",array_cat_id.get(8));
        if(values.equals("9"))
        {
            Log.d("Coming","9");
            //Log.d("cat_if",array_cat_image_selected.get(8));
            cspa=1;
            spa.setImageDrawable(getResources().getDrawable(R.drawable.spa_));
            //Picasso.with(con).load(array_cat_image_selected.get(8)).into(spa);
        }
    }

    public void validateRegister(){
        strInterest = "";
        strInterest = getInterest();
        //Toast.makeText(con, ""+getInterest(), Toast.LENGTH_SHORT).show();
        if(strInterest.equals("Please select one interest to proceed"))
        {
            url_dump.Toastthis(strInterest,con);
        }
        else {
            new UserRegister().execute();
        }
    }
    public class UserRegister extends AsyncTask<Void,Void,Void> {
        String json;
        String str_Code;
        String str_Message;
        String str_UserID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url_dump.startprogress("Fetching Data","Please wait",con,false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json = url_dump.updatePreference(user_id,strInterest);
            } catch (Exception e) {
                url_dump.dismissprogress();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            url_dump.dismissprogress();
            try{
                if(!json.equals(""))
                {
                    JSONArray object = new JSONArray(json);
                    str_Code = object.get(0).toString();
                    str_Message = object.get(1).toString();
                    str_UserID = object.get(2).toString();
                    Log.d("Code",str_Code);
                    Log.d("Mesg",str_Message);
                    Log.d("UsID",str_UserID);
                    JSONObject object1 = object.getJSONObject(3);
                    if(str_Code.equals("HCPC1000"))
                    {
                        //Successfull
                        prefs.edit().putString("cust_interests",object1.getString("hcp_cust_interests")).apply();
                        onBackPressed();
                    }
                    else if(str_Code.equals("HCPC1001"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,con);
                    }
                    else
                    {
                        Toastthis(str_Message,con);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public String getInterest()
    {
        StringBuilder tmp = new StringBuilder();

        int anyone=0;
        if(cclinics==1) {
            //tmp.append("Clinics, ");
            //tmp.append(array_cat_id.get(0)+", ");
            tmp.append("1, ");
        }
        if(chospital==1) {
            //tmp.append("Hospitals, ");
            //tmp.append(array_cat_id.get(1)+", ");
            tmp.append("2, ");
        }
        if(cpathlab==1) {
            //tmp.append("Pathlabs, ");
            //tmp.append(array_cat_id.get(2)+", ");
            tmp.append("3, ");
        }
        if(cfitness==1){
            //tmp.append("Fitness, ");
            //tmp.append(array_cat_id.get(3)+", ");
            tmp.append("4, ");
        }
        if(cbloodbanks==1){
            //tmp.append("Blood Banks, ");
            //tmp.append(array_cat_id.get(4)+", ");
            tmp.append("5, ");
        }
        if(csalon==1){
            //tmp.append("Salon, ");
            //tmp.append(array_cat_id.get(5)+", ");
            tmp.append("6, ");
        }
        if(cpharmacy==1){
            //tmp.append("Pharmacy, ");
            //tmp.append(array_cat_id.get(6)+", ");
            tmp.append("7, ");
        }
        if(cdoctor==1){
            //tmp.append("Doctors, ");
            //tmp.append(array_cat_id.get(7)+", ");
            tmp.append("8, ");
        }
        if(cspa==1) {
            //tmp.append("Spa");
            //tmp.append(array_cat_id.get(8)+", ");
            tmp.append("9, ");
        }
        if((cclinics==0)&&(chospital==0)&&(cpathlab==0)&&(cfitness==0)&&(cbloodbanks==0)&&(csalon==0)&&(cpharmacy==0)&&(cdoctor==0)&&(cspa==0))
        {
            anyone=0;
        }
        else{
            anyone=1;
        }
        if(anyone==0)
        {
            return "Please select one interest to proceed";
        }
        else{
            return tmp.toString();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
}
