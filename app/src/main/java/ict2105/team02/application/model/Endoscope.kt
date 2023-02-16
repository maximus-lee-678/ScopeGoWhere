package ict2105.team02.application.model

data class Endoscope (
    var serial: String,
    val model: String,
    val type: String,
    val status: String,
    val nextSample: String,
    val history: List<EndoscopeTransaction>,
)