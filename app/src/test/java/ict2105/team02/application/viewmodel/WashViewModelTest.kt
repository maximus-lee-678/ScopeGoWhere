package ict2105.team02.application.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ict2105.team02.application.model.WashData
import ict2105.team02.application.repo.DataRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class WashViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var washViewModel: WashViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        washViewModel = WashViewModel(DataRepository.getInstance())
        washViewModel.makeWashData()
    }

    @Test
    fun makeScope() {
        washViewModel.makeScope("brandA", "modelA", 1)
        assertTrue(washViewModel.scopeData.value!!.scopeModel == "modelA")
    }

    @Test
    fun sampleBuilder() {
        val modelAER = "MODEL1"
        val serialAER = 1
        val detergent = "detA"
        val detergentLot = 1
        val filterDate = Date()
        val disinfectant = "disA"
        val disinfectantLot = 1
        val changedDate = Date()
        val scopeDryer = 1
        val dryerLvl = 1
        val remarks = "remarks"

        val washDataObserver = Observer<WashData> {
        }
        washViewModel.washData.observeForever(washDataObserver)

        washViewModel.setWash1AER(modelAER,serialAER)
        washViewModel.setWash2Detergent(detergent,detergentLot, filterDate)
        washViewModel.setWash3Disinfectant(disinfectant,disinfectantLot,changedDate)
        washViewModel.setWash4Drying(scopeDryer,dryerLvl,remarks)

        val washData = washViewModel.washData.value
        if(washData!=null) {
            assertEquals(serialAER, washData.AERSerial)
            assertEquals(detergent, washData.DetergentUsed)
            assertEquals(detergentLot, washData.DetergentLotNo)
            assertEquals(filterDate, washData.FilterChangeDate)
            assertEquals(disinfectant, washData.DisinfectantUsed)
            assertEquals(disinfectantLot, washData.DetergentLotNo)
            assertEquals(changedDate, washData.DisinfectantChangedDate)
            assertEquals(scopeDryer, washData.ScopeDryer)
            assertEquals(dryerLvl, washData.DryerLevel)
            assertEquals(remarks, washData.Remarks)
        }
        washViewModel.washData.removeObserver(washDataObserver)
    }
}