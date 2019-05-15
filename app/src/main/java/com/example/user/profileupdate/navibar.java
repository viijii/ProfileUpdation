package com.example.user.profileupdate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class navibar extends AppCompatActivity {

    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navibar);

        d1 = (DrawerLayout)findViewById(R.id.d1);
        abdt = new ActionBarDrawerToggle(this,d1,R.string.open, R.string.close);
        abdt.setDrawerIndicatorEnabled(true);

        d1.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();

                if (id == R.id.profile){

                    Intent intent = new Intent(navibar.this,Profile1.class);
                    startActivity(intent);

                    Toast.makeText(navibar.this, "Profile",Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });


        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
