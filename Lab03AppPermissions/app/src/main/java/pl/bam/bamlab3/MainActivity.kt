package pl.bam.bamlab3

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lab3.ApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val apiService: ApiService by lazy {
        val url = "https://jsonplaceholder.typicode.com/"
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("xxxxxxx Network Receiver", "Network change")
            checkNetworkStatus()
            sendApiRequest()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.activityButton)
        button.setOnClickListener {
            sendApiRequest()
        }

        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        val btnReadContacts: Button = findViewById(R.id.contacts)
        btnReadContacts.setOnClickListener {
            checkContactsPermissionAndRead()
        }

        val buttonInstalledApp: Button = findViewById(R.id.applicationsButton)
        buttonInstalledApp.setOnClickListener {
            getInstalledApps()
        }
    }

    private fun getInstalledApps() {
        val packageManager: PackageManager = packageManager
        val applications: List<ApplicationInfo> =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        for (applicationInfo in applications) {
            Log.d("xxxxxxx Applications", applicationInfo.loadLabel(packageManager).toString())
        }
    }

    private fun checkContactsPermissionAndRead() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            readContacts()
        }
    }

    @SuppressLint("Range")
    private fun readContacts() {
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.let {
            while (it.moveToNext()) {
                val contactName =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                Log.d("xxxxxxx User contacts", "Contact: $contactName")
            }
            it.close()
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun sendApiRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getPosts().execute()
                val posts = response.body()
                Log.d("xxxxxxx Response from Api",  posts.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("xxxxxxx Exceptgion from Api", "Exception: ${e.message}")
            }
        }
    }

    private fun checkNetworkStatus() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        Log.d("xxxxxxx Connection status", "Connected")
    }

    override fun onDestroy() {
        unregisterReceiver(networkReceiver)
        super.onDestroy()
    }

}