package ict2105.team02.application.model

import java.util.Date

data class Endoscope (
    var serial: String,
    val model: String,
    val type: String,
    val status: String,
    val nextSample: Date,
    val history: List<EndoscopeTransaction>?,
)