package com.sriyaan.qureco;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
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

import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.sriyaan.util.url_dump.DeviceRegistration;
import static com.sriyaan.util.url_dump.SplashTimer;
import static com.sriyaan.util.url_dump.Toastthis;

public class MainActivity extends AppCompatActivity {
    Fragment fragment = null;
    Toolbar toolbar;
    Context con;
    LayoutInflater inflater;
    LinearLayout my_root;
    String bitmap = null;

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
    EditText dob;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText etName,etMobile,etDob,etReferral;
    RadioGroup gender;
    RadioButton male,female;


    ImageView clinics,hospital,pathlab,fitness,bloodbanks,salon,pharmacy,doctor,spa;
    Button btnCompleteRegister;
    int cclinics,chospital,cpathlab,cfitness,cbloodbanks,csalon,cpharmacy,cdoctor,cspa;
    Animation flipin,flipout;
    EditText etNationality,etCity;
    ImageView opengallery,person;
    String strName,strMobile,strDob,strReferral,strGender,strInterest,strImageName,strNationality,strCity;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = MainActivity.this;
        toolbar     = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");

        my_root = (LinearLayout) findViewById(R.id.body);
        inflater = LayoutInflater.from(this);
        setUiPageViewController(3);

        loadLayout("register");
    }

    public void initLogin(){
        input_phone = (EditText)findViewById(R.id.input_phone);
        tvSignUp    = (TextView)findViewById(R.id.tvSignUp);
        btnLogin    = (Button)  findViewById(R.id.btnLogin);
    }
    public void initRegister()
    {
        btnRegister     = (Button)  findViewById(R.id.btnRegister);
        dob             = (EditText)findViewById(R.id.dob);
        skip            = (TextView)  findViewById(R.id.skip);
        gender          = (RadioGroup)  findViewById(R.id.group);
        male            = (RadioButton) findViewById(R.id.male);
        female          = (RadioButton) findViewById(R.id.female);
        etName          = (EditText)    findViewById(R.id.name);
        etMobile        = (EditText)    findViewById(R.id.Mobile);
        etReferral      = (EditText)    findViewById(R.id.promocode);

        myCalendar      = Calendar.getInstance();
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
        skip1               = (TextView)  findViewById(R.id.skip);
        etNationality   = (EditText)        findViewById(R.id.nationality);
        etCity          = (EditText)        findViewById(R.id.city);
        opengallery     = (ImageView)       findViewById(R.id.opengallery);
        person          = (ImageView)       findViewById(R.id.person);
    }
    public void loadLayout(String body)
    {
        if(body.equals("register"))
        {
            page = 1;
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText("Register");
            my_root.removeAllViews();
            View inflatedLayout = inflater.inflate(R.layout.activity_register, null, false);
            my_root.addView(inflatedLayout);
            initRegister();
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
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText("Complete Register");
            my_root.removeAllViews();
            View inflatedLayout = inflater.inflate(R.layout.activity_complete_register, null, false);
            my_root.addView(inflatedLayout);
            initCompleteRegister();
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
                                clinics.setImageDrawable(getResources().getDrawable(R.drawable.icon_hospital_clicked));
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
                                clinics.setImageDrawable(getResources().getDrawable(R.drawable.icon_hospital));
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
                                hospital.setImageDrawable(getResources().getDrawable(R.drawable.icon_hospital_clicked));
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
                                hospital.setImageDrawable(getResources().getDrawable(R.drawable.icon_hospital));
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
                                pathlab.setImageDrawable(getResources().getDrawable(R.drawable.icon_pathlab_clicked));
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
                                pathlab.setImageDrawable(getResources().getDrawable(R.drawable.icon_pathlab));
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
                                fitness.setImageDrawable(getResources().getDrawable(R.drawable.icon_fitness_clicked));
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
                                fitness.setImageDrawable(getResources().getDrawable(R.drawable.icon_fittness));
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
                                bloodbanks.setImageDrawable(getResources().getDrawable(R.drawable.icon_bloodbank_clicked));
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
                                bloodbanks.setImageDrawable(getResources().getDrawable(R.drawable.icon_bloodbank));
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
                                salon.setImageDrawable(getResources().getDrawable(R.drawable.icon_salon_clicked));
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
                                salon.setImageDrawable(getResources().getDrawable(R.drawable.icon_salon));
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
                                pharmacy.setImageDrawable(getResources().getDrawable(R.drawable.icon_pharmacy_clicked));
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
                                pharmacy.setImageDrawable(getResources().getDrawable(R.drawable.icon_pharmacy));
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
                                doctor.setImageDrawable(getResources().getDrawable(R.drawable.icon_doctor_clicked));
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
                                doctor.setImageDrawable(getResources().getDrawable(R.drawable.icon_doctor));
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
                                spa.setImageDrawable(getResources().getDrawable(R.drawable.icon_spa_clicked));
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
                                spa.setImageDrawable(getResources().getDrawable(R.drawable.icon_spa));
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
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText("Login");

            my_root.removeAllViews();
            View inflatedLayout = inflater.inflate(R.layout.activity_login, null, false);
            my_root.addView(inflatedLayout);
            initLogin();
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    str_mobile_login = input_phone.getText().toString();
                    if(!str_mobile_login.equals(""))
                    {

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

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
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
    }

    private void selectImage() {
        Log.d("coming","1");
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=url_dump.checkPermission(MainActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    Log.d("coming","2");
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    Log.d("coming","3");
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Log.d("coming","5");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Log.d("coming","4");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        person.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        person.setImageBitmap(bm);
    }

    public void validateNext()
    {
        strName = etName.getText().toString();
        strMobile = etMobile.getText().toString();
        strDob = dob.getText().toString();
        strReferral = etReferral.getText().toString();
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
            url_dump.Toastthis("",con);
        }
        else if(strMobile.equals(""))
        {
            url_dump.Toastthis("",con);
        }
        else if(strReferral.equals(""))
        {
            url_dump.Toastthis("",con);
        }
        else if(strDob.equals(""))
        {
            url_dump.Toastthis("",con);
        }
        else{
            loadLayout("completeregister");
        }

    }

    public void validateRegister(){
        strInterest = "";
        strCity =  etCity.getText().toString();
        strNationality = etNationality.getText().toString();
        strInterest = getInterest();
        Toast.makeText(MainActivity.this, ""+getInterest(), Toast.LENGTH_SHORT).show();
        if(strInterest.equals("Please select one interest to proceed"))
        {
            url_dump.Toastthis(strInterest,con);
        }
        else if(strNationality.equals("")){
            url_dump.Toastthis("Please enter your nationality",con);
        }
        else if(strCity.equals("")){
            url_dump.Toastthis("Please enter your city",con);
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
            tmp.append("Clinics, ");
        }
        if(chospital==1) {
            tmp.append("Hospitals, ");
        }
        if(cpathlab==1) {
            tmp.append("Pathlabs, ");
        }
        if(cfitness==1){
            tmp.append("Fitness, ");
        }
        if(cbloodbanks==1){
            tmp.append("Blood Banks, ");
        }
        if(csalon==1){
            tmp.append("Salon, ");
        }
        if(cpharmacy==1){
            tmp.append("Pharmacy, ");
        }
        if(cdoctor==1){
            tmp.append("Doctors, ");
        }
        if(cspa==1) {
            tmp.append("Spa");
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
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json = url_dump.doFileUpload(strName,strMobile,strGender,strDob,strReferral,strInterest,strNationality,strCity,bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
                        Intent i = new Intent(con,SmsReciever.class);
                        startActivity(i);
                        finish();
                    }
                    else if(str_Code.equals("HCPC201"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,con);
                    }
                    else if(str_Code.equals("HCP202"))
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
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json = url_dump.LoginUser(strMobile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
                        Intent i = new Intent(con,SmsReciever.class);
                        startActivity(i);
                        finish();
                    }
                    else if(str_Code.equals("HCPC201"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,con);
                    }
                    else if(str_Code.equals("HCP202"))
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
}
