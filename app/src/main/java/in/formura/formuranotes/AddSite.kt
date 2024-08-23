package `in`.formura.formuranotes

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class AddSite: AppCompatActivity() {
    private var isEditMode = false
    private var dbHelper: DBHelper? = null
    private var success: Boolean = false
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_site_activity)

        val enterSiteName = findViewById<EditText>(R.id.siteName_id)
        val enterStartAmount = findViewById<EditText>(R.id.startAmount_id)
        val enterAmountName = findViewById<EditText>(R.id.amountName_id)
        val enterAmount = findViewById<EditText>(R.id.amount_id)
        val plusBtn = findViewById<Button>(R.id.plusBtn)
        val minusBtn = findViewById<Button>(R.id.minusBtn)
        val textViewDate = findViewById<TextView>(R.id.tv_date)
        //EditText design
        enterSiteName.setBackgroundColor(Color.WHITE)
        enterSiteName.setTextColor(Color.BLACK)

        enterStartAmount.setBackgroundColor(Color.WHITE)
        enterStartAmount.setTextColor(Color.BLACK)

        enterAmountName.setBackgroundColor(Color.WHITE)
        enterAmountName.setTextColor(Color.BLACK)

        enterAmount.setBackgroundColor(Color.WHITE)
        enterAmount.setTextColor(Color.BLACK)
        dbHelper = DBHelper(this)
        if (intent != null && intent.getStringExtra("Mode") == "E") { //update data
            isEditMode = true
            plusBtn.text = getString(R.string.update)
            minusBtn.text = getString(R.string.delete)
            val site = dbHelper!!.getSite(intent.getIntExtra("Id", 0))
            enterSiteName.setText(site.sites)
            enterStartAmount.setText(site.amount)
            enterAmountName.setText(site.amountName)
            enterAmount.setText(site.balance)// date.text = site.useAddAmount
        } else { //insert new data
            isEditMode = false
            plusBtn.visibility = View.VISIBLE
            minusBtn.visibility = View.VISIBLE //date.visibility = View.VISIBLE

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            textViewDate.text = formatted
        }
        plusBtn.setOnClickListener {
            val siteListModel = SiteListModel()
            if (isEditMode) {
                if (enterSiteName.text.isEmpty() && enterStartAmount.text.isEmpty() && enterAmountName.text.isEmpty() && enterAmount.text.isEmpty()) {
                    Toast.makeText(this@AddSite, "must enter all date", Toast.LENGTH_LONG).show()
                } else {
                    siteListModel.id = intent.getIntExtra("Id", 0)
                    siteListModel.sites = enterSiteName.text.toString()
                    siteListModel.amount = enterStartAmount.text.toString()
                    siteListModel.amountName = enterAmountName.text.toString()
                    siteListModel.balance = enterAmount.text.toString()
                    success = dbHelper?.updateSite(siteListModel) as Boolean
                    Toast.makeText(this@AddSite, " Successfully Updated", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (enterSiteName.text.isEmpty() && enterStartAmount.text.isEmpty() && enterAmountName.text.isEmpty() && enterAmount.text.isEmpty()) {
                    Toast.makeText(this@AddSite, "must enter all date", Toast.LENGTH_LONG).show()
                } else {
                    siteListModel.id = intent.getIntExtra("Id", 0)
                    siteListModel.date = textViewDate.text.toString()
                    siteListModel.sites = enterSiteName.text.toString()
                    siteListModel.amount = enterStartAmount.text.toString()
                    siteListModel.amountName = enterAmountName.text.toString()
                    siteListModel.useAddAmount = enterAmount.text.toString()
                    val balance = enterAmount.text.toString().toInt()
                    siteListModel.balance = (siteListModel.amount.toInt() + balance).toString()
                    success = dbHelper?.addSite(siteListModel) as Boolean
                    Toast.makeText(this@AddSite, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }
            }
            if (success) {
                val i = Intent(applicationContext, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Something Went Wrong!!", Toast.LENGTH_LONG)
                    .show()
            }
        }
        minusBtn.setOnClickListener {//-
            val siteListModel = SiteListModel()
            if (isEditMode) {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Info")
                    .setMessage("Click Yes If you Went to Delete The Information")
                    .setPositiveButton("YES") { dialog, _ ->
                        val success = dbHelper?.deleteSite(intent.getIntExtra("Id", 0)) as Boolean
                        if (success)
                            finish()
                        dialog.dismiss()
                    }
                    .setNegativeButton("NO") { dialog, _ ->
                        dialog.dismiss()
                    }
                dialog.show()
            } else {
                if (enterSiteName.text.isEmpty() && enterStartAmount.text.isEmpty() && enterAmountName.text.isEmpty() && enterAmount.text.isEmpty()) {
                    Toast.makeText(this@AddSite, "must enter all date", Toast.LENGTH_LONG).show()
                } else {
                    siteListModel.id = intent.getIntExtra("Id", 0)
                    siteListModel.date = textViewDate.text.toString()
                    siteListModel.sites = enterSiteName.text.toString()
                    siteListModel.amount = enterStartAmount.text.toString()
                    siteListModel.amountName = enterAmountName.text.toString()
                    siteListModel.useAddAmount = enterAmount.text.toString()
                    val balance = enterAmount.text.toString().toInt()
                    siteListModel.balance = (siteListModel.amount.toInt() - balance).toString()
                    success = dbHelper?.addSite(siteListModel) as Boolean
                    Toast.makeText(this@AddSite, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }
                if (success) {
                    val i = Intent(applicationContext, MainActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Something Went Wrong!!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}



