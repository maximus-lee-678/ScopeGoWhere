package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample5ReviewBinding
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample5ReviewFragment : Fragment() {
    private lateinit var binding: FragmentSample5ReviewBinding
    private val sampleViewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSample5ReviewBinding.inflate(inflater)

        binding.buttonSendSample.setOnClickListener {
            val confirmationDialog = ConfirmationDialogFragment("Confirm send for wash?") {
                // User clicked confirm
                sampleViewModel.insertSampleData()
                Toast.makeText(
                    requireContext(),
                    "Scope wash recorded successfully!",
                    Toast.LENGTH_LONG
                ).show()
                requireActivity().finish()
            }
            confirmationDialog.show(parentFragmentManager, "ConfirmationDialog")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sampleViewModel.sampleData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it.fluidResult != null) fluidResult.setText(it.fluidResult.toString())
                if (it.fluidAction != null) actionFluid.setText(it.fluidAction)
                if (it.fluidComment != null) cultureCommentFluid.setText(it?.fluidComment)
                if (it.swabDate != null) dateOfSwabResult.setText(it.swabDate.toDateString())
                if (it.swabResult != null) swabResult.setText(it.swabResult.toString())
                if (it.swabAction != null) actionSwab.setText(it.swabAction)
                if (it.swabCultureComment != null) cultureCommentSwab.setText(it.swabCultureComment)
                if (it.quarantineRequired != null) quarantinePeriodInput.setText(it.quarantineRequired.toString())
                if (it.repeatDateMS != null) repeatDateMsInput.setText(it.repeatDateMS.toDateString())
                if (it.borescope != null) borescopeDropdown.setText(it?.borescope.toString())
                if (it.waterATPRLU != null) atpWaterRluInput.setText(it?.waterATPRLU.toString())
                if (it.swabATPRLU != null) atpSwabRluInput.setText(it?.swabATPRLU.toString())
            }
        }
    }
}