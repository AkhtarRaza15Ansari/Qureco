package com.sriyaan.qureco;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sriyaan.util.url_dump;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CompleteRegister extends AppCompatActivity {
    ImageView clinics,hospital,pathlab,fitness,bloodbanks,salon,pharmacy,doctor,spa;
    Button btnRegister;
    Toolbar toolbar;
    int cclinics,chospital,cpathlab,cfitness,cbloodbanks,csalon,cpharmacy,cdoctor,cspa;
    Animation flipin,flipout;
    EditText etNationality,etCity;
    ImageView opengallery,person;
    String strName,strMobile,strDob,strReferral,strGender,strInterest,strImageName,strNationality,strCity;
    Intent intent;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    private String selectedImagePath;

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
        opengallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CompleteRegister.this, "Select Image", Toast.LENGTH_SHORT).show();
                selectImage();
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
        toolbar         = (Toolbar)         findViewById(R.id.toolbar);
        clinics         = (ImageView)       findViewById(R.id.clinics);
        hospital        = (ImageView)       findViewById(R.id.hospital);
        pathlab         = (ImageView)       findViewById(R.id.pathlab);
        fitness         = (ImageView)       findViewById(R.id.fitness);
        bloodbanks      = (ImageView)       findViewById(R.id.bloodbanks);
        salon           = (ImageView)       findViewById(R.id.salon);
        pharmacy        = (ImageView)       findViewById(R.id.pharmacy);
        doctor          = (ImageView)       findViewById(R.id.doctor);
        spa             = (ImageView)       findViewById(R.id.spa);
        opengallery     = (ImageView)       findViewById(R.id.opengallery);
        person          = (ImageView)       findViewById(R.id.person);
        btnRegister     = (Button)          findViewById(R.id.btnRegister);
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
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(CompleteRegister.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=url_dump.checkPermission(CompleteRegister.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
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
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }

}
