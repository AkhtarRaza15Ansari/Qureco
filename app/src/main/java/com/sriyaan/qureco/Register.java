package com.sriyaan.qureco;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sriyaan.util.url_dump;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register extends AppCompatActivity {
    Button btnRegister;
    EditText dob;
    Calendar myCalendar;
    Context con;
    Toolbar toolbar;
    EditText etName,etMobile,etDob,etReferral;
    RadioGroup gender;
    RadioButton male,female;
    String strName,strMobile,strDob,strReferral,strGender;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        con = Register.this;
        init();
        setSupportActionBar(toolbar);
        male.setChecked(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
        setTitle("Register");

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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void validateNext()
    {
        strName = etName.getText().toString();
        strMobile = etMobile.getText().toString();
        strDob = etDob.getText().toString();
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
            Intent i = new Intent(Register.this,CompleteRegister.class);
            i.putExtra("name",strName);
            i.putExtra("mobile",strMobile);
            i.putExtra("referral",strReferral);
            i.putExtra("dob",strDob);
            i.putExtra("gender",strGender);
            startActivity(i);
        }

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
    public void init()
    {
        toolbar         = (Toolbar)     findViewById(R.id.toolbar);
        btnRegister     = (Button)      findViewById(R.id.btnRegister);
        dob             = (EditText)    findViewById(R.id.dob);
        gender          = (RadioGroup)  findViewById(R.id.group);
        male            = (RadioButton) findViewById(R.id.male);
        female          = (RadioButton) findViewById(R.id.female);
        myCalendar      = Calendar.getInstance();
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
}
