package com.libers.unnc.liber.activities;import android.app.AlertDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.support.design.widget.NavigationView;import android.support.v4.app.Fragment;import android.support.v4.app.FragmentPagerAdapter;import android.support.v4.view.GravityCompat;import android.support.v4.view.ViewPager;import android.support.v4.widget.DrawerLayout;import android.support.v7.app.ActionBarDrawerToggle;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.support.v7.widget.Toolbar;import android.util.Log;import android.view.MenuItem;import android.view.View;import android.widget.Button;import android.widget.TextView;import com.github.clans.fab.FloatingActionButton;import com.github.clans.fab.FloatingActionMenu;import com.libers.unnc.liber.R;import com.libers.unnc.liber.fragments.MyBookFragment;import com.libers.unnc.liber.model.bean.State;import com.libers.unnc.liber.model.bean.User;import com.mikepenz.iconics.context.IconicsContextWrapper;import java.util.ArrayList;import java.util.Arrays;import java.util.List;import com.libers.unnc.liber.R;import com.libers.unnc.liber.view.ViewPagerIndicator;//import com.libers.unnc.liber.fragments.FavoriteFragment;  //(BookGrid -> Favorite)import com.libers.unnc.liber.fragments.BookGridFragment;import com.libers.unnc.liber.fragments.GuidelistFragment;import org.litepal.crud.DataSupport;public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {    // ViewPager    private ViewPager viewPager;    private FragmentPagerAdapter pagerAdapter;    public static final String TAG = "MainActivity";    // ViewPagerIndicator    private ViewPagerIndicator viewPagerIndicator;    private List<String> titles = Arrays.asList("GuideList", "Your Borrowed Book", "Favorites");    int log_state;    State check;    // Fragment    private List<Fragment> fragments = new ArrayList<Fragment>();    final Context context = this;    @Override    protected void attachBaseContext(Context newBase) {        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));    }    @Override    protected void onCreate(Bundle savedInstanceState) {        //insert the data        DataSupport.deleteAll(User.class);        check = DataSupport.findLast(State.class);        if(check == null){            State default_state = new State();            default_state.setState(0);            default_state.setUsername("Default");            default_state.save();            check  = DataSupport.findLast(State.class);        }else{            log_state = check.getState();        }        User user1 = new User();        user1.setUsername("Liber");        user1.setPassword("12345");        user1.setBowrrowTime("2017-01-13");        user1.setDueTime("2017-01-25");        user1.setIsbn13("9780861713110");        User user2 = new User();        user2.setUsername("Liber");        user2.setPassword("12345");        user2.setBowrrowTime("2017-02-13");        user2.setDueTime("2017-02-27");        user2.setIsbn13("9781933947570");        User user3 = new User();        user3.setUsername("Liber");        user3.setPassword("12345");        user3.setBowrrowTime("2017-03-13");        user3.setDueTime("2017-04-25");        user3.setIsbn13("9780571239634");        user1.save();        user2.save();        user3.save();        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        Log.e(TAG, "onCreate: ");        // 顶部ToolBar        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);        setSupportActionBar(toolbar);        // 右下角浮动菜单        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fabmenu);        fabMenu.setClosedOnTouchOutside(true);        // 右下角浮动按钮 - 扫一扫        final FloatingActionButton fabBtnScanner = (FloatingActionButton) findViewById(R.id.fab_scanner);        fabBtnScanner.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                fabMenu.close(true);                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);                startActivity(intent);            }        });        // 右下角浮动按钮 - 添加        FloatingActionButton fabBtnAdd = (FloatingActionButton) findViewById(R.id.fab_add);        fabBtnAdd.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                fabMenu.close(true);                Intent intent = new Intent(MainActivity.this, SearchActivity.class);                startActivity(intent);            }        });        // 左上角 Menu 按钮        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);        drawer.setDrawerListener(toggle);        toggle.syncState();        // 菜单        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);        navigationView.setNavigationItemSelectedListener(this);        // Bottun -- Login activty    }    @Override    public void onBackPressed() {        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        if (drawer.isDrawerOpen(GravityCompat.START)) {            drawer.closeDrawer(GravityCompat.START);        } else {            super.onBackPressed();        }    }    @SuppressWarnings("StatementWithEmptyBody")    @Override    public boolean onNavigationItemSelected(MenuItem item) {        int id = item.getItemId();        if (id == R.id.nav_scanner) {            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);            startActivity(intent);        } else if (id == R.id.nav_add) {            Intent intent = new Intent(MainActivity.this, SearchActivity.class);            intent.putExtra("search_type", SearchActivity.SEARCH_NET);            startActivity(intent);        } else if (id == R.id.nav_about) {            Intent intent = new Intent(MainActivity.this, AboutActivity.class);            startActivity(intent);        } else if (id == R.id.nav_search) {            Intent intent = new Intent(MainActivity.this, SearchActivity.class);            intent.putExtra("search_type", SearchActivity.SEARCH_LOCAL);            startActivity(intent);        }        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        drawer.closeDrawer(GravityCompat.START);        return true;    }    @Override    protected void onResume() {        super.onResume();        Log.e(TAG, "onResume: ");        check = DataSupport.findLast(State.class);        if(check == null){            State default_state = new State();            default_state.setState(0);            default_state.setUsername("Default");            default_state.save();            check  = DataSupport.findLast(State.class);        }else{            log_state = check.getState();        }        Log.e(TAG, "onResume: "+ log_state);//        textViewObj  = (TextView) findViewById(R.id.logView);        final Button login_out = (Button) findViewById(R.id.btn_log);        if(log_state == 1){            login_out.setText("Logout");        }        if(log_state == 0){            login_out.setText("Login");        }        login_out.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                if (log_state == 0){                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);                    startActivity(intent);                }                else{ // Logout                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(                            context);                    // set title                    alertDialogBuilder.setTitle("Logout");                    // set dialog message                    alertDialogBuilder                            .setMessage("Are you sure to logout?")                            .setCancelable(false)                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {                                public void onClick(DialogInterface dialog,int id) {                                    // if this button is clicked, close                                    // current activity                                    login_out.setText("Login");                                    State logoutstate = new State();                                    logoutstate.setState(0);                                    logoutstate.save();                                    onResume();                                }                            })                            .setNegativeButton("No",new DialogInterface.OnClickListener() {                                public void onClick(DialogInterface dialog,int id) {                                    // if this button is clicked, just close                                    // the dialog box and do nothing                                    dialog.cancel();                                }                            });                    // create alert dialog                    AlertDialog alertDialog = alertDialogBuilder.create();                    // show it                    alertDialog.show();                }            }        });        // ViewPager        viewPager = (ViewPager) findViewById(R.id.view_pager);        // ViewPagerIndicator        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);        viewPagerIndicator.setTabItemTitles(titles);        // Fragment        fragments.clear();        fragments.add(GuidelistFragment.newInstance(GuidelistFragment.TYPE_ALL));        fragments.add(MyBookFragment.newInstance());        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE));        // PagerAdapter        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {            @Override            public int getCount() {                return fragments.size();            }            @Override            public Fragment getItem(int position) {                return fragments.get(position);            }        };        // 设置数据适配器        viewPager.setAdapter(pagerAdapter);        viewPagerIndicator.setViewPager(viewPager, 0);    }}