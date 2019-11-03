package id.codelabs.codelabsapps_piket.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.model.ModelItem

class BelumPiketAdapter : RecyclerView.Adapter<BelumPiketAdapter.BelumPiketViewHolder>(){
    var list = ArrayList<ModelItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BelumPiketViewHolder {
        return BelumPiketViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_belum_piket,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BelumPiketViewHolder, position: Int) {
        holder.name.text = list[position].namaAnggota
        holder.duty.text = list[position].jenisPiket
    }

    class BelumPiketViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.tv_nama)
        var duty : TextView = itemView.findViewById(R.id.tv_duty)
    }

}