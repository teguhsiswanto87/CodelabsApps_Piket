package id.codelabs.codelabsapps_piket.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.utils.Utils
import id.codelabs.codelabsapps_piket.model.ModelItem

class PiketAdapter(var callback: OnClickButtonItemPiketListListener) :
    RecyclerView.Adapter<PiketAdapter.PiketViewHolder>() {
    var list = ArrayList<ModelItem>()
        @SuppressLint("DefaultLocale")
        set(value) {
            val temp = ArrayList<ModelItem>()
            value.forEach {
                if (it.status!!.toUpperCase() != "SUDAH") {
                    temp.add(it)
                }
            }
            field = temp
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiketViewHolder {
        return PiketViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_piket,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PiketViewHolder, position: Int) {
        holder.name.text = list[position].namaAnggota
        holder.duty.text = list[position].jenisPiket
        Glide.with(holder.itemView.context)
            .load(R.drawable.loading)
            .into(holder.ivLoading)
        holder.btnSelesai.setOnClickListener {
            callback.onClickButtonSelesaiItemPiket(
                list[position],
                holder.ivLoading,
                holder.btnSelesai
            )
        }

        if (list[position].nim != Utils.getSharedPreferences(Utils.SAVED_NIM)) {
            holder.btnSelesai.visibility = View.GONE
        }
        if (!list[position].diperiksaOleh.isNullOrEmpty()) {
            holder.btnSelesai.visibility = View.GONE
        }
    }

    class PiketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tv_nama)
        var duty: TextView = itemView.findViewById(R.id.tv_duty)
        var btnSelesai: Button = itemView.findViewById(R.id.btn_selesai)
        var ivLoading: ImageView = itemView.findViewById(R.id.iv_loading)
    }

    interface OnClickButtonItemPiketListListener {
        fun onClickButtonSelesaiItemPiket(member: ModelItem, loadingView: ImageView, btn: Button)
    }

}