package com.kotlin.kotlinprojectbase

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.kotlin.kotlinprojectbase.adapter.DrawerCustomAdapter
import com.kotlin.kotlinprojectbase.contract.AppConstant
import com.kotlin.kotlinprojectbase.contract.MainContract
import com.kotlin.kotlinprojectbase.model.ItemObject
import com.kotlin.kotlinprojectbase.presenter.MainActivityPresenter
import com.kotlin.kotlinprojectbase.uibase.BaseActivity
import com.kotlin.kotlinprojectbase.uibase.CustomRegularTextView
import com.kotlin.kotlinprojectbase.utilities.CommonUtilities
import com.kotlin.kotlinprojectbase.utilities.NetworkChangeReceiver
import com.versionsolution.kotlindemo.R
import com.versionsolution.kotlindemo.view.fragments.*
import java.util.ArrayList
import android.content.Intent
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import com.versionsolution.kotlindemo.utilities.LocaleHelper


open class BaseDrawerActivity: BaseActivity(),AdapterView.OnItemClickListener, View.OnClickListener, AppConstant, MainContract.View  {
    override fun initView() {
    }

    override fun setViewData(data: String) {
    }

    private val exitFlag = false
    private val TAG = BaseDrawerActivity::class.java.name
    lateinit var toolbar: Toolbar
    lateinit var imageButton_switch: ImageButton
    lateinit var imageButton_user:ImageButton
    lateinit var imageButton_logout:ImageButton
    //public ImageButton imageButtonMenu;
    private var mDrawerTitle: CharSequence? = null
    private var mTitle:CharSequence? = null
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mDrawerToggle: ActionBarDrawerToggle
    private val listViewItems = ArrayList<ItemObject>()
    lateinit var content_frame: FrameLayout
    private var mDrawerList: ListView? = null
    lateinit var avatar: ImageView
    lateinit var imageView_carky: TextView
    lateinit var search_bar: SearchView
    lateinit var avatar_name: TextView
    lateinit var user_email:TextView
    private val bm: Bitmap? = null
    private lateinit var headerView: LinearLayout


    //    private DatabaseHelper databaseHelper = null
    lateinit var drawer_lay: RelativeLayout
    lateinit var header_notification: LinearLayout
    lateinit var lyt_fev:LinearLayout
    private val spotsDialog: ProgressDialog? = null
    private lateinit var notification: CustomRegularTextView;
    private var fev_count:CustomRegularTextView? = null
    private lateinit var toolbar_title: CustomRegularTextView;
    private var not_count: CustomRegularTextView? = null
    internal var networkChangeReceiver: NetworkChangeReceiver? = null
    internal var bundle: Bundle? = null
    var menuItemPosition = 0
    var flag: Int = 0
    internal var ids: Int = 0
    private var presenter: MainContract.Presenter? = null
    private var commonUtilities: CommonUtilities? = null
    private lateinit var fab:FloatingActionButton
    private val mLanguageCode = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)
        presenter = MainActivityPresenter(this)
        commonUtilities = CommonUtilities()
        registerReceiver(networkChangeReceiver, IntentFilter("INTERNET_LOST"))
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar_title = findViewById(R.id.toolbar_title)
        not_count = findViewById(R.id.not_count)

       /* if (SharedPreference.getUserId() == null) {
            not_count.setVisibility(View.GONE)
        } else {
            not_count.setVisibility(View.VISIBLE)
        }
*/
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, HomeFragment.Companion.newInstance())
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .commitAllowingStateLoss();
        // getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();

        //        toolbar.inflateMenu(R.menu.base);
        //imageButtonMenu = (ImageButton) toolbar.findViewById(R.id.imageButtonMenu);
        imageButton_switch = toolbar.findViewById(R.id.imageButton_switch)
        imageButton_user = toolbar.findViewById(R.id.imageButton_user)
        imageButton_logout = toolbar.findViewById(R.id.imageButton_logout)
//        search_bar = toolbar.findViewById(R.id.search_bar)
        imageView_carky = toolbar.findViewById(R.id.toolbar_title)
        header_notification = toolbar.findViewById(R.id.header_notification)
        lyt_fev = toolbar.findViewById(R.id.lyt_fev)
        notification = toolbar.findViewById(R.id.notification)
        fev_count = toolbar.findViewById(R.id.fev_count)
        imageButton_switch.setOnClickListener(this)
        imageButton_user.setOnClickListener(this)
        imageButton_logout.setOnClickListener(this)
      //  imageButtonMenu.setOnClickListener(this);

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initDrawers()
        toolbar.setOnClickListener(this);

        imageButton_user?.setOnClickListener(this)
