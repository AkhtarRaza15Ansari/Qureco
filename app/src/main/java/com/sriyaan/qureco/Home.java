package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.sriyaan.util.FragmentDrawer;
import com.sriyaan.util.url_dump;

import static com.sriyaan.util.url_dump.Logthis;

public class Home extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    Toolbar toolbar;
    Context con;
    private FragmentDrawer drawerFragment;
    LinearLayout category,right_ll,left_ll;
    LinearLayout doctor,clinics,pathlab,fitness,salon,spa,pharmacy,hospital,bloodbanks;
    int num=0;
    ImageView img_profile;
    SharedPreferences prefs;
    String cust_id,cust_name,cust_mobile_no,cust_profile_pic;

    LinearLayout llloyalty,lldeals,llreview,lllife;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
    LinearLayout alertfamily, shoutouthelp,emergency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);

        cust_id = prefs.getString("cust_id","");
        cust_name = prefs.getString("cust_name","");
        cust_mobile_no = prefs.getString("cust_mobile_no","");
        cust_profile_pic = prefs.getString("cust_profile_pic","");
        Logthis("cust_profile_pic",cust_profile_pic+"A");

        Log.d("Ansari","Akhtar");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        setTitle("Home");

        loadScreen(num);
        right_ll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(num<2){
                    num++;
                    loadScreen(num);
                }
            }
        });
        left_ll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(num>0){
                    num--;
                    loadScreen(num);
                }
            }
        });

        img_profile = (ImageView)   findViewById(R.id.img_profile);
        if(!cust_profile_pic.isEmpty())
        Picasso.with(con).load(cust_profile_pic)
                .transform(new CircleTransform()).placeholder(R.drawable.person).into(img_profile);



        llloyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Loyalty.class);
                startActivity(i);
            }
        });
        lldeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,DealsOffers.class);
                startActivity(i);
            }
        });
        llreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,ReviewPage.class);
                startActivity(i);
            }
        });
        lllife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,LifeSavers.class);
                startActivity(i);
            }
        });
        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,MyAccount.class);
                startActivity(i);
            }
        });
        alertfamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,AlertFamily.class);
                startActivity(i);
            }
        });
        shoutouthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,ShoutOutHelp.class);
                startActivity(i);
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Emergency.class);
                startActivity(i);
            }
        });
    }
    public void init(){
        con         = Home.this;
        toolbar     = (Toolbar)         findViewById(R.id.toolbar);
        category    = (LinearLayout)    findViewById(R.id.category);
        right_ll    = (LinearLayout)    findViewById(R.id.right_ll);
        left_ll     = (LinearLayout)    findViewById(R.id.left_ll);



        llloyalty       = (LinearLayout)    findViewById(R.id.loyaltyll);
        lldeals         = (LinearLayout)    findViewById(R.id.dealsll);
        llreview        = (LinearLayout)    findViewById(R.id.reviewll);
        lllife          = (LinearLayout)    findViewById(R.id.lifesaversll);
        llhome          = (LinearLayout)    findViewById(R.id.homell);
        llnotification  = (LinearLayout)    findViewById(R.id.notificationll);
        llchat          = (LinearLayout)    findViewById(R.id.chatll);
        llfavorites     = (LinearLayout)    findViewById(R.id.favouritesll);
        llacounts       = (LinearLayout)    findViewById(R.id.accountsll);
        alertfamily     = (LinearLayout)    findViewById(R.id.alertfamily);
        shoutouthelp    = (LinearLayout)    findViewById(R.id.shoutouthelp);
        emergency       = (LinearLayout)    findViewById(R.id.emergency);

    }

    @Override
    public void onDrawerItemSelected(View view, int position){
        if(position==0)
        {
            //My Accounts
            Intent i = new Intent(Home.this,MyAccount.class);
            startActivity(i);
        }
        else if(position==1)
        {
            //Settings
            Intent i = new Intent(Home.this,SettingsPage.class);
            startActivity(i);
        }else if(position==2)
        {
            //About Us
            Intent i = new Intent(Home.this,AboutUsPage.class);
            startActivity(i);
        }else if(position==3)
        {
            //Rate Us
        }else if(position==4)
        {
            //Feedback
            Intent i = new Intent(Home.this,Feedback.class);
            startActivity(i);
        }else if(position==5)
        {
            //Terms & Conditions
            Intent i = new Intent(Home.this,TermsConditionPage.class);
            startActivity(i);
        }else if(position==6)
        {
            //Contact Us
            Intent i = new Intent(Home.this,ContactUs.class);
            startActivity(i);
        }else if(position==7)
        {
            //Logout
            prefs.edit().clear().apply();
            Intent i = new Intent(Home.this,MainActivity.class);
            i.putExtra("name","login");
            startActivity(i);
            finish();
        }

    }
    public void loadScreen(int number){
        category.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        View cat1 = inflater.inflate(R.layout.category1, null, false);
        View cat2 = inflater.inflate(R.layout.category2, null, false);
        View cat3 = inflater.inflate(R.layout.category3, null, false);
        if(number==0)
        {
            category.addView(cat1);
            doctor      = (LinearLayout)findViewById(R.id.doctor);
            clinics     = (LinearLayout)findViewById(R.id.clinics);
            pathlab     = (LinearLayout)findViewById(R.id.pathlab);
            doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Doctor");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
            clinics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Clinics");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
            pathlab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Pathlab");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
        }
        else if(number==1)
        {
            category.addView(cat2);
            fitness     = (LinearLayout)findViewById(R.id.fitness);
            salon       = (LinearLayout)findViewById(R.id.salon);
            spa         = (LinearLayout)findViewById(R.id.spa);
            fitness.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Logthis("Home","Fitness");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
            salon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Logthis("Home","Salon");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
            spa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Spa");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
        }
        else if(number==2)
        {
            category.addView(cat3);
            pharmacy    = (LinearLayout)findViewById(R.id.pharmacy);
            hospital    = (LinearLayout)findViewById(R.id.hospital);
            bloodbanks  = (LinearLayout)findViewById(R.id.bloodbanks);
            pharmacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Pharmacy");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
            hospital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Hospital");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
            bloodbanks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Blood Banks");
                    Intent i = new Intent(Home.this,SearchListPage.class);
                    startActivity(i);
                }
            });
        }
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size/2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
}
