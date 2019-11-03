package id.codelabs.codelabsapps_piket.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.Utils
import id.codelabs.codelabsapps_piket.model.ModelItem

class SudahPiketAdapter(var callback : OnClickButtonItemSudahPiketListListener) : RecyclerView.Adapter<SudahPiketAdapter.PiketViewHolder>(){
    var list = ArrayList<ModelItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiketViewHolder {
        return PiketViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sudah_piket,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PiketViewHolder, position: Int) {
        holder.name.text = list[position].namaAnggota
        holder.duty.text = list[position].jenisPiket
        Glide.with(holder.itemView.context)
            .load(R.drawable.loading)
            .into(holder.ivLoading)
        holder.btnSelesai.setOnClickListener {
            callback.onClickButtonSelesaiItemSudahPiket(list[position],holder.ivLoading, holder.btnSelesai,holder.ivChecklist)
        }

        if(list[position].nim == Utils.getSharedPreferences(Utils.SAVED_NIM)){
            holder.btnSelesai.visibility = View.GONE
        }
        if(!list[position].diperiksaOleh.isNullOrEmpty()){
            holder.btnSelesai.visibility = View.GONE
            holder.ivChecklist.visibility = View.VISIBLE
        }
    }

    class PiketViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.tv_nama)
        var duty : TextView = itemView.findViewById(R.id.tv_duty)
        var btnSelesai : Button = itemView.findViewById(R.id.btn_selesai)
        var ivLoading : ImageView = itemView.findViewById(R.id.iv_loading)
        var ivChecklist : ImageView = itemView.findViewById(R.id.iv_checklist)
    }

    interface OnClickButtonItemSudahPiketListListener {
        fun onClickButtonSelesaiItemSudahPiket(member : ModelItem, loadingView : ImageView, btn : Button, checklist : ImageView)
    }

}