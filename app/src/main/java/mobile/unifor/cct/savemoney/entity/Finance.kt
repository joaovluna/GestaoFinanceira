package mobile.unifor.cct.savemoney.entity

import java.text.DecimalFormat

data class Finance(
    val id: String?,
    val name: String,
    val value: String,
    val description: String,
    val isMovement: Boolean // TODO: true para Receita, false para Despesa
)