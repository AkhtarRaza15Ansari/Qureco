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
        setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Home");
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
                .transform(new CircleTransform()).into(img_profile);
    }
    public void init(){
        con         = Home.this;
        toolbar     = (Toolbar)         findViewById(R.id.toolbar);
        category    = (LinearLayout)    findViewById(R.id.category);
        right_ll    = (LinearLayout)    findViewById(R.id.right_ll);
        left_ll     = (LinearLayout)    findViewById(R.id.left_ll);
    }

    @Override
    public void onDrawerItemSelected(View view, int position){
        if(position==0)
        {
            //My Accounts
        }
        else if(position==1)
        {
            //Settings
        }else if(position==2)
        {
            //About Us
        }else if(position==3)
        {
            //Rate Us
        }else if(position==4)
        {
            //Feedback
        }else if(position==5)
        {
            //Terms & Conditions
        }else if(position==6)
        {
            //Contact Us
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
                }
            });
            clinics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Clinics");
                }
            });
            pathlab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Pathlab");
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
                }
            });
            salon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Logthis("Home","Salon");
                }
            });
            spa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Spa");
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
                }
            });
            hospital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Hospital");
                }
            });
            bloodbanks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Blood Banks");
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
}
