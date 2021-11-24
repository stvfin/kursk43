package ru.ovoine.kursk43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class upravl extends AppCompatActivity {
    Toolbar tb_upravl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upravl);
        tb_upravl = findViewById(R.id.tb_upravl);
        setSupportActionBar(tb_upravl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upravl_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void cl_iupravl(MenuItem item) {
        if (item.getItemId()==R.id.iupr_back) {  this.finish();}
    }
}