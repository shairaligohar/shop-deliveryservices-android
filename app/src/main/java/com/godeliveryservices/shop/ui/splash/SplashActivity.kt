package com.godeliveryservices.shop.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.godeliveryservices.shop.MainActivity
import com.godeliveryservices.shop.R
import com.godeliveryservices.shop.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

//    private val viewModel: SplashViewModel by obtainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val isUserLoggedIn: Boolean =
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
                .getBoolean("LoggedIn", false)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity


            if (isUserLoggedIn)
                startActivity(Intent(this, MainActivity::class.java))
            else
                startActivity(Intent(this, LoginActivity::class.java))

            // close this activity
            finish()
        }, 3000)
//        setupViews()
//        setupObservers()
//        viewModel.start()

    }

    private fun setupViews() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            app_logo.transitionName = getString(R.string.transition_appLogo)
//        }
    }

    private fun setupObservers() {
//        viewModel.navigate.observe(this, EventObserver { event ->
//            when (event) {
//                SplashViewModel.Events.NAVIGATE_TO_LOGIN -> {
//                    val intent: Intent = LoginActivity.newIntent(context = this)
//                    val options: ActivityOptions = ActivityOptions.makeCustomAnimation(
//                        this,
//                        R.anim.slide_in_left,
//                        R.anim.slide_out_left
//                    )
//                    startActivity(intent, options.toBundle())
//                    supportFinishAfterTransition()
//                }
//            }
//        })
    }
}
