package com.sriyaan.qureco;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.sriyaan.util.FragmentDrawer;
import com.sriyaan.util.url_dump;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.sriyaan.util.url_dump.Logthis;

public class Home extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    Toolbar toolbar;
    Context con;
    private FragmentDrawer drawerFragment;
    LinearLayout category,right_ll,left_ll;
    LinearLayout doctor,clinics,pathlab,fitness,salon,spa,pharmacy,hospital,bloodbanks;
    TextView tvdoctor,tvclinics,tvpathlab,tvfitness,tvsalon,tvspa,tvpharmacy,tvhospital,tvbloodbanks;
    int num=0;
    ImageView img_profile;
    SharedPreferences prefs;
    String cust_id,cust_name,cust_mobile_no,cust_profile_pic;
    String scat1="1";//clinics
    String scat2="2";//hospitals
    String scat3="3";//pathlab
    String scat4="4";//fitness
    String scat5="5";//bloodbanks
    String scat6="6";//salon
    String scat7="7";//pharmacy
    String scat8="8";//doctors
    String scat9="9";//spa
    LinearLayout llloyalty,lldeals,llreview,lllife;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
    LinearLayout alertfamily, shoutouthelp,emergency;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    TextView search,tvLoyalty,tvDeals,tvReview,tvlifesavers,tvAlert,tvShout,tvEmergency,
            tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    DrawerLayout drawerLayout;
    LinearLayout back;
    public static String city = "";
    public static double lat=0,lon=0;
    public static String s_lat,s_lon;
    public static boolean resume = false;
    GoogleApiClient mGoogleApiClient;
    LatLng latLng;
    TextView mTitle;
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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });


        setTitle("");
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(tf);
        mTitle.setText("Home");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Location.class);
                startActivity(i);
            }
        });

        setFont();
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
                i.putExtra("value","0");
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



        buildGoogleApiClient();

        mGoogleApiClient.connect();

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

        search          = (TextView)        findViewById(R.id.search);
        tvLoyalty       = (TextView)        findViewById(R.id.tvLoyalty);
        tvDeals         = (TextView)        findViewById(R.id.tvDeals);
        tvReview        = (TextView)        findViewById(R.id.tvReview);
        tvlifesavers    = (TextView)        findViewById(R.id.tvlifesavers);
        tvAlert         = (TextView)        findViewById(R.id.tvAlert);
        tvShout         = (TextView)        findViewById(R.id.tvShout);
        tvEmergency     = (TextView)        findViewById(R.id.tvEmergency);
        tvHome          = (TextView)        findViewById(R.id.tvHome);
        tvNotification  = (TextView)        findViewById(R.id.tvNotification);
        tvChat          = (TextView)        findViewById(R.id.tvChat);
        tvFavourites    = (TextView)        findViewById(R.id.tvFavourites);
        tvAccounts      = (TextView)        findViewById(R.id.tvAccounts);

    }
    public void setFont()
    {
        search          .setTypeface(tf);
        tvLoyalty       .setTypeface(tf);
        tvDeals         .setTypeface(tf);
        tvReview        .setTypeface(tf);
        tvlifesavers    .setTypeface(tf);
        tvAlert         .setTypeface(tf);
        tvShout         .setTypeface(tf);
        tvEmergency     .setTypeface(tf);
        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);;
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);
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
            /*Intent i = new Intent(Home.this,SettingsPage.class);
            startActivity(i);*/
            shareTextUrl();
        }else if(position==2)
        {
            //About Us
            Intent i = new Intent(Home.this,AboutUsPage.class);
            startActivity(i);
        }else if(position==3)
        {
            //Rate Us
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
            }
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
            Intent i = new Intent(Home.this,Splash.class);
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

            tvdoctor    = (TextView)    findViewById(R.id.tvdoctors);
            tvclinics   = (TextView)    findViewById(R.id.tvclinics);
            tvpathlab   = (TextView)    findViewById(R.id.tvpathlab);

            tvdoctor    .setTypeface(tf);
            tvclinics   .setTypeface(tf);
            tvpathlab   .setTypeface(tf);
            doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Doctor");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat8);
                        startActivity(i);
                    }
                }
            });
            clinics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Clinics");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat1);
                        startActivity(i);
                    }
                }
            });
            pathlab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Pathlab");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat3);
                        startActivity(i);
                    }
                }
            });
        }
        else if(number==1)
        {
            category.addView(cat2);
            fitness     = (LinearLayout)findViewById(R.id.fitness);
            salon       = (LinearLayout)findViewById(R.id.salon);
            spa         = (LinearLayout)findViewById(R.id.spa);

            tvfitness   = (TextView)    findViewById(R.id.tvfitness);
            tvsalon     = (TextView)    findViewById(R.id.tvsalon);
            tvspa       = (TextView)    findViewById(R.id.tvspa);

            tvfitness   .setTypeface(tf);
            tvsalon     .setTypeface(tf);
            tvspa       .setTypeface(tf);

            fitness.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Logthis("Home","Fitness");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat4);
                        startActivity(i);
                    }
                }
            });
            salon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Logthis("Home","Salon");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat6);
                        startActivity(i);
                    }
                }
            });
            spa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Spa");

                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat9);
                        startActivity(i);
                    }
                }
            });
        }
        else if(number==2)
        {
            category.addView(cat3);
            pharmacy    = (LinearLayout)findViewById(R.id.pharmacy);
            hospital    = (LinearLayout)findViewById(R.id.hospital);
            bloodbanks  = (LinearLayout)findViewById(R.id.bloodbanks);

            tvpharmacy  = (TextView)    findViewById(R.id.tvpharmacy);
            tvhospital  = (TextView)    findViewById(R.id.tvhospital);
            tvbloodbanks= (TextView)    findViewById(R.id.tvbloodbank);

            tvpharmacy  .setTypeface(tf);
            tvhospital  .setTypeface(tf);
            tvbloodbanks.setTypeface(tf);

            pharmacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Pharmacy");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat7);
                        startActivity(i);
                    }
                }
            });
            hospital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Hospital");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat2);
                        startActivity(i);
                    }
                }
            });
            bloodbanks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Logthis("Home","Blood Banks");
                    if(lat!=0)
                    {
                        Intent i = new Intent(Home.this,SearchListPage.class);
                        i.putExtra("value",""+scat5);
                        startActivity(i);
                    }
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

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "Share the app");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.akhtar.fan&hl=en");
        startActivity(Intent.createChooser(share, "Share text to..."));
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(resume)
        {
            cust_profile_pic = prefs.getString("cust_profile_pic","");
            Logthis("cust_profile_pic",cust_profile_pic+"A");
            if(!cust_profile_pic.isEmpty())
                Picasso.with(con).load(cust_profile_pic)
                        .transform(new CircleTransform()).placeholder(R.drawable.person).into(img_profile);
        }
        if(city!=null && !city.equals(""))
        {
            Log.d("Print here","resume");
            mTitle.setText(""+ city);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        //Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onConnected(Bundle bundle) {
        //Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        android.location.Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            Log.d("arg0", latLng.latitude + "-" + latLng.longitude);
            s_lat       = String.valueOf(latLng.latitude);
            s_lon       = String.valueOf(latLng.longitude);

            lat         = latLng.latitude;
            lon         = latLng.longitude;
            city        = getLocationName(lat,lon);

            Log.d("Print here","getting location"+ getLocationName(lat,lon));
            mTitle.setText(""+ city);
        }
        else{
            new AlertDialog.Builder(con)
                    .setTitle("Location Disabled")
                    .setMessage("Please switch on your location to proceed")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            onBackPressed();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    public String getLocationName(double lattitude, double longitude) {

        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {

            List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
                    10);

            for (Address adrs : addresses) {
                if (adrs != null) {

                    String city = adrs.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        System.out.println("city ::  " + cityName);
                    } else {
                    }
                    // // you should also try with addresses.get(0).toSring();

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;

    }
}
