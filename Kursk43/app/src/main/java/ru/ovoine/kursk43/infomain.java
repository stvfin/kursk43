package ru.ovoine.kursk43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class infomain extends AppCompatActivity {
    Toolbar tb_maininfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomain);
        tb_maininfo = findViewById(R.id.tb_infomain);
        setSupportActionBar(tb_maininfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maininfo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void cl_tbinfomain(View view) {
    }

    public void cl_infomainmenu(MenuItem item) {
        if (item.getItemId()==R.id.minfo_back) {  this.finish();}
    }

    public void cl_common(View view) {
        Intent myinfo = new Intent(this, info.class);
        startActivity(myinfo);
    }

    public void cl_uslobozn(View view) {
        Intent myuo = new Intent(this, uslobozn.class);
        startActivity(myuo);
    }

    public void cl_upravl(View view) {
        Intent myupr = new Intent(this, upravl.class);
        startActivity(myupr);
    }
}