//        imageButton_logout.setOnClickListener { success_dialog(resources.getString(R.string.logout_alert)) }



      fab=findViewById(R.id.fab)
        fab.setOnClickListener(View.OnClickListener {
            LocaleHelper.setLocale(this, mLanguageCode);

            //It is required to recreate the activity to reflect the change in UI.
            recreate();
        })

    }

    @SuppressLint("RestrictedApi")
    fun initDrawers() {
        mTitle = title
        mDrawerTitle = mTitle
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mDrawerList = findViewById<ListView>(R.id.left_drawer)
        content_frame = findViewById(R.id.content_frame)
        headerView = layoutInflater.inflate(R.layout.nav_header_layout, null) as LinearLayout
        ////        footerView = (RelativeLayout) getLayoutInflater().inflate(R.layout.nav_footer_main, null);
        avatar = findViewById(R.id.profile_image)
        avatar_name = findViewById(R.id.user_name)
        user_email = findViewById(R.id.user_email)
        drawer_lay = findViewById(R.id.drawer_lay)


        listViewItems.clear()
        listViewItems.add(ItemObject(resources.getString(R.string.home), R.drawable.ic_action_home, false))
        listViewItems.add(ItemObject(resources.getString(R.string.nav_noti), R.drawable.ic_action_notifications_green, false))
        listViewItems.add(ItemObject(resources.getString(R.string.nav_setting), R.drawable.ic_action_settings, false))
        listViewItems.add(ItemObject(resources.getString(R.string.nav_about_us), R.drawable.ic_action_info_outline, false))
        listViewItems.add(ItemObject(resources.getString(R.string.nav_feedback), R.drawable.icons_feedback, false))
        listViewItems.add(ItemObject(resources.getString(R.string.nav_rating), R.drawable.icons_star_white, false))



        mDrawerList?.setAdapter(DrawerCustomAdapter(this@BaseDrawerActivity, listViewItems))
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics)
        val params = drawer_lay.layoutParams as DrawerLayout.LayoutParams
        params.width = width.toInt()
        drawer_lay.layoutParams = params
        mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView!!)
                mDrawerLayout.openDrawer(drawer_lay)
                supportActionBar?.setTitle(mTitle)
                supportInvalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView!!)
                mDrawerLayout.closeDrawer(drawer_lay)
                supportActionBar?.setTitle(mDrawerTitle)
                supportInvalidateOptionsMenu()
            }
        }

        mDrawerToggle.toolbarNavigationClickListener = View.OnClickListener {
            if (menuItemPosition != 0) {
                toolbar_title!!.setText(getString(R.string.home_drawer));
                      getSupportFragmentManager().beginTransaction()
                              .replace(R.id.content_frame, HomeFragment.Companion.newInstance(), getString(R.string.home_drawer))
                              .commitAllowingStateLoss();
                      menuItemPosition = 0;
            } else {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }


        mDrawerToggle.isDrawerIndicatorEnabled = true
        mDrawerLayout.setDrawerListener(mDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_action_dehaze)
        mDrawerList?.setOnItemClickListener(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith("android.webkit.")) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.left - scrcoords[0]
            val y = ev.rawY + view.top - scrcoords[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom)
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.window.decorView.applicationWindowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        if (isNetworkAvailable()) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START)
            }
        } else {
            commonUtilities?.commondialog("OK", resources.getString(R.string.app_name), resources.getString(R.string.app_name), R.mipmap.ic_launcher, this)
        }
        consumerLoginDrawer(position)

    }

    fun consumerLoginDrawer(position: Int) {

        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }

        if (position == 0) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_dehaze)
        } else {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_dehaze)
        }

        when (position) {
             0-> {
                 toolbar_title!!.setText(getString(R.string.home_drawer));
                 //setTabTextColor();
                 getSupportFragmentManager().beginTransaction()
                         .add(R.id.frame_layout, HomeFragment.Companion.newInstance())
                         .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                         .commitAllowingStateLoss();
             }
            1->{
                toolbar_title!!.setText(getString(R.string.notifications_drawer));
                //setTabTextColor();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_layout, NotificationFragment.Companion.newInstance())
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .commitAllowingStateLoss();
            }
            2->{
                toolbar_title!!.setText(getString(R.string.nav_setting));
                //setTabTextColor();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_layout, SettingFragment.Companion.newInstance())
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .commitAllowingStateLoss();
            }

            3->{
                toolbar_title!!.setText(getString(R.string.nav_about_us));
                //setTabTextColor();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_layout, AboutUsFragment.Companion.newInstance())
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .commitAllowingStateLoss();
            }
            4->{
                toolbar_title!!.setText(getString(R.string.nav_feedback));
                //setTabTextColor();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_layout, FeedbackFragment.Companion.newInstance())
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .commitAllowingStateLoss();
            }
            5->{
                val appPackageName = packageName // getPackageName() from Context or Activity object
                try {
                   // startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store?hl=en")))
                } catch (anfe: android.content.ActivityNotFoundException) {
                    //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store?hl=en")))
                }

            }




        }
    }


    override fun onClick(view: View) {


        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }

        when (view.getId()) {

        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
            if (flag == 0) {
                toolbar_title?.setText(resources.getString(R.string.app_name))
//                setTabTextColor()
            } else if (flag == 1) {
                toolbar_title?.setText(resources.getString(R.string.app_name))
                flag = 0
            }

        }
    }

}