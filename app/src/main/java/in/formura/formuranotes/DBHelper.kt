package `in`.formura.formuranotes

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, dbname,null,1){
    companion object{
        private const val dbname="notes.db"
        private const val TABLE="sitesInfo"
        private const val ID="id"
        private const val DATE ="date"
        private const val SITES="sites"
        private const val AMOUNT="amount"
        private const val Name_Of_The_Amount="name_of_the_amount"
        private const val  FinalAmount="finalAmount"
        private const val BALANCE="balance"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val query= "CREATE TABLE $TABLE($ID INTEGER PRIMARY KEY,$DATE TEXT,$SITES TEXT,$AMOUNT TEXT,$Name_Of_The_Amount TEXT,$FinalAmount TEXT,$BALANCE TEXT);"
        p0?.execSQL(query)
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTable= "DROP TABLE IF EXISTS $TABLE"
        p0?.execSQL(dropTable)
        onCreate(p0)
    }

    fun addSite(siteListModel: SiteListModel):Boolean{
        val db=this.writableDatabase
        val values= ContentValues()
        values.put(DATE,siteListModel.date)
        values.put(SITES,siteListModel.sites)
        values.put(AMOUNT,siteListModel.amount)
        values.put(Name_Of_The_Amount,siteListModel.amountName)
        values.put(FinalAmount,siteListModel.useAddAmount)
        values.put(BALANCE,siteListModel.balance)
        val success=db.insert(TABLE,null,values)
        db.close()
        return (Integer.parseInt("$success")!= -1)
    }
    @SuppressLint("Range")
    fun getSite(id:Int):SiteListModel{
        val siteListModel=SiteListModel()
        val db=writableDatabase
        val selectQuery="SELECT * FROM $TABLE WHERE $ID=$id"
        val cursor=db.rawQuery(selectQuery,null)

        cursor?.moveToFirst()
        siteListModel.id= Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        siteListModel.date = cursor.getString(cursor.getColumnIndex(DATE))
        siteListModel.sites = cursor.getString(cursor.getColumnIndex(SITES))
        siteListModel.amount = cursor.getString(cursor.getColumnIndex(AMOUNT))
        siteListModel.amountName = cursor.getString(cursor.getColumnIndex(Name_Of_The_Amount))
        siteListModel.useAddAmount = cursor.getString(cursor.getColumnIndex(FinalAmount))
        siteListModel.balance=cursor.getString(cursor.getColumnIndex(BALANCE))
        cursor.close()
        return siteListModel
    }
    fun deleteSite(id: Int):Boolean{
        val db=this.writableDatabase
        val success=db.delete(TABLE, "$ID=?", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") !=-1
    }
    fun updateSite(siteListModel: SiteListModel):Boolean{
        val db=this.writableDatabase
        val values= ContentValues()
        values.put(SITES, siteListModel.sites)
        values.put(AMOUNT,siteListModel.amount)
        values.put(Name_Of_The_Amount,siteListModel.amountName)
        values.put(FinalAmount,siteListModel.useAddAmount)
        values.put(BALANCE,siteListModel.balance)
        val success=db.update(TABLE,values, "$ID=?", arrayOf(siteListModel.id.toString()))
        db.close()
        return Integer.parseInt("$success")  !=-1
    }
    @SuppressLint("Range")
    fun getAllSite():List<SiteListModel>{
        val siteList = ArrayList<SiteListModel>()
        val db= writableDatabase
        val selectQuery = "SELECT * FROM $TABLE"
        val cursor=db.rawQuery(selectQuery,null)
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                    val  site= SiteListModel()
                    site.id= Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    site.date = cursor.getString(cursor.getColumnIndex(DATE))
                    site.sites=cursor.getString(cursor.getColumnIndex(SITES))
                    site.amount=cursor.getString(cursor.getColumnIndex(AMOUNT))
                    site.amountName=cursor.getString(cursor.getColumnIndex(Name_Of_The_Amount))
                    site.useAddAmount=cursor.getString(cursor.getColumnIndex(FinalAmount))
                    site.balance=cursor.getString(cursor.getColumnIndex(BALANCE))
                    siteList.add(site)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return siteList
    }
}
