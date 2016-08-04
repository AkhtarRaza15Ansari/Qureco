package com.sriyaan.qureco;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Fragment fragment = null;
    Toolbar toolbar;
    Context con;
    LayoutInflater inflater;
    LinearLayout my_root;

    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    private GestureDetector mGestureDetector;
    Button btnLogin;
    TextView tvSignUp,tvForgotPassword;

    Button btnRegister;
    TextView skip,skip1;
    EditText dob;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    ImageView clinics,hospital,pathlab,fitness,bloodbanks,salon,pharmacy,doctor,spa;
    Button btnCompleteRegister;
    int cclinics,chospital,cpathlab,cfitness,cbloodbanks,csalon,cpharmacy,cdoctor,cspa;
    Animation flipin,flipout;
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
        tvSignUp    = (TextView)findViewById(R.id.tvSignUp);
        btnLogin    = (Button)  findViewById(R.id.btnLogin);
    }
    public void initRegister()
    {
        btnRegister     = (Button)  findViewById(R.id.btnRegister);
        dob             = (EditText)findViewById(R.id.dob);
        skip            = (TextView)  findViewById(R.id.skip);
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
                    loadLayout("completeregister");
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
            btnCompleteRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadLayout("login");
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
                    Intent i = new Intent(MainActivity.this,Home.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
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
}
