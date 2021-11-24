package ru.ovoine.kursk43;

import static android.graphics.BitmapFactory.decodeFile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.Timer;

import ru.ovoine.kursk43.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private final String TAG = "mylog";
    private ActivityMainBinding bind;
    AssetManager myAssetManager;
    int screenWidth, screenHeight;
    final static float picWidth = 2070f, picHeight = 2937f;
    float wscale;    float mas_scale = 1f;
    float centr_x = 100.0f, centr_y = 150.0f;
    float centr_dx = 200.0f, centr_dy = 300.0f;
    float centr_minx = 200.0f, centr_miny = 300.0f;
    float centr_maxx = 200.0f, centr_maxy = 300.0f;
    float x_touch = centr_x, y_touch = centr_y;
    float x_touch_s = centr_x, y_touch_s = centr_y;
    boolean x_left = false, x_right = false, y_up = false, y_down = false;
    boolean x_left2 = false, x_right2 = false, y_up2 = false, y_down2 = false;
    boolean x_left4 = false, x_right4 = false, y_up4 = false, y_down4 = false;
    float touch_dx = 50.0f, touch_dy = 75.0f;
    private final int numScreen = 4;
    private final int[] mas_vel = { 15000, 9000, 6000, 4000, 2000, 1000, 500 };
    private String[] f_asset;    int l_asset;
    private final String folder_asset = "yug/";
    private int num_asset;
    private SubsamplingScaleImageView iv_pic;
    //    BitmapFactory.Options options;
    static CountDownTimer mycDT = null;
    private int velocity = 4;
    long startTime;
    final static long doubleTime = 500;
    final static int rc_load=314;  //Ответ от load

    private final String Dirname = "yug";
    private String pathfiles="";
    private boolean fload = false;

    private final String[] afiles = {"04_16", "04_24"
            ,"05_06","05_07","05_08","05_09","05_10","05_11","05_12","05_13","05_14","05_15"
            ,"05_16","05_17","05_18","05_19","05_20","05_21","05_22","05_23","05_24"
            ,"06_06","06_07","06_08","06_09","06_10","06_11","06_12","06_13","06_14","06_15"
            ,"06_16","06_17","06_18","06_19","06_20","06_21","06_22","06_23","06_24"
            ,"07_06","07_07","07_08","07_09","07_10","07_11","07_12","07_13","07_14","07_15"
            ,"07_16","07_17","07_18","07_19","07_20","07_21","07_22","07_23","07_24"
            ,"08_06","08_07","08_08","08_09","08_10","08_11","08_12","08_13","08_14","08_15"
            ,"08_16","08_17","08_18","08_19","08_20","08_21","08_22","08_23","08_24"
            ,"09_06","09_07","09_08","09_09","09_10","09_11","09_12","09_13","09_14","09_15"
            ,"09_16","09_17","09_18","09_19","09_20","09_21","09_22","09_23","09_24"
            ,"10_06","10_07","10_08","10_09","10_10","10_11","10_12","10_13","10_14","10_15"
            ,"10_16","10_17","10_18","10_19","10_20","10_21","10_22","10_23","10_24"
            ,"11_06","11_07","11_08","11_09","11_10","11_11","11_12","11_13","11_14","11_15"
            ,"11_16","11_17","11_18","11_19","11_20","11_21","11_22","11_23","11_24"
            ,"12_06","12_07","12_08","12_09","12_10","12_11","12_12","12_13","12_14","12_15"
            ,"12_16","12_17","12_18","12_19","12_20","12_21","12_22","12_23","12_24"
            ,"13_06","13_07","13_08","13_09","13_10","13_11","13_12","13_13","13_14","13_15"
            ,"13_16","13_17","13_18","13_19","13_20","13_21","13_22","13_23","13_24"
            ,"14_06","14_07","14_08","14_09","14_10","14_11","14_12","14_13","14_14","14_15"
            ,"14_16","14_17","14_18","14_19","14_20","14_21","14_22","14_23","14_24"
            ,"15_06"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
//        tb_main = findViewById(R.id.tb_main);  //Выдает ошибку
        setSupportActionBar(bind.tbMain);

        fload = setloadfiles();
        if (!fload) {
            Intent myload = new Intent(this, load.class);
            startActivityForResult(myload, rc_load);
        } else Toast.makeText(this,"Карты загружены, Порядок", Toast.LENGTH_LONG).show();

        iv_pic = bind.ivPic;
        iv_pic.setOnTouchListener(this);
        iv_pic.setZoomEnabled(false);
        //Log.d(TAG,"\"+++++++++++++++START KURSK43 +++++++++++++++++++++++++++++++\"");
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        set_center(mas_scale,350f, 1600f);  //Томаровка
        myAssetManager = getApplicationContext().getAssets();
        l_asset = read_assets();
        num_asset = 0;
        out_datetime(num_asset, centr_x, centr_y);
        startTime = System.currentTimeMillis();
    }  //onCreate(Bundle savedInstanceState)

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(TAG,"result="+resultCode+"  data="+data+"  reqc="+requestCode);
        if(requestCode == rc_load && resultCode == RESULT_OK && data != null){
            String mesread = data.getStringExtra("key_read");
            String mesmax = data.getStringExtra("key_max");
            //Log.d(TAG, "RESULT_OK read="+mesread+" из "+mesmax);
        } else if (requestCode == rc_load && resultCode == RESULT_CANCELED) {
            //Log.d(TAG,"Не считали файлы... но");
            if (data!=null) {
                String mesread = data.getStringExtra("key_read");
                String mesmax = data.getStringExtra("key_max");
                //Log.d(TAG, "RESULT_CANCELED read=" + mesread + " из " + mesmax);
            } else finish();
        } else if (requestCode == rc_load) {
            //Log.d(TAG,"Не OK не CANCELED req="+rc_load);
            String mesread = data.getStringExtra("key_read");
            String mesmax = data.getStringExtra("key_max");
            //Log.d(TAG, "NOT OK, NOT _CANCELED read="+mesread+" из "+mesmax);
        }
        fload = setloadfiles();
        l_asset = read_assets();
    }

    private boolean setloadfiles(){
        getDir(Dirname,MODE_PRIVATE);  /*Создается data/data/app_yug, если не было*/
        String mcachdir = ""+getCacheDir();
        pathfiles = mcachdir.substring(1, mcachdir.length()-5);
        pathfiles = pathfiles+"app_yug/";
        //Log.d(TAG,"pathfiles="+pathfiles);
        File directoryfiles = new File(pathfiles);
        File[] filesfiles = directoryfiles.listFiles();
        return afiles.length==filesfiles.length;
    }

    private void set_center(float scal, float c_x, float c_y) {
        wscale = scal*(screenHeight)/picHeight; //шкала по ширине
        centr_x = c_x;        centr_y = c_y;
        centr_dx = (picWidth / numScreen / numScreen)/wscale;
        centr_dy = (picHeight / numScreen / numScreen)/wscale;
        centr_minx = centr_dx; centr_miny = centr_dy;
        centr_maxx = picWidth-centr_dx; centr_maxy = picHeight-centr_dy;
        //Log.d(TAG,"== RAS wscale ="+wscale+ " CENTR x="+centr_x+" y="+centr_y+" dx="+centr_dx+ " dy="+ centr_dy +"\nminx="+centr_minx+ " miny="+ centr_miny+" maxx="+centr_maxx+ " maxy="+ centr_maxy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private int read_assets(){
        //Log.d(TAG,"read_assets");
        int l = 0;
        if (fload) {
            l = afiles.length;
            //Log.d(TAG,"from FILES l="+l);
        } else {
            try {
                f_asset = myAssetManager.list("yug"); // массив имён файлов
            } catch (IOException e) {
                e.printStackTrace();
            }
            l = f_asset.length;
            //Log.d(TAG, " Ftom assets l= " + l);
            if (f_asset.length > 0) {
                for (int i = 0; (i < l && i < 3); i++) {
                    //Log.d(TAG, " " + f_asset[i]);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Нет файлов для работы программы " + getString(R.string.app_name), Toast.LENGTH_LONG).show();
                finish();
            }
        }
        return l;
    }

    private static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;  //true - только параметры Не работает для decodeStream
        options.inSampleSize = 1;  //Во сколько раз пожать картинку
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            return null;
        }
        bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

    private static Bitmap getBitmapFromFile(Context context, String pathfile, String fileName) {
        File image = new File(pathfile, fileName);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        return bitmap;
    }

    private void bitmapToSS(int num_asset, float f_compr, float c_x, float c_y){
//        //Log.d(TAG, "---------void bitmapToSS(int num_asset="+num_asset+" , float f_compr="+ f_compr+" , float c_x="+c_x+" , \nfloat c_y="+c_y+", fload="+fload);
        Bitmap bmin = null;
        String s = "";
        if (fload) {
            s=afiles[num_asset];
            s = s.substring(0,s.length())+".jpg";
            //Log.d(TAG,"FROM FILES Отображаем ="+pathfiles+"  file="+s);
            bmin = getBitmapFromFile(getApplicationContext(),pathfiles,s);
        } else {
            s = folder_asset + f_asset[num_asset];
            //Log.d(TAG, "ASSETS  Отображаем файл =" + s + " c_x=" + c_x + "  c_y=" + c_y);
            bmin = getBitmapFromAsset(getApplicationContext(), s);
//            //Log.d(TAG, "BITMAP H=" + bmin.getHeight() + " W=" + bmin.getWidth());
//            //Log.d(TAG, "ПАРАМ f_compr=" + f_compr + "  CENTR x=" + c_x + " y=" + c_y);
        }
        iv_pic.setImage(ImageSource.bitmap(bmin));
        PointF mycentr = new PointF(c_x, c_y);
        iv_pic.setScaleAndCenter(f_compr, mycentr);
        /*
        //Log.d(TAG, "AFTER setSCALE");
        //Log.d(TAG, "IV_PIC H=" + iv_pic.getHeight() + " SH=" + iv_pic.getSHeight() +
                " W=" + iv_pic.getWidth() + " SW=" + iv_pic.getSWidth());
        //Log.d(TAG, "IV_PIC ScX=" + iv_pic.getScaleX() + " ScY=" + iv_pic.getScaleY() +
                " Top=" + iv_pic.getTop());

         */
        //Log.d(TAG, "        Отобразили " + s);
    }

