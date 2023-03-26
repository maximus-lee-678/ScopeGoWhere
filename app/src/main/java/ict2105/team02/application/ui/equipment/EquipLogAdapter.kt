package ict2105.team02.application.ui.equipment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.EndoscopeTransaction
import ict2105.team02.application.model.ResultData
import ict2105.team02.application.model.WashData
import ict2105.team02.application.utils.toDateString

class EquipLogAdapter:
    ListAdapter<EndoscopeTransaction, EquipLogAdapter.EndoscopeTransactionViewHolder>(
        EndoscopeTransactionComparator()
    )
{

    class EndoscopeTransactionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val aerModelLL: LinearLayout = itemView.findViewById(R.id.aerModel)
        private val aerModelData: TextView = itemView.findViewById(R.id.aerModelData)

        private val aerSerialLL: LinearLayout = itemView.findViewById(R.id.aerSerial)
        private val aerSerialData: TextView = itemView.findViewById(R.id.aerSerialData)

        private val detergentUsedLL: LinearLayout = itemView.findViewById(R.id.detergentUsed)
        private val detergentUsedData: TextView = itemView.findViewById(R.id.detergentUsedData)

        private val detergentLotNoLL: LinearLayout = itemView.findViewById(R.id.detergentLotNo)
        private val detergentLotNoData: TextView = itemView.findViewById(R.id.detergentLotNoData)

        private val filterChangeDateLL: LinearLayout = itemView.findViewById(R.id.filterChangeDate)
        private val filterChangeDateData: TextView = itemView.findViewById(R.id.filterChangeDateData)

        private val disinfectantUsedLL: LinearLayout = itemView.findViewById(R.id.disinfectantUsed)
        private val disinfectantUsedData: TextView = itemView.findViewById(R.id.disinfectantUsedData)

        private val disinfectantLotNoLL: LinearLayout = itemView.findViewById(R.id.disinfectantLotNo)
        private val disinfectantLotNoData: TextView = itemView.findViewById(R.id.disinfectantLotNoData)

        private val disinfectantChangedDateLL: LinearLayout = itemView.findViewById(R.id.disinfectantChangedDate)
        private val disinfectantChangedDateData: TextView = itemView.findViewById(R.id.disinfectantChangedDateData)

        private val scopeDryerLL: LinearLayout = itemView.findViewById(R.id.scopeDryer)
        private val scopeDryerData: TextView = itemView.findViewById(R.id.scopeDryerData)

        private val doneByLL: LinearLayout = itemView.findViewById(R.id.doneBy)
        private val doneByData: TextView = itemView.findViewById(R.id.doneByData)

        private val remarksLL: LinearLayout = itemView.findViewById(R.id.remarks)
        private val remarksData: TextView = itemView.findViewById(R.id.remarksData)

        private val dryerLevelLL: LinearLayout = itemView.findViewById(R.id.dryerLevel)
        private val dryerLevelData: TextView = itemView.findViewById(R.id.dryerLevelData)

        private val washDateLL: LinearLayout = itemView.findViewById(R.id.washDate)
        private val washDateData: TextView = itemView.findViewById(R.id.washDateData)

        private val fluidResultLL: LinearLayout = itemView.findViewById(R.id.fluidResult)
        private val fluidResultData: TextView = itemView.findViewById(R.id.fluidResultData)

        private val fluidActionLL: LinearLayout = itemView.findViewById(R.id.fluidAction)
        private val fluidActionData: TextView = itemView.findViewById(R.id.fluidActionData)

        private val fluidCommentLL: LinearLayout = itemView.findViewById(R.id.fluidComment)
        private val fluidCommentData: TextView = itemView.findViewById(R.id.fluidCommentData)

        private val quarantineRequiredLL: LinearLayout = itemView.findViewById(R.id.quarantineRequired)
        private val quarantineRequiredData: TextView = itemView.findViewById(R.id.quarantineRequiredData)

        private val recordedByLL: LinearLayout = itemView.findViewById(R.id.recordedBy)
        private val recordedByData: TextView = itemView.findViewById(R.id.recordedByData)

        private val swabResultLL: LinearLayout = itemView.findViewById(R.id.swabResult)
        private val swabResultData: TextView = itemView.findViewById(R.id.swabResultData)

        private val swabActionLL: LinearLayout = itemView.findViewById(R.id.swabAction)
        private val swabActionData: TextView = itemView.findViewById(R.id.swabActionData)

        private val swabCultureCommentLL: LinearLayout = itemView.findViewById(R.id.swabCultureComment)
        private val swabCultureCommentData: TextView = itemView.findViewById(R.id.swabCultureCommentData)

        private val repeatDateMSLL: LinearLayout = itemView.findViewById(R.id.repeatDateMS)
        private val repeatDateMSData: TextView = itemView.findViewById(R.id.repeatDateMSData)

        private val borescopeLL: LinearLayout = itemView.findViewById(R.id.borescope)
        private val borescopeData: TextView = itemView.findViewById(R.id.borescopeData)

        private val waterATPRLULL: LinearLayout = itemView.findViewById(R.id.waterATPRLU)
        private val waterATPRLUData: TextView = itemView.findViewById(R.id.waterATPRLUData)

        private val swabATPRLULL: LinearLayout = itemView.findViewById(R.id.swabATPRLU)
        private val swabATPRLUData: TextView = itemView.findViewById(R.id.swabATPRLUData)

        private val resultDateLL: LinearLayout = itemView.findViewById(R.id.resultDate)
        private val resultDateData: TextView = itemView.findViewById(R.id.resultDateData)

        private val swabDateLL: LinearLayout = itemView.findViewById(R.id.swabDate)
        private val swabDateData: TextView = itemView.findViewById(R.id.swabDateData)

        private val washDataLL: LinearLayout = itemView.findViewById(R.id.washDataLL)
        private val resultDataLL: LinearLayout = itemView.findViewById(R.id.resultDataLL)

        fun bindWashData(washData: WashData?){
            if(washData == null){
                washDataLL.visibility = View.GONE
            }else {
                if (washData.AERModel?.isEmpty() == false) {
                    aerModelData.text = washData.AERModel
                } else {
                    aerModelLL.visibility = View.GONE
                }

                if (washData.AERSerial != null) {
                    aerSerialData.text = washData.AERSerial.toString()
                } else {
                    aerSerialLL.visibility = View.GONE
                }

                if (washData.WashDate != null) {
                    washDateData.text = washData.WashDate.toDateString()
                } else {
                    washDateLL.visibility = View.GONE
                }

                if (washData.DetergentLotNo != null) {
                    detergentLotNoData.text = washData.DetergentLotNo.toString()
                } else {
                    detergentLotNoLL.visibility = View.GONE
                }

                if (washData.DetergentUsed?.isEmpty() == false) {
                    detergentUsedData.text = washData.DetergentUsed
                } else {
                    detergentUsedLL.visibility = View.GONE
                }

                if (washData.DisinfectantChangedDate != null) {
                    disinfectantChangedDateData.text = washData.DisinfectantChangedDate.toDateString()
                } else {
                    disinfectantChangedDateLL.visibility = View.GONE
                }

                if (washData.DisinfectantLotNo != null) {
                    disinfectantLotNoData.text = washData.DisinfectantLotNo.toString()
                } else {
                    disinfectantLotNoLL.visibility = View.GONE
                }

                if (washData.DisinfectantUsed?.isEmpty() == false) {
                    disinfectantUsedData.text = washData.DisinfectantUsed
                } else {
                    disinfectantUsedLL.visibility = View.GONE
                }

                if (washData.FilterChangeDate != null) {
                    filterChangeDateData.text = washData.FilterChangeDate.toDateString()
                } else {
                    filterChangeDateLL.visibility = View.GONE
                }

                if (washData.ScopeDryer != null) {
                    scopeDryerData.text = washData.ScopeDryer.toString()
                } else {
                    scopeDryerLL.visibility = View.GONE
                }

                if (washData.DoneBy?.isEmpty() == false) {
                    doneByData.text = washData.DoneBy
                } else {
                    doneByLL.visibility = View.GONE
                }

                if (washData.Remarks?.isEmpty() == false) {
                    remarksData.text = washData.Remarks
                } else {
                    remarksLL.visibility = View.GONE
                }

                if (washData.DryerLevel != null) {
                    dryerLevelData.text = washData.DryerLevel.toString()
                } else {
                    dryerLevelLL.visibility = View.GONE
                }

                if (washData.WashDate != null) {
                    washDateData.text = washData.WashDate.toDateString()
                } else {
                    washDateLL.visibility = View.GONE
                }
            }
        }

        fun bindResultData(resultData: ResultData?){
            Log.d("RV Adapter", resultData.toString())
            if(resultData == null){
                resultDataLL.visibility = View.GONE
            }else {
                if (resultData.fluidResult != null) {
                    fluidResultData.text = resultData.fluidResult.toString()
                } else {
                    fluidResultLL.visibility = View.GONE
                }

                if (resultData.fluidAction != null) {
                    fluidActionData.text = resultData.fluidAction.toString()
                } else {
                    fluidActionLL.visibility = View.GONE
                }

                if (resultData.fluidComment?.isEmpty() == false) {
                    fluidCommentData.text = resultData.fluidComment
                } else {
                    fluidCommentLL.visibility = View.GONE
                }

                if (resultData.quarantineRequired != null) {
                    quarantineRequiredData.text = resultData.quarantineRequired.toString()
                } else {
                    quarantineRequiredLL.visibility = View.GONE
                }

                if (resultData.doneBy?.isEmpty() == false) {
                    recordedByData.text = resultData.doneBy
                } else {
                    recordedByLL.visibility = View.GONE
                }

                if (resultData.swabResult != null) {
                    swabResultData.text = resultData.swabResult.toString()
                } else {
                    swabResultLL.visibility = View.GONE
                }

                if (resultData.swabAction?.isEmpty() == false) {
                    swabActionData.text = resultData.swabAction
                } else {
                    swabActionLL.visibility = View.GONE
                }

                if (resultData.swabCultureComment?.isEmpty() == false) {
                    swabCultureCommentData.text = resultData.swabCultureComment
                } else {
                    swabCultureCommentLL.visibility = View.GONE
                }

                if (resultData.repeatDateMS != null) {
                    repeatDateMSData.text = resultData.repeatDateMS.toDateString()
                } else {
                    repeatDateMSLL.visibility = View.GONE
                }

                if (resultData.borescope != null) {
                    borescopeData.text = resultData.borescope.toString()
                } else {
                    borescopeLL.visibility = View.GONE
                }

                if (resultData.waterATPRLU != null) {
                    waterATPRLUData.text = resultData.waterATPRLU.toString()
                } else {
                    waterATPRLULL.visibility = View.GONE
                }

                if (resultData.swabATPRLU != null) {
                    swabATPRLUData.text = resultData.swabATPRLU.toString()
                } else {
                    swabATPRLULL.visibility = View.GONE
                }

                if (resultData.resultDate != null) {
                    resultDateData.text = resultData.resultDate.toDateString()
                } else {
                    resultDateLL.visibility = View.GONE
                }

                if (resultData.swabDate != null) {
                    swabDateData.text = resultData.swabDate.toDateString()
                } else {
                    swabDateLL.visibility = View.GONE
                }
            }
        }

        companion object{
            fun create(parent: ViewGroup): EndoscopeTransactionViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_log_item, parent, false)
                return EndoscopeTransactionViewHolder(view)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EndoscopeTransactionViewHolder {
        return EndoscopeTransactionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EndoscopeTransactionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bindWashData(current.washData)
        holder.bindResultData(current.resultData)
    }

    class EndoscopeTransactionComparator: DiffUtil.ItemCallback<EndoscopeTransaction>() {
        override fun areItemsTheSame(
            oldItem: EndoscopeTransaction,
            newItem: EndoscopeTransaction
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EndoscopeTransaction,
            newItem: EndoscopeTransaction
        ): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }

    }
}
