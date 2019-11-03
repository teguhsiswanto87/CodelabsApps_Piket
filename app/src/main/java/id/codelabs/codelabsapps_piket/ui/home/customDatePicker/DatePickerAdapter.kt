package id.codelabs.codelabsapps_piket.ui.home.customDatePicker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import id.codelabs.codelabsapps_piket.R
import java.lang.StringBuilder
import java.util.*

class DatePickerAdapter(
    var callback: OnClickItemCustomDatePickerListener,
    var calendar: Calendar,
    var context: Context,
    numberOfWeek: Int,
    recyclerView: RecyclerView
) : RecyclerView.Adapter<DatePickerAdapter.DatePickerViewHolder>() {

    private var listDatePicker = CustomDatePickerUtils.generateDate(calendar, numberOfWeek)


    init {
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = this

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        if (CustomDatePickerUtils.selectedDateMarker == null) {
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(numberOfWeek / 2)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatePickerViewHolder {
        return DatePickerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_date_picker,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listDatePicker.size

    override fun onBindViewHolder(holder: DatePickerViewHolder, position: Int) {

        holder.tvC1.text = listDatePicker[position][0].date.toString()
        holder.tvC2.text = listDatePicker[position][1].date.toString()
        holder.tvC3.text = listDatePicker[position][2].date.toString()
        holder.tvC4.text = listDatePicker[position][3].date.toString()
        holder.tvC5.text = listDatePicker[position][4].date.toString()
        holder.tvC6.text = listDatePicker[position][5].date.toString()
        holder.tvC7.text = listDatePicker[position][6].date.toString()

        holder.llD1.setOnClickListener {
            onItemClickListener(
                position,
                0,
                holder.tvC1,
                holder.ivMarker1
            )
        }
        holder.llD2.setOnClickListener {
            onItemClickListener(
                position,
                1,
                holder.tvC2,
                holder.ivMarker2
            )
        }
        holder.llD3.setOnClickListener {
            onItemClickListener(
                position,
                2,
                holder.tvC3,
                holder.ivMarker3
            )
        }
        holder.llD4.setOnClickListener {
            onItemClickListener(
                position,
                3,
                holder.tvC4,
                holder.ivMarker4
            )
        }
        holder.llD5.setOnClickListener {
            onItemClickListener(
                position,
                4,
                holder.tvC5,
                holder.ivMarker5
            )
        }
        holder.llD6.setOnClickListener {
            onItemClickListener(
                position,
                5,
                holder.tvC6,
                holder.ivMarker6
            )
        }
        holder.llD7.setOnClickListener {
            onItemClickListener(
                position,
                6,
                holder.tvC7,
                holder.ivMarker7
            )
        }

        if (CustomDatePickerUtils.selectedDateMarker == null) {
            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> {
                    changeSelectedItem(holder.tvC7, holder.ivMarker7)
                    holder.todayMarker7.visibility = View.VISIBLE
                }
                2 -> {
                    changeSelectedItem(holder.tvC1, holder.ivMarker1)
                    holder.todayMarker1.visibility = View.VISIBLE
                }

                3 -> {
                    changeSelectedItem(holder.tvC2, holder.ivMarker2)
                    holder.todayMarker2.visibility = View.VISIBLE
                }

                4 -> {
                    changeSelectedItem(holder.tvC3, holder.ivMarker3)
                    holder.todayMarker3.visibility = View.VISIBLE
                }

                5 -> {
                    changeSelectedItem(holder.tvC4, holder.ivMarker4)
                    holder.todayMarker4.visibility = View.VISIBLE
                }

                6 -> {
                    changeSelectedItem(holder.tvC5, holder.ivMarker5)
                    holder.todayMarker5.visibility = View.VISIBLE
                }

                7 -> {
                    changeSelectedItem(holder.tvC6, holder.ivMarker6)
                    holder.todayMarker6.visibility = View.VISIBLE
                }

            }
        }


    }


    private fun changeSelectedItem(tv: TextView, marker: ImageView) {
        unselectSelectedItem()
        CustomDatePickerUtils.selectedDateTextView = tv
        CustomDatePickerUtils.selectedDateMarker = marker
        selectSelectedItem()
    }

    private fun selectSelectedItem() {
        if (CustomDatePickerUtils.selectedDateTextView != null && CustomDatePickerUtils.selectedDateMarker != null) {
            CustomDatePickerUtils.selectedDateTextView!!.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
            CustomDatePickerUtils.selectedDateMarker!!.visibility = View.VISIBLE
        }
    }

    private fun unselectSelectedItem() {
        if (CustomDatePickerUtils.selectedDateTextView != null && CustomDatePickerUtils.selectedDateMarker != null) {
            CustomDatePickerUtils.selectedDateTextView!!.setTextColor(context.resources.getColor(R.color.datePickerNumber))
            CustomDatePickerUtils.selectedDateMarker!!.visibility = View.INVISIBLE
        }
    }


    private fun onItemClickListener(position: Int, days: Int, tv: TextView, marker: ImageView) {
        changeSelectedItem(tv, marker)

        val strDateBuilder = StringBuilder()
        strDateBuilder.append(listDatePicker[position][days].year.toString() + "-")
        strDateBuilder.append(listDatePicker[position][days].month.toString() + "-")
        strDateBuilder.append(listDatePicker[position][days].date)
        val date = strDateBuilder.toString()
        callback.OnClickItemCustomDatePicker(date)

    }

    class DatePickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var llD1: ConstraintLayout = itemView.findViewById(R.id.ll_d1)
        var llD2: ConstraintLayout = itemView.findViewById(R.id.ll_d2)
        var llD3: ConstraintLayout = itemView.findViewById(R.id.ll_d3)
        var llD4: ConstraintLayout = itemView.findViewById(R.id.ll_d4)
        var llD5: ConstraintLayout = itemView.findViewById(R.id.ll_d5)
        var llD6: ConstraintLayout = itemView.findViewById(R.id.ll_d6)
        var llD7: ConstraintLayout = itemView.findViewById(R.id.ll_d7)

        var tvC1: TextView = itemView.findViewById(R.id.tv_c1)
        var tvC2: TextView = itemView.findViewById(R.id.tv_c2)
        var tvC3: TextView = itemView.findViewById(R.id.tv_c3)
        var tvC4: TextView = itemView.findViewById(R.id.tv_c4)
        var tvC5: TextView = itemView.findViewById(R.id.tv_c5)
        var tvC6: TextView = itemView.findViewById(R.id.tv_c6)
        var tvC7: TextView = itemView.findViewById(R.id.tv_c7)

        var ivMarker1: ImageView = itemView.findViewById(R.id.iv_marker1)
        var ivMarker2: ImageView = itemView.findViewById(R.id.iv_marker2)
        var ivMarker3: ImageView = itemView.findViewById(R.id.iv_marker3)
        var ivMarker4: ImageView = itemView.findViewById(R.id.iv_marker4)
        var ivMarker5: ImageView = itemView.findViewById(R.id.iv_marker5)
        var ivMarker6: ImageView = itemView.findViewById(R.id.iv_marker6)
        var ivMarker7: ImageView = itemView.findViewById(R.id.iv_marker7)

        var todayMarker1: ImageView = itemView.findViewById((R.id.today_marker1))
        var todayMarker2: ImageView = itemView.findViewById((R.id.today_marker2))
        var todayMarker3: ImageView = itemView.findViewById((R.id.today_marker3))
        var todayMarker4: ImageView = itemView.findViewById((R.id.today_marker4))
        var todayMarker5: ImageView = itemView.findViewById((R.id.today_marker5))
        var todayMarker6: ImageView = itemView.findViewById((R.id.today_marker6))
        var todayMarker7: ImageView = itemView.findViewById((R.id.today_marker7))
    }

}


//    private fun unselectAll(holder:DatePickerViewHolder){
//        holder.tvC1.setTextColor(context.resources.getColor(R.color.black))
//        holder.tvC2.setTextColor(context.resources.getColor(R.color.black))
//        holder.tvC3.setTextColor(context.resources.getColor(R.color.black))
//        holder.tvC4.setTextColor(context.resources.getColor(R.color.black))
//        holder.tvC5.setTextColor(context.resources.getColor(R.color.black))
//        holder.tvC6.setTextColor(context.resources.getColor(R.color.black))
//        holder.tvC7.setTextColor(context.resources.getColor(R.color.black))
//
//        holder.ivMarker1.visibility = View.INVISIBLE
//        holder.ivMarker2.visibility = View.INVISIBLE
//        holder.ivMarker3.visibility = View.INVISIBLE
//        holder.ivMarker4.visibility = View.INVISIBLE
//        holder.ivMarker5.visibility = View.INVISIBLE
//        holder.ivMarker6.visibility = View.INVISIBLE
//        holder.ivMarker7.visibility = View.INVISIBLE
//    }
//
//    private fun unselectLiteralyAll() {
//        for (i in 0 until itemCount) {
//            var viewGroup = recyclerView.layoutManager!!.findViewByPosition(i)!!
//
//            viewGroup.findViewById<TextView>(R.id.tv_c1).setTextColor(context.resources.getColor(R.color.black))
//            viewGroup.findViewById<TextView>(R.id.tv_c2).setTextColor(context.resources.getColor(R.color.black))
//            viewGroup.findViewById<TextView>(R.id.tv_c3).setTextColor(context.resources.getColor(R.color.black))
//            viewGroup.findViewById<TextView>(R.id.tv_c4).setTextColor(context.resources.getColor(R.color.black))
//            viewGroup.findViewById<TextView>(R.id.tv_c5).setTextColor(context.resources.getColor(R.color.black))
//            viewGroup.findViewById<TextView>(R.id.tv_c6).setTextColor(context.resources.getColor(R.color.black))
//            viewGroup.findViewById<TextView>(R.id.tv_c7).setTextColor(context.resources.getColor(R.color.black))
//
//            viewGroup.findViewById<ImageView>(R.id.iv_marker1).visibility = View.INVISIBLE
//            viewGroup.findViewById<ImageView>(R.id.iv_marker2).visibility = View.INVISIBLE
//            viewGroup.findViewById<ImageView>(R.id.iv_marker3).visibility = View.INVISIBLE
//            viewGroup.findViewById<ImageView>(R.id.iv_marker4).visibility = View.INVISIBLE
//            viewGroup.findViewById<ImageView>(R.id.iv_marker5).visibility = View.INVISIBLE
//            viewGroup.findViewById<ImageView>(R.id.iv_marker6).visibility = View.INVISIBLE
//            viewGroup.findViewById<ImageView>(R.id.iv_marker7).visibility = View.INVISIBLE
//        }
//    }