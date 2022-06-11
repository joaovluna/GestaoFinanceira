package mobile.unifor.cct.savemoney.adapter

import android.view.View

interface FinanceItemListener {

    fun onFinanceItemClick(view: View, position: Int)

    fun onFinanceItemLongClick(view: View, position: Int)
}