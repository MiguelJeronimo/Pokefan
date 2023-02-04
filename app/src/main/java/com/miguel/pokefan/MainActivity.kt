package com.miguel.pokefan

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.miguel.pokefan.databinding.ActivityMainBinding
import com.miguel.pokefan.utilidades.ThemesLightDark

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var temas = ThemesLightDark()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        // en este when validaremos si no tiene tema el shared y de no ser asi almacenar el tema claro
        val temaa = temas.MostrarTema(AppCompatDelegate.getDefaultNightMode(), this) != null
        if (temaa){
            AppCompatDelegate.setDefaultNightMode(
                temas.MostrarTema(
                    AppCompatDelegate.MODE_NIGHT_NO,
                    applicationContext
                )!!.toInt()
            )
        } else{
            temas.GuardarTema(AppCompatDelegate.MODE_NIGHT_NO, applicationContext)
            AppCompatDelegate.setDefaultNightMode(
                temas.MostrarTema(
                    AppCompatDelegate.MODE_NIGHT_NO,
                    applicationContext
                )!!.toInt()
            )
        }

        //agregando los iconos de modo claro y obscuro al switch
        val lighTheme = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO
        if(lighTheme){
            binding.imgMode.setImageResource(R.drawable.icon_dark_mode)
            binding.statusMode.isChecked = true
        } else{
            binding.imgMode.setImageResource(R.drawable.icon_light_mode)
        }

        binding.statusMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    binding.imgMode.setImageResource(R.drawable.icon_dark_mode)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    temas.GuardarTema(AppCompatDelegate.MODE_NIGHT_NO,this)
                    print(AppCompatDelegate.getDefaultNightMode())
                }
            } else{
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                    print(AppCompatDelegate.getDefaultNightMode())
                    binding.imgMode.setImageResource(R.drawable.icon_light_mode)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    temas.GuardarTema(AppCompatDelegate.MODE_NIGHT_YES,this)}
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}