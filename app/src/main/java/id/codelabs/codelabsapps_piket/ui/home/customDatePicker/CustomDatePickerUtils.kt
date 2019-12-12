package id.codelabs.codelabsapps_piket.ui.home.customDatePicker

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class CustomDatePickerUtils {
    companion object {


        @SuppressLint("StaticFieldLeak")
        var selectedDateTextView : TextView? = null
        @SuppressLint("StaticFieldLeak")
        var selectedDateMarker : ImageView? = null

        private var maxDayMonth = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        private var thisYear: Int? = null
        private var listDatePicker: Array<Array<ModelDate>>? = null
        private lateinit var cal: Calendar
        @SuppressLint("SimpleDateFormat")
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        private lateinit var currentDate: String
        private lateinit var splitCurrentDate: List<String>
        private var mNumberOfWeeks = 0
        private var midValNumberOfWeeksIndex: Int = 0

        fun generateDate(calendarInstance: Calendar, numberOfWeeks: Int): Array<Array<ModelDate>> {

            if (listDatePicker == null) {

                listDatePicker = Array(numberOfWeeks) { Array(7) { ModelDate(0, 0, 0) } }

                mNumberOfWeeks = numberOfWeeks
                midValNumberOfWeeksIndex = (mNumberOfWeeks / 2)
                cal = calendarInstance
                currentDate = simpleDateFormat.format(cal.time)
                splitCurrentDate = currentDate.split("-")

                thisYear = cal.get(Calendar.YEAR)
                if (thisYear!! % 4 == 0) maxDayMonth[1] = 29

                var tempThisYear = splitCurrentDate[0].toInt()
                var tempThisMonth = splitCurrentDate[1].toInt()
                val tempThisDate = splitCurrentDate[2].toInt()
                var dayOfWeekToday =
                    cal.get(Calendar.DAY_OF_WEEK) - 2 //cal.get(Calendar.DAY_OF_WEEK) -> minggu = 1
                if (dayOfWeekToday == -1) {
                    dayOfWeekToday = 6
                }

                var dateOfMondayThisWeek = tempThisDate - dayOfWeekToday
                if (dateOfMondayThisWeek < 1) {
                    tempThisMonth -= 1
                    if (tempThisMonth < 1) {
                        tempThisMonth = 12
                        tempThisYear -= 1
                    }
                    val maxDayThisMonth = maxDayMonth[tempThisMonth - 1]
                    dateOfMondayThisWeek += maxDayThisMonth
                }

                val fullDateOfMondayThisWeek =
                    ModelDate(
                        dateOfMondayThisWeek,
                        tempThisMonth,
                        tempThisYear
                    )
                listDatePicker!![midValNumberOfWeeksIndex][0] = fullDateOfMondayThisWeek

                generateBeforeDate()
                generateThisWeek()
                generateAfterDate()
            }
            return listDatePicker!!
        }

        private fun generateBeforeDate() {
            for (i in (midValNumberOfWeeksIndex - 1) downTo 0) {
                val tempArray = Array(7) {
                    ModelDate(
                        0,
                        0,
                        0
                    )
                }
                for (j in 6 downTo 0) {
                    var tempDate: Int
                    var tempMonth: Int
                    var tempYear: Int
                    if (j == 6) {
                        tempDate = listDatePicker!![i + 1][0].date - 1
                        tempMonth = listDatePicker!![i + 1][0].month
                        tempYear = listDatePicker!![i + 1][0].year
                    } else {
                        tempDate = tempArray[j + 1].date - 1
                        tempMonth = tempArray[j + 1].month
                        tempYear = tempArray[j + 1].year
                    }
                    if (tempDate == 0) {
                        tempMonth -= 1
                        if (tempMonth < 1) {
                            tempMonth = 12
                            tempYear -= 1
                        }
                        val maxDayThisMonth = maxDayMonth[tempMonth - 1]
                        tempDate = maxDayThisMonth
                    }
                    val tempModelDate =
                        ModelDate(
                            tempDate,
                            tempMonth,
                            tempYear
                        )
                    tempArray[j] = tempModelDate
                }
                listDatePicker!![i] = tempArray
            }
        }

        private fun generateThisWeek() {
            for (j in 1..6) {
                var tempDate = listDatePicker!![midValNumberOfWeeksIndex][j - 1].date + 1
                var tempMonth = listDatePicker!![midValNumberOfWeeksIndex][j - 1].month
                var tempYear = listDatePicker!![midValNumberOfWeeksIndex][j - 1].year
                val maxDayMonth = maxDayMonth[tempMonth - 1]
                if (tempDate > maxDayMonth) {
                    tempMonth += 1
                    if (tempMonth > 12) {
                        tempMonth = 1
                        tempYear += 1
                    }
                    tempDate = 1
                }
                val tempModelDate =
                    ModelDate(
                        tempDate,
                        tempMonth,
                        tempYear
                    )
                listDatePicker!![midValNumberOfWeeksIndex][j] = tempModelDate
            }
        }


        private fun generateAfterDate() {
            for (i in (midValNumberOfWeeksIndex + 1) until mNumberOfWeeks) {
                val tempArray = Array(7) {
                    ModelDate(
                        0,
                        0,
                        0
                    )
                }
                for (j in 0..6) {
                    var tempDate: Int
                    var tempMonth: Int
                    var tempYear: Int
                    if (j == 0) {
                        tempDate = listDatePicker!![i - 1][6].date + 1
                        tempMonth = listDatePicker!![i - 1][6].month
                        tempYear = listDatePicker!![i - 1][6].year
                    } else {
                        tempDate = tempArray[j - 1].date + 1
                        tempMonth = tempArray[j - 1].month
                        tempYear = tempArray[j - 1].year
                    }
                    val maxDayThisMonth = maxDayMonth[tempMonth - 1]
                    if (tempDate > maxDayThisMonth) {
                        tempMonth += 1
                        if (tempMonth > 12) {
                            tempMonth = 1
                            tempYear += 1
                        }
                        tempDate = 1
                    }
                    val tempModelDate =
                        ModelDate(
                            tempDate,
                            tempMonth,
                            tempYear
                        )
                    tempArray[j] = tempModelDate
                }
                listDatePicker!![i] = tempArray
            }
        }
    }
}