package com.sriyaan.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by akhtarraza on 23/07/16.
 */
public class url_dump {

    public static void SplashTimer(final Context con, final Class class1)
    {
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    Intent i = null;
                    i = new Intent(con,class1);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    con.startActivity(i);
                }
            }
        };
        timer.start();

    }
}
