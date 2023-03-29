package ict2105.team02.application.repo

import ict2105.team02.application.R
import ict2105.team02.application.model.HelpData

class HelpRepo {
	private val helpDataList = listOf(
		HelpData("How to use App", "DKvU-5yJo0s", R.array.app_instructions),
		HelpData("Endoscope Cleaning", "SxBjCvnXIeo", R.array.endoscope_cleaning_instructions),
		HelpData("Endoscope Drying", "Sd5xafHAydU", R.array.endoscope_drying_instructions),
		HelpData("Endoscope Sampling", "XyGhlNorlfE", R.array.endoscope_sampling_instructions)
	)
	fun getHelpList() : List<HelpData>{
		return helpDataList
	}
}