package com.sriyaan.qureco;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.sriyaan.util.url_dump.Toastthis;

public class EditProfile extends AppCompatActivity {
    Toolbar toolbar;
    Context con;

    ImageView opengallery,person;
    LinearLayout location;
    SharedPreferences prefs;
    Button btnSave;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText etName,etMobile,etDob;
    RadioGroup gender;
    RadioButton male,female;

    String strName="",strMobile="",strDob="",strReferral="",strGender="",strInterest="";
    public static String str_lat,str_lon;
    int REQUEST_CAMERA = 0, FILE_SELECT_CODE = 1;
    private String userChoosenTask;
    int page = 0;

    String path = "",selectedImagePath;
    String user_id,user_name,mobile_no,profile_pic,sgender,sdob,referral_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();

        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
        setTitle("Edit Profile");

        user_id = prefs.getString("cust_id","");
        user_name = prefs.getString("cust_name","");
        mobile_no = prefs.getString("cust_mobile_no","");
        profile_pic = prefs.getString("cust_profile_pic","");
        sgender = prefs.getString("cust_gender","");
        sdob = prefs.getString("cust_dob","");
        referral_code = prefs.getString("cust_referral_code","");
        str_lat = prefs.getString("cust_map_lat","");
        str_lon = prefs.getString("cust_map_long","");

        etName.setText(user_name);
        etMobile.setText(mobile_no);
        etDob.setText(sdob);
        if(sgender.equalsIgnoreCase("male"))
        {
            gender.check(R.id.male);
        }
        else {
            gender.check(R.id.male);
        }

        Picasso.with(con).load(profile_pic).into(person);
        //user_name
        opengallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(con,MapsActivity1.class);
                i.putExtra("lat",str_lat);
                i.putExtra("lon",str_lon);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateNext();
            }
        });
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
        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPicker();
            }
        });
        etDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    showPicker();
                }
            }
        });
    }
    public void validateNext()
    {
        strName = etName.getText().toString();
        strMobile = etMobile.getText().toString();
        strDob = etDob.getText().toString();
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
        else if(strMobile.equals(""))
        {
            url_dump.Toastthis("Please enter mobile number to proceed",con);
        }
        else if(strDob.equals(""))
        {
            url_dump.Toastthis("Please select date of birth to proceed",con);
        }
        else{
            new UserRegister().execute();
        }

    }
    public void init(){
        con         = EditProfile.this;
        toolbar     = (Toolbar)         findViewById(R.id.toolbar);
        opengallery         = (ImageView)   findViewById(R.id.opengallery);
        person              = (ImageView)   findViewById(R.id.person);

        btnSave         = (Button)      findViewById(R.id.btnSave);
        etDob           = (EditText)    findViewById(R.id.dob);
        gender          = (RadioGroup)  findViewById(R.id.group);
        male            = (RadioButton) findViewById(R.id.male);
        female          = (RadioButton) findViewById(R.id.female);
        etName          = (EditText)    findViewById(R.id.name);
        etMobile        = (EditText)    findViewById(R.id.Mobile);
        location        = (LinearLayout)findViewById(R.id.location);

        myCalendar      = Calendar.getInstance();
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

        etDob.setText(sdf.format(myCalendar.getTime()));
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(con);
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
                        Toast.makeText(con, "Please install a File Manager.",
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
        Picasso.with(con).load(uri).resize(96, 96).centerCrop().into(person);
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
                prefs.edit().putString("mobile",strMobile).apply();
                json = url_dump.updateProfile(user_id,strName,strDob,profile_pic,str_lat,str_lon,path);
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

                    if(str_Code.equals("HCPC900"))
                    {
                        //Successfull
                        onBackPressed();
                    }
                    else if(str_Code.equals("HCPC901"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,con);
                    }
                    else
                    {
                        //User Already Registered
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
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
}
