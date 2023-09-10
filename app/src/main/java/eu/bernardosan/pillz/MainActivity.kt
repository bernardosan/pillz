package eu.bernardosan.pillz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import eu.bernardosan.pillz.databinding.ActivityMainBinding
import eu.bernardosan.pillz.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mainViewModel = MainViewModel(Calendar.getInstance())

    companion object {
        private const val ID_HOME = 1
        private const val ID_DASHBOARD = 2
        private const val ID_NOTIFICATION = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}