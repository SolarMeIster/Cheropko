package ru.solarmeister.tinkoffexam

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ru.solarmeister.tinkoffexam.databinding.ActivityMainBinding
import ru.solarmeister.tinkoffexam.listfilms.ListFilmsFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.gray)
        window.navigationBarColor = getColor(R.color.gray)
        if (!isNetworkConnected()) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NetworkErrorFragment>(R.id.fragment_container)
            }
            return
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ListFilmsFragment>(R.id.fragment_container)
            }
        }
    }
}

fun Context.isNetworkConnected(): Boolean {
    val connectivity = getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager?
        ?: return false
    if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
        val nw = connectivity.activeNetwork ?: return false
        val actNw = connectivity.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
            else -> false
        }
    } else {
        val networkInfo = connectivity.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}