package `in`.formura.formuranotes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import `in`.formura.formuranotes.AddSite
import `in`.formura.formuranotes.R
import `in`.formura.formuranotes.SiteListModel

class SiteListAdapter(siteList:List<SiteListModel>, private var context: Context)//,private val dateSelectionListener: DateSelectionListener
    : RecyclerView.Adapter<SiteListAdapter.SiteViewHolder>() {
        private var siteList:List<SiteListModel> = ArrayList()
        init {
            this.siteList = siteList
        }
        inner class SiteViewHolder(view: View) : RecyclerView.ViewHolder(view){////,private val listener: DateSelectionListener
            var siteName: TextView =view.findViewById(R.id.txtSite_name)
            var siteAmount: TextView =view.findViewById(R.id.txtAmount_id)
            var amountName: TextView =view.findViewById(R.id.txtName)
            var finalAmount: TextView =view.findViewById(R.id.finalAmount)
            var balance:TextView=view.findViewById(R.id.balance_id)
             var date: TextView =view.findViewById(R.id.tv_date)
            //var edit:TextView = view.findViewById(R.id.btn_edit)

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
            val view= LayoutInflater.from(context).inflate(R.layout.recycler_site_list,parent,false)
            return SiteViewHolder(view)
        }
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("StringFormatInvalid", "SetTextI18n", "SuspiciousIndentation")
        override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
            val site=siteList[position]
            holder.siteName.text="${context.getString(R.string.site_name)}:${site.sites}"
            holder.siteAmount.text="${context.getString(R.string.start_amount)}:${site.amount}"
            holder.amountName.text="${context.getString(R.string.name)}:${site.amountName}"
            holder.finalAmount.text="${context.getString(R.string.amount)}:${site.useAddAmount}"
            holder.balance.text="${context.getString(R.string.balance)}:${site.balance}"
            holder.date.text = site.date
            //holder.bind(position) //holder.date.text=site.date
            holder.itemView.setOnClickListener {
                val intent= Intent(context, AddSite::class.java)
                intent.putExtra("Mode","E")
                intent.putExtra("Id",site.id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
        override fun getItemCount(): Int {
            return siteList.size
        }
}
