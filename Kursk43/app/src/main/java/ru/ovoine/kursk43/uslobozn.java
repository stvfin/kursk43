package ru.ovoine.kursk43;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.ovoine.kursk43.databinding.ActivityInfoBinding;
import ru.ovoine.kursk43.databinding.ActivityUsloboznBinding;

public class uslobozn extends AppCompatActivity {
    private ActivityUsloboznBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityUsloboznBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        setSupportActionBar(bind.tbUsl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usl_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void cl_tbusl(View view) {
    }

    public void cl_um(MenuItem item) {
        if (item.getItemId()==R.id.um_back) {  this.finish();}
    }
}