package ict2105.team02.application.repo
import androidx.lifecycle.MutableLiveData
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeTransaction
import java.text.SimpleDateFormat
import java.util.*


class EndoscopeProducer {
    val endoscopeTransaction : List<EndoscopeTransaction> = emptyList()

    private val endoscopes = ArrayList<Endoscope>()

    val dateString = "2023-02-17"
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date : Date = format.parse(dateString)
    init {

    }

    fun getendoscopes() : MutableLiveData<List<Endoscope>> {
        pullDB();
        val data: MutableLiveData<List<Endoscope>> = MutableLiveData()
        data.setValue(endoscopes)
        return data;
    }
    fun pullDB(){
        endoscopes.add(
            Endoscope("123", "ABC", "Omplyus", "Washing",
                date , endoscopeTransaction)
        )
        endoscopes.add(
            Endoscope("456", "EFG", "Test", "Sampling",
                date , endoscopeTransaction)
        )
        endoscopes.add(
            Endoscope("789", "ASD", "Test2", "Drying",
                date , endoscopeTransaction)
        )
    }
}