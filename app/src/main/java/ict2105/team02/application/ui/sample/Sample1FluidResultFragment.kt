package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ict2105.team02.application.databinding.FragmentSample1FluidResultBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample1FluidResultFragment : Fragment() {
    private lateinit var binding: FragmentSample1FluidResultBinding
    private val sampleViewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        binding = FragmentSample1FluidResultBinding.inflate(inflater)

        binding.fluidResultInputSpinner.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf("False", "True"))
            setSelection(0)

            onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val fluidResult = parent!!.getItemAtPosition(position).toString().lowercase()
                    sampleViewModel.setSample1Result(fluidResult.toBooleanStrictOrNull())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            sampleViewModel.setSample1Fluid(
                binding.actionInput.text.toString(),
                binding.cultureCommentInput.text.toString()
            )
        }

        sampleViewModel.sampleData.observe(viewLifecycleOwner) {
            if (it.fluidResult != null){
                if(it.fluidResult){
                    binding.fluidResultInputSpinner.setSelection(1)
                }
                else {
                    binding.fluidResultInputSpinner.setSelection(0)
                }
            }
            if (it.fluidAction != null) binding.actionInput.setText(it.fluidAction)
            if (it.fluidComment != null) binding.cultureCommentInput.setText(it.fluidComment)
        }

        binding.actionInput.addTextChangedListener(textChangeListener)
        binding.cultureCommentInput.addTextChangedListener(textChangeListener)

        return binding.root
    }

    private fun validate(): Boolean {
        var valid = true

        if(TextUtils.isEmpty(binding.actionInput.text)){
            binding.errorMsgFluid.text = "Please fill in all the fields"
            valid = false
        }

        if (valid) {
            binding.errorMsgFluid.visibility = View.GONE
        } else {
            binding.errorMsgFluid.visibility = View.VISIBLE
        }

        return valid
    }
}