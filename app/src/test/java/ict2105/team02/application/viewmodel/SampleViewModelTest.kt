package ict2105.team02.application.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.ResultData
import ict2105.team02.application.repo.DataRepository
import ict2105.team02.application.utils.parseDateString
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class SampleViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var sampleDataObserver: Observer<ResultData>

    @Mock
    private lateinit var scopeDataObserver: Observer<Endoscope>

    private lateinit var viewModel: SampleViewModel

    @Before
    fun setUp() {
        viewModel = SampleViewModel(DataRepository.getInstance())
        viewModel.sampleData.observeForever(sampleDataObserver)
        viewModel.scopeData.observeForever(scopeDataObserver)
    }


    @Test
    fun testMakeSampleData() {
        viewModel.makeSampleData()

        viewModel.setSample1Fluid("action1", "comment1")
        viewModel.setSample1Result(true)
        viewModel.setSample2Swab("1/1/2023".parseDateString(), "action2", "comment2")
        viewModel.setSample2Result(false)
        viewModel.setSample3RepeatOfMS("1/1/2023".parseDateString())
        viewModel.setSample3Data(true, false)
        viewModel.setSample4Atp(55, 55)

        val newBuildResultData = ResultData(
            fluidAction = "action1",
            fluidComment = "comment1",
            fluidResult = true,
            swabDate = "1/1/2023".parseDateString(),
            swabAction = "action2",
            swabCultureComment = "comment2",
            swabResult = false,
            repeatDateMS = "1/1/2023".parseDateString(),
            quarantineRequired = true,
            borescope = false,
            waterATPRLU = 55,
            swabATPRLU = 55
        )
        verify(sampleDataObserver).onChanged(newBuildResultData)
    }

    @Test
    fun testSetAllSample() {
        val sampleDataMap = hashMapOf(
            "fluidResult" to "true",
            "fluidAction" to "action1",
            "fluidComment" to "comment1",
            "swabDate" to "1/1/2023",
            "swabResult" to "false",
            "swabAction" to "action2",
            "swabCultureComment" to "comment2",
            "quarantineRequired" to "false",
            "repeatDateMS" to "",
            "waterATPRLU" to "55",
            "swabATPRLU" to "55"
        )

        viewModel.setAllSample(sampleDataMap)

        val newHashResultData = ResultData(
            fluidResult = true,
            fluidAction = "action1",
            fluidComment = "comment1",
            swabDate = "1/1/2023".parseDateString(),
            swabResult = false,
            swabAction = "action2",
            swabCultureComment = "comment2",
            quarantineRequired = false,
            repeatDateMS = null,
            waterATPRLU = 55,
            swabATPRLU = 55
        )

        verify(sampleDataObserver).onChanged(newHashResultData)
    }
}