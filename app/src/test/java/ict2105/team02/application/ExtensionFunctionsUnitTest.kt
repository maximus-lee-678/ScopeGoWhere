package ict2105.team02.application

import ict2105.team02.application.model.ResultData
import ict2105.team02.application.model.WashData
import ict2105.team02.application.utils.*
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class ExtensionFunctionsUnitTest {
    @Test
    fun testTAG() {
        val testObject = Any()
        assertEquals("Object", testObject.TAG)
    }

    @Test
    fun testToDateString() {
        val calendar = Calendar.getInstance()
        calendar.set(2023, 2, 26)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val date = calendar.time
        assertEquals("26/03/2023", date.toDateString())
    }

    @Test
    fun testParseDateString() {
        val dateString = "26/03/2023"
        val calendar = Calendar.getInstance()
        calendar.set(2023, 2, 26)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val expectedDate = calendar.time
        assertEquals(expectedDate, dateString.parseDateString())
    }

    @Test
    fun testAsHashMap_WashData() {
        val washData = WashData(
            AERModel = "ModelA",
            AERSerial = 123,
            DetergentUsed = "DetergentA",
            DetergentLotNo = 456,
            FilterChangeDate = Date(),
            DisinfectantUsed = "DisinfectantA",
            DisinfectantLotNo = 789,
            DisinfectantChangedDate = Date(),
            ScopeDryer = 101112,
            DryerLevel = 131415,
            Remarks = "Test remarks",
            WashDate = Date(),
            DoneBy = "John Doe"
        )
        val hashMap = washData.asHashMap()

        // Perform assertions for each field
        assertEquals(washData.AERModel, hashMap["AERModel"])
        assertEquals(washData.AERSerial, hashMap["AERSerial"])
        assertEquals(washData.DetergentUsed, hashMap["DetergentUsed"])
        assertEquals(washData.DetergentLotNo, hashMap["DetergentLotNo"])
        assertEquals(washData.FilterChangeDate, hashMap["FilterChangeDate"])
        assertEquals(washData.DisinfectantUsed, hashMap["DisinfectantUsed"])
        assertEquals(washData.DisinfectantLotNo, hashMap["DisinfectantLotNo"])
        assertEquals(washData.DisinfectantChangedDate, hashMap["DisinfectantChangedDate"])
        assertEquals(washData.ScopeDryer, hashMap["ScopeDryer"])
        assertEquals(washData.DryerLevel, hashMap["DryerLevel"])
        assertEquals(washData.Remarks, hashMap["Remarks"])
        assertEquals(washData.WashDate, hashMap["WashDate"])
        assertEquals(washData.DoneBy, hashMap["DoneBy"])
    }

    @Test
    fun testAsHashMap_ResultData() {
        val resultData = ResultData(
            fluidResult = true,
            fluidAction = "ActionA",
            fluidComment = "CommentA",
            swabDate = Date(),
            swabResult = false,
            swabAction = "ActionB",
            swabCultureComment = "CommentB",
            quarantineRequired = true,
            repeatDateMS = Date(),
            borescope = false,
            waterATPRLU = 12345,
            swabATPRLU = 67890,
            resultDate = Date(),
            doneBy = "Jane Doe"
        )
        val hashMap = resultData.asHashMap()

        // Perform assertions for each field
        assertEquals(resultData.fluidResult, hashMap["fluidResult"])
        assertEquals(resultData.fluidAction, hashMap["fluidAction"])
        assertEquals(resultData.fluidComment, hashMap["fluidComment"])
        assertEquals(resultData.swabDate, hashMap["swabDate"])
        assertEquals(resultData.swabResult, hashMap["swabResult"])
        assertEquals(resultData.swabAction, hashMap["swabAction"])
        assertEquals(resultData.swabCultureComment, hashMap["swabCultureComment"])
        assertEquals(resultData.quarantineRequired, hashMap["quarantineRequired"])
        assertEquals(resultData.repeatDateMS, hashMap["repeatDateMS"])
        assertEquals(resultData.borescope, hashMap["borescope"])
        assertEquals(resultData.waterATPRLU, hashMap["waterATPRLU"])
        assertEquals(resultData.swabATPRLU, hashMap["swabATPRLU"])
        assertEquals(resultData.resultDate, hashMap["resultDate"])
        assertEquals(resultData.doneBy, hashMap["doneBy"])
    }

    @Test
    fun testMapYesNoToBoolean() {
        assertEquals(true, "YES".mapYesNoToBoolean())
        assertEquals(true, "Yes".mapYesNoToBoolean())
        assertEquals(true, "yes".mapYesNoToBoolean())
        assertEquals(false, "NO".mapYesNoToBoolean())
        assertEquals(false, "No".mapYesNoToBoolean())
        assertEquals(false, "no".mapYesNoToBoolean())
    }

    @Test
    fun testMapPositiveNegativeToBoolean() {
        assertEquals(true, "POSITIVE".mapPositiveNegativeToBoolean())
        assertEquals(true, "Positive".mapPositiveNegativeToBoolean())
        assertEquals(true, "positive".mapPositiveNegativeToBoolean())
        assertEquals(false, "NEGATIVE".mapPositiveNegativeToBoolean())
        assertEquals(false, "Negative".mapPositiveNegativeToBoolean())
        assertEquals(false, "negative".mapPositiveNegativeToBoolean())
    }
}