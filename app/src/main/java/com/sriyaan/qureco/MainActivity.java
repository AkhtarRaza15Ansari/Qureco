package com.sriyaan.qureco;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.sriyaan.util.url_dump.Toastthis;
import static com.sriyaan.util.url_dump.removeLastChar;

public class MainActivity extends AppCompatActivity {
    Fragment fragment = null;
    Toolbar toolbar;
    Context con;
    LayoutInflater inflater;
    LinearLayout my_root;
    String bitmap = null;

    ArrayList<String> array_cat_name,array_cat_image,array_cat_image_selected,
            array_cat_id ;
    String response="";
    String hcp_cust_cat_id,hcp_cust_cat_name,hcp_cust_cat_image,hcp_cust_cat_image_selected,
            hcp_cust_cat_order,hcp_cust_cat_status,hcp_cust_cat_deleted,hcp_cust_cat_add_date;

    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    private GestureDetector mGestureDetector;
    Button btnLogin;
    EditText input_phone;
    String str_mobile_login;
    TextView tvSignUp,tvForgotPassword;

    Button btnRegister;
    TextView skip,skip1;
    TextView donatebloodsave,doyouwant,tvbloodgroup,caution;
    EditText dob;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText etName,etMobile,etBloodGroup,etReferral;
    RadioGroup gender,donategroup;
    RadioButton male,female,yes,no;

    ImageView clinics,hospital,pathlab,fitness,bloodbanks,salon,pharmacy,doctor,spa;
    TextView tvselectPreference,tvdoctor,tvclinics,tvpathlab,tvfitness,tvsalon,tvspa,tvpharmacy,tvhospital
            ,tvbloodbank,selectlocation;
    Button btnCompleteRegister;
    int cclinics,chospital,cpathlab,cfitness,cbloodbanks,csalon,cpharmacy,cdoctor,cspa;
    Animation flipin,flipout;
    LinearLayout location;
    ImageView opengallery,person;
    TextView tvName,clickheretoselect;
    String strName="",strMobile="",strDob="",strReferral="",strGender="",strInterest="",lifesaver="",bloodgroup="";
    public static String str_lat="",str_lon="";
    int REQUEST_CAMERA = 0, FILE_SELECT_CODE = 1;
    private String userChoosenTask;
    int page = 0;
    TextView mTitle;
    String path = "",selectedImagePath;
    SharedPreferences prefs;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        toolbar     = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(tf);
        mTitle.setText("");

