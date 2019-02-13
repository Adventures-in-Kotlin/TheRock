package com.satansminion.myhell.therockapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.TheRockStyle)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController
        supportActionBar?.title = resources.getString(R.string.app_name)

        appBarConfiguration = AppBarConfiguration(
            (
                setOf(R.id.mainFragment)
                )
        )

        setupActionBar(navController, appBarConfiguration)

//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.about_dest -> {
//                val options = navOptions {
//                    anim {
//                        enter = R.anim.slide_in_left
//                        exit = R.anim.slide_out_left
//                        popEnter = R.anim.slide_in_right
//                        popExit = R.anim.slide_out_right
//                    }
//                }
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.about_dest, null, getAnimOptions())
                return true
            }
            R.id.fav_dest -> {
//                val options = navOptions {
//                    anim {
//                        enter = R.anim.slide_in_left
//                        exit = R.anim.slide_out_left
//                        popEnter = R.anim.slide_in_right
//                        popExit = R.anim.slide_out_right
//                    }
//                }
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.fav_dest, null, getAnimOptions())
                return true
            }
            R.id.mainFragment -> {
//                val options = getAnimOptions()
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.mainFragment, null, getAnimOptions())
//                Toast.makeText(baseContext,getString(R.string.loading_playlist_toast_message),Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAnimOptions(): NavOptions {
        return navOptions {
            anim {
                enter = R.anim.slide_in_left
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_right
                popExit = R.anim.slide_out_right
            }
        }
    }

    private fun setupActionBar(navController: NavController, appBarConfig: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.my_nav_host_fragment).navigateUp()


}
