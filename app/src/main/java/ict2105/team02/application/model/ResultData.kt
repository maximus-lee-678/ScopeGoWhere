package ict2105.team02.application.model

import java.util.*

data class ResultData(
    val fluidResult: Boolean? = null,
    val fluidAction: String? = null,
    val fluidComment: String? = null,

    val swabDate: Date? = null,
    val swabResult: Boolean? = null,
    val swabAction: String? = null,
    val swabCultureComment: String? = null,

    val quarantineRequired: Boolean? = null,
    val repeatDateMS: Date? = null,
    val borescope: Boolean? = null,

    val waterATPRLU: Int? = null,
    val swabATPRLU: Int? = null,

    val resultDate: Date? = null,
    val doneBy: String? = null,
)