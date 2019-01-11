package com.android.mouse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.pet.mypet.R;

import permission.FloatWindowManager;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("onKeyDown", event.toString()+"["+keyCode+"]");
        int DefaultSpeed = 25;
        int leftright = 0;
        int updown = 0;
        switch (keyCode){
            case 96:
                Toast.makeText(getApplicationContext(), "Btn[A]", Toast.LENGTH_SHORT).show();
                break;
            case 97:
                Toast.makeText(getApplicationContext(), "Btn[B]", Toast.LENGTH_SHORT).show();
                break;
            case 19:
                Toast.makeText(getApplicationContext(), "Btn[up]", Toast.LENGTH_SHORT).show();
                updown+=DefaultSpeed;
                break;
            case 20:
                Toast.makeText(getApplicationContext(), "Btn[down]", Toast.LENGTH_SHORT).show();
                updown-=DefaultSpeed;
                break;
            case 21:
                Toast.makeText(getApplicationContext(), "Btn[left]", Toast.LENGTH_SHORT).show();
                leftright+=DefaultSpeed;
                break;
            case 22:
                Toast.makeText(getApplicationContext(), "Btn[right]", Toast.LENGTH_SHORT).show();
                leftright-=DefaultSpeed;
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }
        // update screen size
        WindowManager manager = getWindowManager();
        Point point = new Point();
        if(Build.VERSION.SDK_INT < 17) {
            manager.getDefaultDisplay().getSize(point);
        } else {
            manager.getDefaultDisplay().getRealSize(point);
        }
        FloatWindowManager.getInstance().UpdateScreen(0,point.y,0,point.x);
        // update float window
        FloatWindowManager.getInstance().UpdateFloatWindow(leftright, updown);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btn_start = (Button) findViewById(R.id.main_start_service);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "service已开启", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(MainActivity.this, PetService.class);
//                startService(intent);
                FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this);
            }
        });

        Button btn_stop = (Button) findViewById(R.id.main_stop_service);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "service已关闭", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent();
//                intent.setAction("com.android.mouse.PetService");
//                stopService(intent);
                FloatWindowManager.getInstance().dismissWindow();
            }
        });

        Button btn_permission = (Button) findViewById(R.id.permission);
        btn_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "获取权限", Toast.LENGTH_LONG).show();
                // permission
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                intent.setData(uri);
                try {
                    MainActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
