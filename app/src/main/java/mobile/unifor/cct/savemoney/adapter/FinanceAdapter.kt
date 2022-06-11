package mobile.unifor.cct.savemoney.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mobile.unifor.cct.savemoney.R
import mobile.unifor.cct.savemoney.entity.Finance

class FinanceAdapter(var finances: List<Finance>): RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>(){

    private var listener: FinanceItemListener? = null

    class FinanceViewHolder(view: View, listener: FinanceItemListener?): RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.movement_textview_nome)
        val indicator: View = view.findViewById(R.id.movement_item_indicator)
        val value: TextView = view.findViewById(R.id.movement_textview_value)

        init {
            view.setOnClickListener {
                listener?.onFinanceItemClick(it, adapterPosition)
            }
            view.setOnLongClickListener {
                listener?.onFinanceItemLongClick(it, adapterPosition)
                true
            }
        }

    }

    fun setOnFinanceItemListener(listener: FinanceItemListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movement_item,parent,false)

        return FinanceViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {

        // TODO: FAZER A ENTITY FINANCE COM AS INFORMACOES NAME, VALUE & Income -> vai verificar qual RadioButton esta ativo e setar como true ou false.

//        holder.name.text = finances[position].name
//
//        if(finances[position].isIncome) {
//            holder.indicator.setBackgroundColor(Color.GREEN)
//        } else {
//            holder.indicator.setBackgroundColor(Color.RED)
//        }

    }

    override fun getItemCount(): Int { //tamanho de itens que tenho na lista
        return finances.size
    }


}