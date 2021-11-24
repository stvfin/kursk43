package ru.ovoine.kursk43;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.ovoine.kursk43.databinding.ActivityInfoBinding;

public class info extends AppCompatActivity {
    private ActivityInfoBinding bind;
//    private Toolbar tb_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        setSupportActionBar(bind.tbInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void cl_tbinfo(View view) {
    }

    public void cl_im(MenuItem item) {
        if (item.getItemId()==R.id.im_back) {  this.finish();}
        /*
        else if (item.getItemId()==R.id.im_usl) {
            Toast.makeText(this, "Условные обозначения", Toast.LENGTH_SHORT).show();

        }

         */
    }
}