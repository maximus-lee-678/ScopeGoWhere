package ict2105.team02.application.model

import java.util.Date

data class EndoscopeTransaction (
    val date: Date,
    val doneBy: String,
    val transaction: String,
)