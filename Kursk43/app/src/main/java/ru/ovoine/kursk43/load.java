package ru.ovoine.kursk43;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ru.ovoine.kursk43.databinding.ActivityLoadBinding;
import ru.ovoine.kursk43.databinding.ActivityMainBinding;

public class load extends AppCompatActivity {
    private ActivityLoadBinding bind;
    private final String TAG = "mylog";
    private TextView tvinfoload, tvinfomax;
    private final long nugnomax = 600;
    private long nugno = 600;  /*Объем флеш для загрузки карт*/
    private final String Dirname = "yug";
    private final String urlinput =
            "https://raw.githubusercontent.com/stvfin/filesforkursk43/main/yug/";
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

    String mcachdir="";
    String pathcach = "";
    String pathfiles = "";
    String pathwrk = "";

    static CountDownTimer timer_load = null;
    static long int_time = 100;
    boolean fmove = false;
    boolean fload = false;
    int numfileload = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLoadBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        setSupportActionBar(bind.tbLoad);
        tvinfoload = bind.tvinfoload;
        tvinfomax = bind.tvinfomax;
        tvinfoload.setText("0");
        tvinfomax.setText("200");
        setloadfiles();
        infofiles();
        numfileload = 0;
        outinfoload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.load_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void cl_il(MenuItem item) {
        if (item.getItemId() == R.id.il_back) {
            //Log.d(TAG, "Back");
            reztomain();
            finish();
        }
    }

    private  long is_file(String nf) {
        long rez = 0;
//        //Log.d(TAG," \n*************************Рабочая папка "+pathfiles);
        File directoryfiles = new File(pathfiles);
        File[] filesfiles = directoryfiles.listFiles();
        for (int i =0; i<filesfiles.length; i++){  //Можно не проверять на 0
            String fnt = filesfiles[i].getName();
            long sizefile = filesfiles[i].length();
//            //Log.d(TAG, "FILE:"+fnfiles+" Размер="+sizefile);
            if (fnt.equals(nf+".jpg")) {
                //Log.d(TAG, "Существует FILE:"+fnt+" Размер="+sizefile);
                tvinfoload.setText(""+(i+1)+"  "+fnt);
                rez = sizefile;
                break;
            }
        }
//        if (rez==0) //Log.d(TAG, "File:"+nf+".jpg не скачан");
        return rez;
    }

    private void outinfoload(){   //Для оперативного отображения
        //Log.d(TAG,"void outinfoload(){   //Для оперативного отображения");
        int nlf=0;
        for (int i=0; i < afiles.length; i++) if (is_file(afiles[i])>0) nlf++;
        tvinfoload.setText(""+nlf);
        tvinfomax.setText(""+afiles.length);
    }

    private void setloadfiles(){  //Разовая фунция!!! Прописывание путей
        //Log.d(TAG,"void setloadfiles(){  //Разовая фунция!!! Прописывание путей");
        getDir(Dirname,MODE_PRIVATE);  /*Создается data/data/app_yug, если не было*/
        mcachdir = ""+getCacheDir();
        pathfiles = mcachdir.substring(1, mcachdir.length()-5);
        pathcach = mcachdir+"/image_manager_disk_cache";
        pathfiles = pathfiles+"app_yug/";
        //Log.d(TAG,"pathfiles="+pathfiles);
        File directoryfiles = new File(pathfiles);
        File[] filesfiles = directoryfiles.listFiles();
        //Log.d(TAG, " \nВ папке "+ pathfiles + " файлов:"+filesfiles.length);
        for (int i =0; i<filesfiles.length; i++){           //Можно не проверять на 0
            String fnfiles = filesfiles[i].getName();
            long sizefile = filesfiles[i].length();
            //Log.d(TAG, "FILE:"+(i+1)+" "+fnfiles+" size="+sizefile);
        }
        //Log.d(TAG,"-----------------------------");
    }

    private void infofiles() {  //Разовая операция проверка свободного места
        long sumsize=0;
        //Log.d(TAG,"void infofiles() {  //Разовая операция проверка свободного места");
        for (int i=0; i < afiles.length; i++) sumsize+=is_file(afiles[i]);
        nugno = nugnomax - sumsize/1000000;
        long gfs = getFilesDir().getFreeSpace();
        long gts = getFilesDir().getTotalSpace();
        long ost = gfs/1000000-nugno;
        bind.tvtotaldata.setText(""+gts/1000000+" Mb");
        bind.tvfreedata.setText(""+gfs/1000000+" Mb");
        bind.tvnugnodata.setText(""+nugno+" Mb");
        bind.tvostdata.setText(""+ost+" Mb");
        if (ost<0) {
            Toast.makeText(getApplicationContext(),"Места недостаточно, свободно "+
                    gfs/1000000+" Mb \nНУЖНО "+nugno+" Mb\nПриложение закрывается", Toast.LENGTH_LONG).show();
            reztomain();
            finish();
        }
    }