        my_root = (LinearLayout) findViewById(R.id.body);
        inflater = LayoutInflater.from(this);
        setUiPageViewController(3);
        if(getIntent().getStringExtra("name").equals("Register"))
        {
            loadLayout("register");
        }
        else {
            loadLayout("login");
        }

    }

    public void initLogin(){
        input_phone = (EditText)findViewById(R.id.input_phone);
        tvSignUp    = (TextView)findViewById(R.id.tvSignUp);
        btnLogin    = (Button)  findViewById(R.id.btnLogin);
    }
    public void initRegister()
    {
        btnRegister     = (Button)      findViewById(R.id.btnRegister);
        dob             = (EditText)    findViewById(R.id.dob);
        skip            = (TextView)    findViewById(R.id.skip);
        gender          = (RadioGroup)  findViewById(R.id.group);
        male            = (RadioButton) findViewById(R.id.male);
        female          = (RadioButton) findViewById(R.id.female);
        etName          = (EditText)    findViewById(R.id.name);
        etMobile        = (EditText)    findViewById(R.id.Mobile);
        etReferral      = (EditText)    findViewById(R.id.promocode);
        etBloodGroup    = (EditText)    findViewById(R.id.bloodGroup);
        donategroup     = (RadioGroup)  findViewById(R.id.donategroup);
        yes             = (RadioButton) findViewById(R.id.yes);
        no              = (RadioButton) findViewById(R.id.no);
        donatebloodsave = (TextView)    findViewById(R.id.donatebloodsave);
        doyouwant       = (TextView)    findViewById(R.id.doyouwant);
        tvbloodgroup    = (TextView)    findViewById(R.id.tvbloodgroup);
        caution         = (TextView)    findViewById(R.id.caution);
        myCalendar      = Calendar.getInstance();

        male.setChecked(true);
    }
    public void initCompleteRegister()
    {
        clinics             = (ImageView)   findViewById(R.id.clinics);
        hospital            = (ImageView)   findViewById(R.id.hospital);
        pathlab             = (ImageView)   findViewById(R.id.pathlab);
        fitness             = (ImageView)   findViewById(R.id.fitness);
        bloodbanks          = (ImageView)   findViewById(R.id.bloodbanks);
        salon               = (ImageView)   findViewById(R.id.salon);
        pharmacy            = (ImageView)   findViewById(R.id.pharmacy);
        doctor              = (ImageView)   findViewById(R.id.doctor);
        spa                 = (ImageView)   findViewById(R.id.spa);
        btnCompleteRegister = (Button)      findViewById(R.id.btnRegister);
        skip1               = (TextView)    findViewById(R.id.skip);
        location            = (LinearLayout)findViewById(R.id.location);
        opengallery         = (ImageView)   findViewById(R.id.opengallery);
        person              = (ImageView)   findViewById(R.id.person);
        tvName              = (TextView)    findViewById(R.id.name);
        clickheretoselect   = (TextView)    findViewById(R.id.clickheretoselect);

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
        selectlocation      = (TextView)    findViewById(R.id.selectlocation);
    }
    public void loadLayout(String body)
    {
        if(body.equals("register"))
        {
            page = 1;
            setTitle("");
            mTitle.setText("REGISTRATION");
            my_root.removeAllViews();
            View inflatedLayout = inflater.inflate(R.layout.activity_register, null, false);
            my_root.addView(inflatedLayout);
            initRegister();

            setFontRegister();
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validateNext();
                }
            });
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadLayout("login");
                }
            });
            setPositionDots(0);

            date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };
            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPicker();
                }
            });
            dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b)
                    {
                        showPicker();
                    }
                }
            });
        }
        else if(body.equals("completeregister")){

            page = 2;
            setTitle("");
            mTitle.setText("COMPLETE REGISTRATION");
            my_root.removeAllViews();
            View inflatedLayout = inflater.inflate(R.layout.activity_complete_register, null, false);
            my_root.addView(inflatedLayout);
            initCompleteRegister();

            setFontComplete();
            tvName.setText(strName);
            //new GetCategories().execute();
            opengallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImage();
                }
            });
            btnCompleteRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //loadLayout("login");
                    validateRegister();
                }
            });
            setPositionDots(1);

            skip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadLayout("login");
                }
            });
            flipin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_in);
            flipout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_out);
            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i =new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(i);
                }
            });
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
        else if(body.equals("login"))
        {
            page = 3;

            setTitle("");
            mTitle.setText("LOGIN");

            my_root.removeAllViews();
            View inflatedLayout = inflater.inflate(R.layout.activity_login, null, false);
            my_root.addView(inflatedLayout);
            initLogin();

            setFontLogin();

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    str_mobile_login = input_phone.getText().toString();
                    if(!str_mobile_login.equals(""))
                    {
                        new UserLogin().execute();
                    }
                    /*Intent i = new Intent(MainActivity.this,Home.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();*/

                }
            });
            tvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadLayout("register");
                }
            });
            setPositionDots(2);
        }
    }
    public void setPositionDots(int count)
    {
        setUiPageViewController(3);
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
        }
        dots[count].setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    private void setUiPageViewController(int count) {
        dotsLayout = (LinearLayout) findViewById(R.id.footer);
        dots = new TextView[count];
        dotsLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsLayout.addView(dots[i]);
        }
        //dots[0].setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    public void showPicker()
    {
        new DatePickerDialog(con, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void updateLabel() {

        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case url_dump.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }*/

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add File");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult(
                                Intent.createChooser(intent, "Select a File to Upload"),
                                FILE_SELECT_CODE);
                    } catch (android.content.ActivityNotFoundException ex) {
                        // Potentially direct the user to the Market with a Dialog
                        Toast.makeText(MainActivity.this, "Please install a File Manager.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FILE_SELECT_CODE)
            {
                Uri uri = data.getData();
                Log.d("TAG", "File Uri: " + uri.toString());
                // Get the path

                path = getPath(this, uri);
                Log.d("TAG", "File Path: " + path);
                String filename = path.substring(path.lastIndexOf("/") + 1);
                PlaceImage();
                //new PostNotification().execute();
            }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    public void PlaceImage(){
        Uri uri = Uri.fromFile(new File(path));
        Picasso.with(MainActivity.this).load(uri).resize(96, 96).centerCrop().into(person);
    }
    public String getImagePath() {
        return imgPath;
    }
    private void onCaptureImageResult(Intent data) {
        selectedImagePath = getImagePath();
        File destination = new File(selectedImagePath);
        path = destination.getAbsolutePath();
        Log.d("path",path);
        PlaceImage();
        //new PostNotification().execute();
    }
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private String imgPath;
    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }


    public void validateNext()
    {
        strName = etName.getText().toString();
        strMobile = etMobile.getText().toString();
        strDob = dob.getText().toString();
        bloodgroup = etBloodGroup.getText().toString();

        int lifesaverId = gender.getCheckedRadioButtonId();
        if(lifesaverId == yes.getId())
        {
            lifesaver = "Yes";
        }
        else if(lifesaverId == no.getId())
        {
            lifesaver = "No";
        }

        int selectedId = gender.getCheckedRadioButtonId();
        if(selectedId == male.getId())
        {
            strGender = "Male";
        }
        else if(selectedId == female.getId())
        {
            strGender = "Female";
        }

        if(strName.equals(""))
        {
            url_dump.Toastthis("Please enter name to proceed",con);
        }
        else if(strMobile.equals("") && (strMobile.length()<10))
        {
            url_dump.Toastthis("Please enter mobile number to proceed",con);
        }
        else if(strDob.equals(""))
        {
            url_dump.Toastthis("Please select date of birth to proceed",con);
        }
        else{
            loadLayout("completeregister");
        }

    }

    public void validateRegister(){
        strInterest = "";
        strInterest = getInterest();
        //Toast.makeText(MainActivity.this, ""+getInterest(), Toast.LENGTH_SHORT).show();
        if(strInterest.equals("Please select one interest to proceed"))
        {
            url_dump.Toastthis(strInterest,con);
        }
        else if(str_lat.equals("")){
            url_dump.Toastthis("Please select your location",con);
        }
        else if(str_lon.equals("")){
            url_dump.Toastthis("Please select your location",con);
        }
        else {
            new UserRegister().execute();
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
    public class UserRegister extends AsyncTask<Void,Void,Void> {
        String json;
        String str_Code;
        String str_Message;
        String str_UserID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url_dump.startprogress("Fetching Data","Please wait",MainActivity.this,false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                prefs.edit().putString("mobile",strMobile).apply();
                strInterest = removeLastChar(strInterest);
                json = url_dump.doFileUpload(strName,strMobile,strGender,strDob,strReferral,strInterest,str_lat,str_lon,path,lifesaver,bloodgroup);
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

                    if(str_Code.equals("HCPC200"))
                    {
                        //Successfull
                        prefs.edit().putString("type","register").apply();
                        Intent i = new Intent(con,SmsReciever.class);
                        startActivity(i);
                        finish();
                    }
                    else if(str_Code.equals("HCPC201"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,con);
                    }
                    else if(str_Code.equals("HCPC202"))
                    {
                        //User Already Registered
                        Toastthis(str_Message,con);
                        loadLayout("login");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public class UserLogin extends AsyncTask<Void,Void,Void> {
        String json;
        String str_Code,str_Message,str_UserID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url_dump.startprogress("Fetching Data","Please wait",MainActivity.this,false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                prefs.edit().putString("mobile",str_mobile_login).apply();
                json = url_dump.LoginUser(str_mobile_login);
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

                    if(str_Code.equals("HCPC400"))
                    {
                        //Successfull
                        prefs.edit().putString("type","login").apply();
                        Intent i = new Intent(con,SmsReciever.class);
                        i.putExtra("str_mobile_login",str_mobile_login);
                        startActivity(i);
                        finish();
                    }
                    else if(str_Code.equals("HCPC401"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,con);
                    }
                    else if(str_Code.equals("HCP402"))
                    {
                        //User Already Registered
                        Toastthis(str_Message,con);
                        loadLayout("login");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
    @Override
    public void onResume()
    {
        super.onResume();
        try {
            if (!str_lat.equals("")) {
                clickheretoselect.setText("Your location is:(" + str_lat + "," + str_lon + ")");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void setFontLogin() {

        tvSignUp.setTypeface(tf);
        input_phone.setTypeface(tf);
        input_phone.setHint("Mobile Number");
    }
    public void setFontRegister() {
        skip            .setTypeface(tf);
        donatebloodsave .setTypeface(tf);
        doyouwant       .setTypeface(tf);
        tvbloodgroup    .setTypeface(tf);
        caution         .setTypeface(tf);
        btnRegister     .setTypeface(tf);
        dob             .setTypeface(tf);
        male            .setTypeface(tf);
        female          .setTypeface(tf);
        etName          .setTypeface(tf);
        etMobile        .setTypeface(tf);
        etReferral      .setTypeface(tf);
        etBloodGroup    .setTypeface(tf);

        etName          .setHint("User name");
        etMobile        .setHint("Mobile Number");
        etReferral      .setHint("Referral Code");
        etBloodGroup    .setHint("Enter Here");
        dob             .setHint("Date of birth");

        yes             .setTypeface(tf);
        no              .setTypeface(tf);
    }
    public void setFontComplete() {
        tvselectPreference.setTypeface(tf);
        tvdoctor.setTypeface(tf);
        tvclinics.setTypeface(tf);
        tvpathlab.setTypeface(tf);
        tvfitness.setTypeface(tf);
        tvsalon.setTypeface(tf);
        tvspa.setTypeface(tf);
        tvpharmacy.setTypeface(tf);
        tvhospital.setTypeface(tf);
        tvbloodbank.setTypeface(tf);
        selectlocation.setTypeface(tf);
        tvName.setTypeface(tf);
        clickheretoselect.setTypeface(tf);
        btnCompleteRegister.setTypeface(tf);
    }
}
