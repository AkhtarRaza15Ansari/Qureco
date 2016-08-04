package com.sriyaan.qureco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class CompleteRegister extends AppCompatActivity {
    ImageView clinics,hospital,pathlab,fitness,bloodbanks,salon,pharmacy,doctor,spa;
    Button btnRegister;
    Toolbar toolbar;
    int cclinics,chospital,cpathlab,cfitness,cbloodbanks,csalon,cpharmacy,cdoctor,cspa;
    Animation flipin,flipout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);

        init();

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

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Register");
        getSupportActionBar().setIcon(R.drawable.logo);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompleteRegister.this,Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
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
    public void init()
    {
        toolbar         = (Toolbar)     findViewById(R.id.toolbar);
        clinics         = (ImageView)   findViewById(R.id.clinics);
        hospital        = (ImageView)   findViewById(R.id.hospital);
        pathlab         = (ImageView)   findViewById(R.id.pathlab);
        fitness         = (ImageView)   findViewById(R.id.fitness);
        bloodbanks      = (ImageView)   findViewById(R.id.bloodbanks);
        salon           = (ImageView)   findViewById(R.id.salon);
        pharmacy        = (ImageView)   findViewById(R.id.pharmacy);
        doctor          = (ImageView)   findViewById(R.id.doctor);
        spa             = (ImageView)   findViewById(R.id.spa);
        btnRegister     = (Button)      findViewById(R.id.btnRegister);
    }
}
