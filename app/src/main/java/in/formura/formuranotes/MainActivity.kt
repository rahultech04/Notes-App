package `in`.formura.formuranotes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import `in`.formura.formuranotes.adapter.SiteListAdapter
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
class MainActivity : AppCompatActivity() {
    private var siteList:List<SiteListModel> = ArrayList()
    private var dbHelper:DBHelper?=null
    private var linearLayoutManager: LinearLayoutManager?=null
    private lateinit var recyclerSite: RecyclerView
    private var siteListAdapter: SiteListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerSite=findViewById(R.id.rv_list)

        val btnAdd = findViewById<Button>(R.id.btn_add_items)
        btnAdd.setOnClickListener {
            val i = Intent(applicationContext,AddSite::class.java)
            startActivity(i)
        }

        dbHelper = DBHelper(this)
        fetchList()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun fetchList(){
        siteList=dbHelper!!.getAllSite()
        siteListAdapter = SiteListAdapter(siteList,applicationContext)
        linearLayoutManager= LinearLayoutManager(applicationContext)
        recyclerSite.layoutManager=linearLayoutManager
        recyclerSite.adapter= siteListAdapter
        siteListAdapter?.notifyDataSetChanged()
    }
}