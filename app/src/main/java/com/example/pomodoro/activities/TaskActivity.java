package com.example.pomodoro.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.pomodoro.adapters.TaskAdapter;
import com.example.pomodoro.database.DBContext;
import com.example.pomodoro.database.models.Task;
import com.example.pomodoro.fragment.FragmentTaskDetailListener;
import com.example.pomodoro.fragment.TaskDetailFragment;
import com.example.pomodoro.network.TaskNetworkContext;
import com.example.pomodoro.settings.SharedPrefs;
import com.example.pomodoro.signal.ProcessDialogState;
import com.example.pomodoro.signal.SignalProcessDialog;
import com.example.pomodoro.signal.SignalTokenHasExpired;
import com.example.tranh.pomodoro.R;
import com.example.pomodoro.fragment.FragmentTaskListener;
import com.example.pomodoro.fragment.PomodoroFragment;
import com.example.pomodoro.fragment.TaskFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindDrawable;
import butterknife.ButterKnife;

public class TaskActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentTaskDetailListener, FragmentTaskListener {
    private static final String TAG = "TaskActivity";
    @BindDrawable(R.drawable.ic_arrow_back_white_24dp)
    Drawable drawableback;

    private TaskNetworkContext taskNetworkContext;
    private TaskAdapter taskAdapter;
    private ProgressDialog progressDialog;


    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    toggle.setDrawerIndicatorEnabled(false);
                    toggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
                    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } else {
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setToolbarNavigationClickListener(null);
                }
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
//        setUI();


        ButterKnife.bind(this);
        onReplaceTaskListener();
        if (isConnected()) {
            DBContext.instance.getDBOnNetwork();
            progressDialog.show();
        }else{
            Toast.makeText(this, "No Internet Access - You can't add, edit or remove", Toast.LENGTH_SHORT).show();
        }
    }

    public void transactionFragment(Fragment fragment, boolean addtobackstack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        if (addtobackstack) {
            ft.replace(R.id.fl_main, fragment).addToBackStack(null).commit();
        } else {
            ft.replace(R.id.fl_main, fragment).commit();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.task, menu);

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
            goSetting();
            return true;
        } else if (id == R.id.action_color) {
            startChooseColor();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startChooseColor() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        ChooseColor chooseColor = new ChooseColor();

        fragmentTransaction.replace(R.id.frame_layout, chooseColor);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    public void backToLogin(){
        SharedPrefs.getInstance().clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void replaceFragment(Fragment fragment, boolean addToBackStack, boolean useanimation) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (useanimation) {
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }
        if (addToBackStack) {
            ft.replace(R.id.fl_main, fragment).addToBackStack(null).commit();
        } else {
            ft.replace(R.id.fl_main, fragment).commit();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onReplaceTaskDetailListener(Task task, int position) {
        if (task == null) {
            TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
            taskDetailFragment.setTitle("Create Task");
            taskDetailFragment.setPositionTask(-1);
            replaceFragment(taskDetailFragment, true, true);
        } else {
            TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
            taskDetailFragment.setTitle("Edit Task");
            taskDetailFragment.setTask(task);
            taskDetailFragment.setPositionTask(position);
            replaceFragment(taskDetailFragment, true, true);
        }
    }

    @Override
    public void onReplaceTaskListener() {
        replaceFragment(new TaskFragment(), false, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onTokenHasExpired(SignalTokenHasExpired signalTokenHasExpired){
        if(signalTokenHasExpired.isExpired()==true){
            backToLogin();
        }
    }
    @Subscribe
    public void onEvent(Task task) {
        PomodoroFragment pomodoroFragment = new PomodoroFragment();
        getSupportActionBar().setTitle(task.getName());
        replaceFragment(pomodoroFragment, true, true);
    }

    @Subscribe
    public void onProgressDialog(SignalProcessDialog signalProcessDialog){
        switch (signalProcessDialog.getProcessDialogState()){
            case START:
                progressDialog.show();
                break;
            case HIDE:
                progressDialog.hide();
                break;
            case DISMISS:
                progressDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

}