///////////////////////////////////////////////////////////////////
private void out_datetime(int num_a, float c_x, float c_y){
    //Log.d(TAG,"out_datetime(int num_a)");
    int td = day_asset(num_a);
    int tt = hour_asset(num_a);
    //Log.d(TAG,"td="+td+" tt="+tt);
    bitmapToSS(num_a, wscale, c_x, c_y);
    bind.tvDate.setText(String.format("%2d",td));
    bind.tvTime.setText(String.format("%2d:00",tt));
}

    private int hour_asset(int num_a) {
        int i=0;
        if (!fload) {
            String s = f_asset[num_a];
            i = Integer.parseInt(s.substring(11,13));
        }
        else {
            String s = afiles[num_a];
            i = Integer.parseInt(s.substring(3,5));
        }
        return i;
    }

    private int day_asset(int num_a) {
        int i=0;
        if (!fload) {
            String s = f_asset[num_a];
            i = Integer.parseInt(s.substring(8,10));
        }
        else {
            String s = afiles[num_a];
            i = Integer.parseInt(s.substring(0,2));
        }
        return i;
    }

    private int hour_back() {
        if (num_asset>0) {
            num_asset--;
        }
        return hour_asset(num_asset);
    }

    private int hour_forward() {
        if (num_asset<l_asset-1) {
            num_asset++;
        }
        return hour_asset(num_asset);
    }

    public void cl_back(View view) {
        hour_back();
        out_datetime(num_asset, centr_x, centr_y);
    }

    public void cl_dayback(View view) {
        int td = day_asset(num_asset);
        int mtd = day_asset(0);
        int tt = hour_asset(num_asset);
        if (td>mtd) {
            while (hour_back()!=tt && num_asset>0) {            }
        }
        out_datetime(num_asset, centr_x, centr_y);
    }

    public void cl_forward(View view) {
        hour_forward();
        out_datetime(num_asset, centr_x, centr_y);
    }

    public void cl_dayforward(View view) {
        int td = day_asset(num_asset);
        int mtd = day_asset(l_asset-1);
        int tt = hour_asset(num_asset);
        if (td < mtd) while (hour_forward()!=tt && num_asset<l_asset-1) {            }
        out_datetime(num_asset, centr_x, centr_y);
    }

    private void set_velocity(int vel, boolean vel_enable){
        switch (vel) {
            case 1:  bind.tbMain.getMenu().findItem(R.id.mm_vel).
                        setIcon(getResources().getDrawable(R.drawable.vel01));
                break;
            case 2:  bind.tbMain.getMenu().findItem(R.id.mm_vel).
                        setIcon(getResources().getDrawable(R.drawable.vel02));
                break;
            case 3:  bind.tbMain.getMenu().findItem(R.id.mm_vel).
                        setIcon(getResources().getDrawable(R.drawable.vel03));
                break;
            case 4:  bind.tbMain.getMenu().findItem(R.id.mm_vel).
                        setIcon(getResources().getDrawable(R.drawable.vel04));
                break;
            case 5:  bind.tbMain.getMenu().findItem(R.id.mm_vel).
                        setIcon(getResources().getDrawable(R.drawable.vel05));
                break;
            case 6:  bind.tbMain.getMenu().findItem(R.id.mm_vel).
                        setIcon(getResources().getDrawable(R.drawable.vel06));
                break;
            case 7:  bind.tbMain.getMenu().findItem(R.id.mm_vel).
                        setIcon(getResources().getDrawable(R.drawable.vel07));
                break;
        }
        bind.tbMain.getMenu().findItem(R.id.mm_vel).setEnabled(vel_enable);
    }

    private void setbutton_normal(){
        bind.btBack.setEnabled(true);
        bind.btBack.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        bind.btDayback.setEnabled(true);
        bind.btDayback.setImageResource(R.drawable.ic_fast_rewind_black_24dp);
        bind.btForward.setEnabled(true);
        bind.btForward.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
        bind.btDayforward.setEnabled(true);
        bind.btDayforward.setImageResource(R.drawable.ic_fast_forward_black_24dp);
        bind.tbMain.getMenu().findItem(R.id.mm_start).
                setIcon(getResources().getDrawable(R.drawable.ic_play_circle_green));
        set_velocity(velocity,true);
        bind.tbMain.getMenu().findItem(R.id.mm_info).setEnabled(true);
    }

    private void setbutton_play(){
        bind.btBack.setEnabled(false);
        bind.btBack.setImageResource(R.drawable.ic_arrow_back_gray_24);
        bind.btDayback.setEnabled(false);
        bind.btDayback.setImageResource(R.drawable.ic_fast_rewind_gray_24);
        bind.btForward.setEnabled(false);
        bind.btForward.setImageResource(R.drawable.ic_arrow_forward_gray_24);
        bind.btDayforward.setEnabled(false);
        bind.btDayforward.setImageResource(R.drawable.ic_fast_forward_gray_24);
        bind.tbMain.getMenu().findItem(R.id.mm_start).
                setIcon(getResources().getDrawable(R.drawable.ic_pause_circle_red));
//        set_velocity(velocity,false);
        bind.tbMain.getMenu().findItem(R.id.mm_info).setEnabled(false);
    }

    public void cl_starttm(MenuItem item) {
        if (item.getItemId() == R.id.mm_start) {
            if (mycDT!=null) {
                setbutton_normal();
                mycDT.cancel();
                mycDT = null;
            } else {
                setbutton_play();
                mycDT = new CountDownTimer(300000, mas_vel[velocity-1]) {
                    public void onTick(long millisUntilFinished) {
                        hour_forward();
                        out_datetime(num_asset, centr_x, centr_y);
                        if (num_asset>=l_asset-1) {
                            setbutton_normal();
                            Toast.makeText(getApplicationContext(),"Конец",Toast.LENGTH_SHORT).show();;
                            this.cancel();
                            mycDT = null;
                        }
                    }
                    public void onFinish() {
                    }
                };
                mycDT.start();
            }
        }
        else if (item.getItemId() == R.id.mm_info){
            Intent myinfo = new Intent(this, infomain.class);
            startActivity(myinfo);
        }
        else if (item.getItemId() == R.id.mm_vel){
            if (velocity<7) velocity++;
            else velocity = 1;
            set_velocity(velocity, true);
        }
    }  //cl_starttm(MenuItem item)

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        x_touch = event.getX();
        y_touch = event.getY();
        String sDown="";        String sMove="";        String sUp="";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                sDown = "Down: x=" + x_touch + ", y=" + y_touch;
                sMove = ""; sUp = "";
                x_touch_s = x_touch;
                y_touch_s = y_touch;
                break;
            case MotionEvent.ACTION_MOVE: // движение
                sMove = "Move: x=" + x_touch + ", y=" + y_touch;
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                sMove = "";
                sUp = "Up: x=" + x_touch + ", y=" + y_touch;
                x_right= ((x_touch-x_touch_s)>touch_dx); x_left = ((x_touch_s-x_touch)>touch_dx);
                y_down= ((y_touch-y_touch_s)>touch_dy);  y_up = ((y_touch_s-y_touch)>touch_dy);
                x_right2= ((x_touch-x_touch_s)>touch_dx*2);
                x_left2 = ((x_touch_s-x_touch)>touch_dx*2);
                y_down2= ((y_touch-y_touch_s)>touch_dy*2);
                y_up2 = ((y_touch_s-y_touch)>touch_dy*2);
                x_right4= ((x_touch-x_touch_s)>touch_dx*4);
                x_left4 = ((x_touch_s-x_touch)>touch_dx*4);
                y_down4= ((y_touch-y_touch_s)>touch_dy*4);
                y_up4 = ((y_touch_s-y_touch)>touch_dy*4);
                //Log.d(TAG,"x_left="+x_left+" x_right="+x_right+ "  Y_up="+y_up+ "  y_down="+y_down);
                long tektTime = System.currentTimeMillis();
                if ((tektTime-startTime)>doubleTime) {
                    startTime = tektTime;
                } else  //DoubleClick
                {
                    //Log.d(TAG,"  DOUBLE CLICK x=" + x_touch + " y=" + y_touch);
                    if (mas_scale>2.5f) mas_scale = 1f; else mas_scale = mas_scale+1f;
                    set_center(mas_scale, centr_x, centr_y);
                    //Log.d(TAG, "AFTER DOUBLE CLICK CENTR x="+centr_x+ " y="+centr_y);
                    out_datetime(num_asset, centr_x, centr_y);
                   /*
                    Rect rectf = new Rect();
                    iv_pic.getLocalVisibleRect(rectf); //For coordinates location relative to the parent
//                    anyView.getGlobalVisibleRect(rectf); //For coordinates location relative to the screen/display
                    //Log.d(TAG, "getLocalvisib w="+ String.valueOf(rectf.width()+
                            " h="+ String.valueOf(rectf.height())+" left="+ String.valueOf(rectf.left))+
                            "  right="+ String.valueOf(rectf.right)+"  top="+String.valueOf(rectf.top)+
                            "  bottom=" + String.valueOf(rectf.bottom));

                     */
                }
                break;
        }
