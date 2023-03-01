package ict2105.team02.application.model

import ict2105.team02.application.repo.DataRepository
import java.util.Date
import java.util.Objects

data class EndoscopeTransaction (
    val date: Date,
    val doneBy: String,
    val transaction: String,
    val washDataList: List<Objects>,
    val sampleDataList: List<Objects>,
)