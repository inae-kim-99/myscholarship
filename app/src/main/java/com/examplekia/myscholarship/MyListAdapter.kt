package com.examplekia.myscholarship

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.examplekia.myscholarship.data.MyData
import com.examplekia.myscholarship.menu.scholarship.InfoActivity

class MyListAdapter(val pnumList: ArrayList<String>?,val context: Context,val activity: FragmentActivity?) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    var myDBHelper:MyDBHelper = MyDBHelper(context)

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname:TextView
        var pinstitution:TextView
        var papplydate:TextView
        var item:LinearLayout

        init{
            pname = itemView.findViewById(R.id.pname)
            pinstitution = itemView.findViewById(R.id.pinstitution)
            papplydate = itemView.findViewById(R.id.papplydate)
            item = itemView.findViewById(R.id.item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if(pnumList == null)
            return myDBHelper.getRecordCount()
        else
            return pnumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data:MyData?
        if(pnumList == null)
            data = myDBHelper.showItem((position+1).toString())
        else
            data = myDBHelper.showItem(pnumList[position])
        //신청기간
//        var today = Date()
//        var date = data?.papplydate
//        if(date?.substring(0,1) == "- "){
//            date = date.substring(2,date.length-1)
//            date = date.trim()
//            date = date.replace(" ","")
//            var date2 = date.split("~")
//
//        }
        holder.pname.text = data?.PNAME
        holder.pinstitution.text = data?.PINSTITUTION
        holder.papplydate.text = data?.PAPPLY_DATE

        holder.item.setOnClickListener{
            val intent = Intent(context, InfoActivity::class.java)
            if(pnumList == null)
                intent.putExtra("position",(position+1).toString())
            else
                intent.putExtra("position",pnumList[position])
            context.startActivity(intent)
            activity?.overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left)
        }
    }
}