    private void reztomain() {
        Intent imain = new Intent();
        File directoryfiles = new File(pathfiles);
        File[] filesfiles = directoryfiles.listFiles();
        imain.putExtra("key_read",""+filesfiles.length);
        imain.putExtra("key_max",""+afiles.length);
        setResult(RESULT_CANCELED, imain);
    }

    private void DownloadFilesGlide() {
        String tf=urlinput+"/yug_4307"+afiles[numfileload]+".jpg";
        //Log.d(TAG," "+"\ntf="+tf);
        fload = false;
        Glide.with(this)
                .load(tf)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        fload = true;
                        return false; // important to return false so the error placeholder can be placed
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //Log.d(TAG, "Загружен файл (GLIDE): "+afiles[numfileload]+".jpg");
                        MoveFilesFromCach();
                        fload = true;
                        return false;
                    }
                })
                .into(bind.ivback);
    }

    private void MoveFilesFromCach(){
        //Log.d(TAG,"MoveFilesFromCach() cachdir="+mcachdir);
        File directorycach = new File(pathcach);
        File[] filescach = directorycach.listFiles();
        File directoryfiles = new File(pathfiles);
        File[] filesfiles = directoryfiles.listFiles();
        String tf ="";
        int locnumf=0;
        for (int i = 0; i < filescach.length; i++)        {
            String fn = filescach[i].getName();
            if (!fn.equals("journal")) {
                tf=fn;
                locnumf=i;
                break;
            }
        }
        //Log.d(TAG,"Выбран файл для переноса:"+tf);
        String nsrc = pathcach+"/"+tf;
        String ndst = pathfiles+afiles[numfileload]+".jpg";
        //Log.d(TAG, " "+"\nnsrc="+nsrc+"\nndst="+ndst);
        boolean rez=false;
        try {
            rez = filemove(nsrc, ndst);
            //Log.d(TAG, "Скопировано rez =" + rez);
            fmove=true;
        } catch (IOException e) {
            //Log.d(TAG, "Ошибка копирования");
            e.printStackTrace();
            fmove=true;   //Возможно, нужно удвлить или нет
            rez=false;
        }
    }

    public boolean filemove(String nsrc, String ndst) throws IOException {
        File src = new File(nsrc);
        File dst = new File(ndst);
        //Log.d(TAG,"filemove()");
        boolean rez = false;
        InputStream in = new FileInputStream(src);
        //Log.d(TAG,"После InputStream in = new FileInputStream(src=\n"+src);
        try {
            //Log.d(TAG,"Перед OutputStream out = new FileOutputStream(dst);\n"+dst);
            OutputStream out = new FileOutputStream(dst);
            //Log.d(TAG,"После OutputStream out = new FileOutputStream(dst);"+dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                rez = true;
            }catch (IOException e) {
                //Log.d(TAG,"Ошибка while ((len = in.read(buf)) > 0)");
                e.printStackTrace();
                rez=false;
                out.close();
            } finally {
                out.close();
            }
        }catch (IOException e1) {
            //Log.d(TAG,"Ошибка OutputStream out = new FileOutputStream(dst);"+dst);
            e1.printStackTrace();
            rez=false;
        } finally {
            in.close();
        }
        File file = new File(nsrc);
        //Log.d(TAG, "Перед new File(nsrc).getAbsoluteFile().delete(); nsrc="+nsrc);
        new File(nsrc).getAbsoluteFile().delete();
        return  rez;
    }

    public void cl_start(View view) {
        if (timer_load !=null) {
            setbutton_normal();
        } else {
            Toast.makeText(this, "Проверка загрузки файлов и старт загрузки новых", Toast.LENGTH_SHORT).show();
            numfileload = 0;
            timer_start();
            setbutton_play();
        }
    }

    private void setbutton_normal(){
        bind.btstart.setText("Старт");
    }

    private void setbutton_play(){
        bind.btstart.setText("Стоп");
    }

    private void timer_stop() {
        if (timer_load !=null) {
            timer_load.cancel();
            timer_load = null;
        }
    }

    private void timer_start(){
        if (timer_load !=null) {
            timer_stop();
        } else {
            DownloadFilesGlide();
            timer_load = new CountDownTimer(3000000, int_time) {
                public void onTick(long millisUntilFinished) {
                    if (fmove) {
                        fmove = false;
                        outinfoload();
                        String s = bind.btstart.getText().toString();
                        if (s.equals("Стоп")) {
                            numfileload++;
                            if (numfileload < afiles.length) {
                                if (is_file(afiles[numfileload]) == 0) DownloadFilesGlide();
                                else {
                                    fmove = true;
                                }
                            } else {
                                setbutton_normal();
                                Toast.makeText(getApplicationContext(), "Конец загрузки", Toast.LENGTH_SHORT).show();
                                this.cancel();
                                timer_load = null;
                                reztomain();
                                finish();
                            }
                        } else {
                            setbutton_normal();
                            Toast.makeText(getApplicationContext(), "Прерывание загрузки", Toast.LENGTH_SHORT).show();
                            this.cancel();
                            timer_load = null;
                        }
                    }
                }
                public void onFinish() {
                }
            };
            timer_load.start();
        }
    }

}