//        //Log.d(TAG, "SDown="+sDown+" sMove="+sMove+" sUp="+sUp);
//        //Log.d(TAG, "rx="+rx+" ry="+ry+" size="+si);
        if (x_right || x_left || y_up || y_down) set_scroll();
        return true;  //true без скроллинга false со скроллингом
    }

    private void set_scroll(){
        //Log.d(TAG,"+++++ set_scroll(){====");
        //Log.d(TAG,"x_left="+x_left+" x_right="+x_right+ "  Y_up="+y_up+ "  y_down="+y_down);
        if (x_left) if ((centr_x+centr_dx)<centr_maxx) centr_x = centr_x+centr_dx;
        if (x_right) if ((centr_x-centr_dx)>centr_minx) centr_x = centr_x-centr_dx;
        if (y_up) if ((centr_y+centr_dy)<centr_maxy) centr_y = centr_y+centr_dy;
        if (y_down) if ((centr_y-centr_dy)>centr_miny) centr_y = centr_y-centr_dy;
        if (x_left2) if ((centr_x+centr_dx)<centr_maxx) centr_x = centr_x+centr_dx;
        if (x_right2) if ((centr_x-centr_dx)>centr_minx) centr_x = centr_x-centr_dx;
        if (y_up2) if ((centr_y+centr_dy)<centr_maxy) centr_y = centr_y+centr_dy;
        if (y_down2) if ((centr_y-centr_dy)>centr_miny) centr_y = centr_y-centr_dy;
        if (x_left4) if ((centr_x+centr_dx)<centr_maxx) centr_x = centr_x+centr_dx;
        if (x_right4) if ((centr_x-centr_dx)>centr_minx) centr_x = centr_x-centr_dx;
        if (y_up4) if ((centr_y+centr_dy)<centr_maxy) centr_y = centr_y+centr_dy;
        if (y_down4) if ((centr_y-centr_dy)>centr_miny) centr_y = centr_y-centr_dy;
        if (x_left4) if ((centr_x+centr_dx)<centr_maxx) centr_x = centr_x+centr_dx;
        if (x_right4) if ((centr_x-centr_dx)>centr_minx) centr_x = centr_x-centr_dx;
        if (y_up4) if ((centr_y+centr_dy)<centr_maxy) centr_y = centr_y+centr_dy;
        if (y_down4) if ((centr_y-centr_dy)>centr_miny) centr_y = centr_y-centr_dy;
        x_right = false;  x_left = false; y_up = false; y_down = false;
        x_right2 = false;  x_left2 = false; y_up2 = false; y_down2 = false;
        x_right4 = false;  x_left4 = false; y_up4 = false; y_down4 = false;
        //Log.d(TAG, "AFTER TOUCH CENTR x="+centr_x+ " y="+centr_y);
        out_datetime(num_asset, centr_x, centr_y);
    }

    private void read_scale(int x, int y) {
        //Log.d(TAG,"        READ_SCALE viewToSource");
        PointF myp = new PointF((float)x,(float) y);
        PointF myvtosc = iv_pic.viewToSourceCoord(myp);
        PointF myViewTL = new PointF(0.0f,0.0f);
        PointF myvtoscTL = iv_pic.viewToSourceCoord(myViewTL);
        PointF myViewBR = new PointF((float)screenWidth, (float)screenHeight);
        PointF myvtoscBR = iv_pic.viewToSourceCoord(myViewBR);
        //Log.d(TAG, "vtos        x("+myp.x+")="+myvtosc.x+ " y("+myp.y+")="+myvtosc.y);
        //Log.d(TAG, "TOPLEFT     x(0)="+myvtoscTL.x+ " y(0)="+myvtoscTL.y);
        //Log.d(TAG, "BottomRight x("+screenWidth+","+myViewBR.x+")="+myvtoscBR.x +" y("+screenHeight+","+myViewBR.y+")="+myvtoscBR.y);
    }

    private void set_scale(float x, float y){
//        wscale = (screenHeight+0.0f)/picHeight; //шкала по высоте
        centr_x = x;
        centr_y = y;
        out_datetime(num_asset, centr_x, centr_y);

    }
}
