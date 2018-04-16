package com.example.vishnu.gridgame;


import android.app.ActionBar;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.transition.TransitionManager;

import android.Manifest.permission;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.id.message;
import static android.os.Build.ID;
import static com.example.vishnu.gridgame.R.id.val;


public class MainActivity extends AppCompatActivity {

    MediaPlayer theme;
    int sct=0;
Object vals;
    String numval="";
    model model;
private static final int REQUEST_DEVICE_PERMISSION = 7;
    String deviceId= "";
    long time1;
    long time2;
    MyDBHandler dbHandler;
    private DatabaseReference mFirebaseDatabaseReference;
  //  final TextView vall=(TextView)findViewById(R.id.val);
    int p1max=0;
    int p2max=0;
    int latestele[]= new int[50];
    int elecounter=0;
    int toremove[]= new int[2];
    Boolean isoff=Boolean.FALSE;

Boolean isp1=Boolean.TRUE;
String ss="";
    int savegame[] =new int[100];
    int undo[]= new int[100];
    int calledcreate;


    int p1score=0;
    int p2score=0;
public void increasescore(int player)
{
    if(player==1)
    {p1score++;
    p2score--;}
    else if(player==2)
        p2score++;
}
/*private void alert(){
    ActivityCompat.requestPermissions(this,new String[]{permission.READ_PHONE_STATE},REQUEST_DEVICE_PERMISSION);
    checkPermission();
}*/
    public static String getDeviceId(Context context){
        return Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }
   /* private void checkPermission(){
        int result1= ContextCompat.checkSelfPermission(this, permission.READ_PHONE_STATE);
        if(result1 == PackageManager.PERMISSION_GRANTED){
            deviceId= getDeviceId(this);
        }
        else {
            alert();
        }
    }*/
    final RadioButton rb[][] = new RadioButton[7][7];
    final View s[][] = new View[6][6];
    final View b[][][][] = new View[7][7][7][7];
    final int isclicked[][] = new int[7][7];

Boolean isnew = Boolean.FALSE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        populate(0);
            createGame();




            }

    private void createGame(){


        theme = MediaPlayer.create(this, R.raw.varienop);
        theme.start();
        theme.setLooping(true);
        populate(0);
        setContentView(R.layout.activity_main);
        dbHandler = new MyDBHandler(this,null,null,1);


        isclicked[1][6]++;
        isclicked[6][1]++;

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
        final TextView p1scoret = (TextView) findViewById(R.id.p1score);
        final TextView p2scoret = (TextView) findViewById(R.id.p2score);
        final TextView GOtextt = (TextView) findViewById(R.id.GOtext);


        p1scoret.setTypeface(typeFace);
        p2scoret.setTypeface(typeFace);
        GOtextt.setTypeface(typeFace);



        Resources res = getResources();

        int p;
        int q;
        for (p = 1; p < 7; p++)
            for (q = 1; q < 7; q++) {
                String rbstring = "rb" + p + q;

                rb[p][q] = (RadioButton) findViewById(res.getIdentifier(rbstring, "id", getPackageName()));

            }




        int r;
        int f;
        for (p = 1; p < 7; p++)
            for (q = 1; q < 7; q++)
                for(r = 1;r<7;r++)
                    for(f = 1;f<7;f++)
                    {
                        String bstring = "b" + p + q + r + f;
                        b[p][q][r][f] =findViewById(res.getIdentifier(bstring,"id",getPackageName()));
                    }



        for (p = 1; p < 6; p++)
            for (q = 1; q < 6; q++)
            {
                String sstring = "s" + p + q;
                s[p][q] = findViewById(res.getIdentifier(sstring,"id",getPackageName()));
            }
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        for (int i = 1; i < 7; i++)
            for (int j = 1; j < 7; j++) {
                {
                    //checkPermission();
                    final int finalJ = j;
                    final int finalI = i;

                    rb[i][j].setOnClickListener(new RadioButton.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(isclicked[finalI][finalJ]==0)
                            {
                                time1=Calendar.getInstance().getTimeInMillis();
                            }

                            //vals= new ArrayList<model>();
                            try{mFirebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {



                                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                                    //vals=dataSnapshot.getValue();
                                    String value= "";
                                    Set set;
                                    if(map!=null) {
                                        set = map.entrySet();

                                        Iterator iterator = set.iterator();
                                        int j = 0;
                                        while (iterator.hasNext()) {
                                            Map.Entry mentry = (Map.Entry) iterator.next();
                                            long d = 1;
                                          /*  Context context = getApplicationContext();

                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context,String.valueOf(d) , duration);
                                            toast.show();
                                            d = (long) mentry.getValue();*/
                                            //numval=numval + String.valueOf(d);
                                            savegame[j] = (int) d;

                                            j++;

                                        }
                                        if(isclicked[finalI][finalJ]==0) {
                                            if (j % 2 == 0) {
                                                isp1 = Boolean.FALSE;
                                            } else {
                                                isp1 = Boolean.TRUE;
                                            }
                                        }
                                    }
                                    int i=0;


                                    int aftermod;

                                    int val;
                                    int scores;
                                    int player;

                                    while(savegame[i]!=0) {


                                        scores=savegame[i]/1000;
                                        scores=scores-1;
                                        aftermod=savegame[i]%1000;

                                        if(aftermod>200)
                                        {player=2;
                                            val=aftermod-200;
                                            isp1=Boolean.FALSE;}
                                        else {
                                            player=1;
                                            isp1=Boolean.TRUE;
                                            val=aftermod-100;
                                        }
                                        if(scores>p1max&&player==1)
                                        {
                                            p1max=scores;
                                        }
                                        else if(scores>p2max&&player==2)
                                        {
                                            p2max=scores;
                                        }


                                        rb[val/10][val%10].setChecked(true);
                                        draw(val/10,val%10);

                                        isclicked[val/10][val%10]++;
                                        i++;
                                        printscores(scores,player);
                                        printscores(p1max,1);
                                        printscores(p2max,2);
                                        if(toremove[0]!=0&&toremove[1]!=0)
                                            rb[toremove[0]][toremove[1]].setChecked(false);

                                    }


                                }




                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });}catch (NullPointerException e){
                                e.printStackTrace();
                            }


                            elecounter++;
                            draw(finalI,finalJ);




                            if(!isp1) {
                                int xval=100+finalI * 10 + finalJ + 1000 + 1000*p1score;
                               /* Buttons product = new Buttons(100+finalI * 10 + finalJ + 1000 + 1000*p1score);
                                latestele[elecounter]=product.get_BStatus();
                                dbHandler.addProduct(product);*/
                                if(isclicked[finalI][finalJ]==1) {
                                    mFirebaseDatabaseReference.push().setValue(xval);
                                    time2=Calendar.getInstance().getTimeInMillis();


                                    populate(1);
                                   // vall.setText(String.valueOf(time2-time1));
                                    /*if (model.key!=null)
                                    val.setText(String.valueOf(deviceId)+model.key);*/
                                }



                                p1scoret.setTextSize(25);
                                p2scoret.setTextSize(35);
                            }
                            else{
                                int xval=200+finalI * 10 + finalJ + 1000+1000*p2score;
                                /*Buttons product = new Buttons(200+finalI * 10 + finalJ + 1000+1000*p2score);
                                latestele[elecounter]=product.get_BStatus();
                                dbHandler.addProduct(product);*/

                                if(isclicked[finalI][finalJ]==1) {
                                    mFirebaseDatabaseReference.push().setValue(xval);
                                    time2=Calendar.getInstance().getTimeInMillis();

                                    populate(2);
                                   // vall.setText(String.valueOf(time2-time1));
                                    /*if(model.key!=null)
                                    val.setText(String.valueOf(deviceId)+model.key);*/
                                }
                                p2scoret.setTextSize(25);
                                p1scoret.setTextSize(35);
                            }
                           // savegame = dbHandler.databaseToString();








                            p1scoret.setText(String.valueOf(p1score));
                            p2scoret.setText(String.valueOf(p2score));
                            if (p1score + p2score == 25) {
                                GOtextt.setVisibility(View.VISIBLE);
                                if(p1score>p2score)
                                    p1scoret.setTextSize(55);
                                else{
                                    p2scoret.setTextSize(55);
                                    p1scoret.setTextSize(25);}


                                theme.stop();
                            }


                        }


                    });

                }
            }

    }


    @Override
    protected void onRestart() {

        super.onRestart();
        RelativeLayout gamescreen = (RelativeLayout)findViewById(R.id.gamescreen);
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        gamescreen.setSystemUiVisibility(uiOptions);
        View off = findViewById(R.id.vader);
        View on = findViewById(R.id.mutedb);
        nomusic(on);
        if(isoff){
            off.setVisibility(View.VISIBLE);
        }

        theme.start();
       populate(0);


        // Activity being restarted from stopped state
    }

    @Override
    protected void onPause() {
        super.onPause();

        theme.pause();
        // Activity being restarted from stopped state
    }

    @Override
    protected void onStart()
    {

        populate(0);
        RelativeLayout gamescreen = (RelativeLayout)findViewById(R.id.gamescreen);
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        gamescreen.setSystemUiVisibility(uiOptions);


        super.onStart();
        View off = findViewById(R.id.vader);
        View on= findViewById(R.id.mutedb);


        if(calledcreate==1)
        nomusic(on);

        calledcreate=0;





    }
    @Override
    protected void onStop()
    {
        super.onStop();
               theme.pause();

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
            theme.pause();
            // Stop the media player here
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void nomusic(View view)
    {

        View other = findViewById(R.id.mutedb);

            theme.pause();
            view.setVisibility(view.INVISIBLE);
            other.setVisibility(view.VISIBLE);


    }

    public void yesmusic(View view)
    {
        View other = findViewById(R.id.vader);
        theme.start();
        view.setVisibility(view.INVISIBLE);
        other.setVisibility(view.VISIBLE);
    }
    public void Restart(View view)
    {

        View on = findViewById(R.id.vader);
        View off = findViewById(R.id.mutedb);



        if(off.getVisibility()==View.VISIBLE){

            isoff=Boolean.TRUE;
        }
        theme.pause();
        this.recreate();


        if(calledcreate==1)
            nomusic(off);

        calledcreate=0;


        int p;
        int q;
        for (p = 1; p < 7; p++)
            for(q=1;q<7;q++)
        {
            if((p==1&&q==6)||(p==6&&q==1))
                ;
            else
            rb[p][q].setChecked(false);
        }
mFirebaseDatabaseReference.removeValue();
        //dbHandler.deleteProduct();

       if(isoff){
           off.setVisibility(View.VISIBLE);
           theme.pause();
       }

populate(0);


    }


    public void Undo(View view){




        undo=dbHandler.databaseToString();
        int val;
        int i=0;
        while(undo[i++]!=0);
        i=i-2;
        if(i<0)
            return;
        val=undo[i];
        val=val%100;
        toremove[0]=val/10;
        toremove[1]=val%10;
       if(toremove[0]!=0&&toremove[1]!=0)
            rb[toremove[0]][toremove[1]].setChecked(false);
        dbHandler.deletelastentry();




        calledcreate=1;
  //   this.recreate();

        createGame();
        populate(0);



    }


    public void draw(int finalI,int finalJ) {

        isclicked[finalI][finalJ]++;
        rb[finalI][finalJ].setChecked(false);


        if (finalI + 1 <= 6) {
            if (rb[finalI + 1][finalJ].isChecked())

            {
                b[finalI][finalJ][finalI + 1][finalJ].setVisibility(View.VISIBLE);
            }
        }
        if (finalI - 1 >= 1) {
            if (rb[finalI - 1][finalJ].isChecked())

            {
                b[finalI - 1][finalJ][finalI][finalJ].setVisibility(View.VISIBLE);
            }
        }

        if (finalJ + 1 <= 6) {
            if (rb[finalI][finalJ + 1].isChecked())

            {
                b[finalI][finalJ][finalI][finalJ + 1].setVisibility(View.VISIBLE);

            }
        }
        if (finalJ - 1 >= 1) {
            if (rb[finalI][finalJ - 1].isChecked())

            {
                b[finalI][finalJ - 1][finalI][finalJ].setVisibility(View.VISIBLE);

            }
        }

        if (finalI - 1 >= 1 && finalJ - 1 >= 1) {
            if (rb[finalI - 1][finalJ].isChecked() && rb[finalI - 1][finalJ - 1].isChecked() && rb[finalI][finalJ - 1].isChecked()) {
                if (isclicked[finalI][finalJ] <= 1)
                    increasescore(2);
                s[finalI - 1][finalJ - 1].setVisibility(View.VISIBLE);
            }
            if (isclicked[finalI][finalJ] <= 1)

                if (isp1 == Boolean.TRUE && s[finalI - 1][finalJ - 1].getVisibility() == View.VISIBLE && isclicked[finalI][finalJ] <= 1) {
                    s[finalI - 1][finalJ - 1].setBackgroundColor(Color.parseColor("#ff5500"));
                    increasescore(1);
                }
        }
        if (finalI - 1 >= 1 && finalJ + 1 <= 6) {
            if (rb[finalI - 1][finalJ].isChecked() && rb[finalI - 1][finalJ + 1].isChecked() && rb[finalI][finalJ + 1].isChecked()) {
                if (isclicked[finalI][finalJ] <= 1)
                    increasescore(2);
                s[finalI - 1][finalJ].setVisibility(View.VISIBLE);
            }
            if (isclicked[finalI][finalJ] <= 1)

                if (isp1 == Boolean.TRUE && s[finalI - 1][finalJ].getVisibility() == View.VISIBLE && isclicked[finalI][finalJ] <= 1) {
                    s[finalI - 1][finalJ].setBackgroundColor(Color.parseColor("#ff5500"));
                    increasescore(1);
                }
        }
        if (finalI + 1 <= 6 && finalJ - 1 >= 1) {
            if (rb[finalI + 1][finalJ].isChecked() && rb[finalI + 1][finalJ - 1].isChecked() && rb[finalI][finalJ - 1].isChecked()) {
                if (isclicked[finalI][finalJ] <= 1)
                    increasescore(2);
                s[finalI][finalJ - 1].setVisibility(View.VISIBLE);
            }
            if (isclicked[finalI][finalJ] <= 1)

                if (isp1 == Boolean.TRUE && s[finalI][finalJ - 1].getVisibility() == View.VISIBLE && isclicked[finalI][finalJ] <= 1) {
                    s[finalI][finalJ - 1].setBackgroundColor(Color.parseColor("#ff5500"));
                    increasescore(1);
                }
        }
        if (finalI + 1 <= 6 && finalJ + 1 <= 6) {
            if (rb[finalI][finalJ + 1].isChecked() && rb[finalI + 1][finalJ + 1].isChecked() && rb[finalI + 1][finalJ].isChecked()) {
                if (isclicked[finalI][finalJ] <= 1)
                    increasescore(2);
                s[finalI][finalJ].setVisibility(View.VISIBLE);
            }
            if (isclicked[finalI][finalJ] <= 1)

                if (isp1 == Boolean.TRUE && s[finalI][finalJ].getVisibility() == View.VISIBLE && isclicked[finalI][finalJ] <= 1) {
                    s[finalI][finalJ].setBackgroundColor(Color.parseColor("#ff5500"));
                    increasescore(1);
                }
        }
        rb[finalI][finalJ].setChecked(true);
        /*if (isclicked[finalI][finalJ] <= 1) {
            if (isp1 == Boolean.FALSE)
                isp1 = Boolean.TRUE;


            else isp1 = Boolean.FALSE;
        }*/

    }

    public void printscores(int score,int player)
    {
        TextView sc1= (TextView)findViewById(R.id.p1score);
        TextView sc2=(TextView)findViewById(R.id.p2score);

       if(player==1) sc1.setText(String.valueOf(score));
        else if(player==2)
        sc2.setText(String.valueOf(score));

    }


    public void populate(int p){
        //savegame = dbHandler.databaseToString();


        try{mFirebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               /* for(DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    model = childSnapshot.getValue(model.class);
                    ss=ss+model.key;
                    Context context = getApplicationContext();

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, ss, duration);
                    toast.show();

                }*/



                //model = dataSnapshot.getValue(model.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                dataSnapshot.getValue();
                String value= "";

                Set set;
                if(map!=null) {
                    set = map.entrySet();

                    Iterator iterator = set.iterator();
                    int j = 0;
                    while (iterator.hasNext()) {
                        Map.Entry mentry = (Map.Entry) iterator.next();
                        long d = 1;

                        d = (long) mentry.getValue();
                        //numval=numval + String.valueOf(d);
                        savegame[j] = (int) d;

                        j++;
                    }

                    if(j%2==0)
                    {
                        isp1=Boolean.FALSE;
                    }
                    else
                    {
                        isp1=Boolean.TRUE;
                    }
                }

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}catch (NullPointerException e){
            e.printStackTrace();
        }



        int i=0;


        int aftermod;

        int val;
        int scores;
        int player;

        while(savegame[i]!=0) {


            scores=savegame[i]/1000;
            scores=scores-1;
            aftermod=savegame[i]%1000;

            if(aftermod>200)
            {player=2;
                val=aftermod-200;
                isp1=Boolean.FALSE;}
            else {
                player=1;
                isp1=Boolean.TRUE;
                val=aftermod-100;
            }
            if(scores>p1max&&player==1)
            {
                p1max=scores;
            }
            else if(scores>p2max&&player==2)
            {
                p2max=scores;
            }



            rb[val/10][val%10].setChecked(true);
            draw(val/10,val%10);

            isclicked[val/10][val%10]++;
            i++;
            printscores(scores,player);
            printscores(p1max,1);
            printscores(p2max,2);
            if(toremove[0]!=0&&toremove[1]!=0)
                rb[toremove[0]][toremove[1]].setChecked(false);

        }

    }
    }
