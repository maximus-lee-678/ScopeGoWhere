package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash5ReviewBinding
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.WashViewModel

class Wash5ReviewFragment : Fragment() {
    private lateinit var binding: FragmentWash5ReviewBinding
    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWash5ReviewBinding.inflate(inflater)

        binding.buttonSendForWash.setOnClickListener{
            val confirmationDialog = ConfirmationDialogFragment("Confirm send for wash?") {
                // User clicked confirm
                viewModel.insertWashData()
                Toast.makeText(requireContext(), "Scope wash recorded successfully!", Toast.LENGTH_LONG).show()
                activity?.finish()
            }
            confirmationDialog.show(parentFragmentManager, "ConfirmationDialog")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.washData.observe(viewLifecycleOwner) {
            binding.apply {
                Log.d(TAG, it.toString())
                if (it.AERModel != null) binding.aerModel.setText(it.AERModel)
                if (it.AERSerial != null) binding.aerSerialNo.setText(it.AERSerial.toString())
                if (it.DetergentUsed != null) binding.detergentUsed.setText(it.DetergentUsed)
                if (it.DetergentLotNo != null) binding.detergentLotNo.setText(it.DetergentLotNo.toString())
                if (it.FilterChangeDate != null) binding.filterChangeDate.setText(it.FilterChangeDate.toDateString())
                if (it.DisinfectantUsed != null) binding.disinfectantUsed.setText(it.DisinfectantUsed)
                if (it.DisinfectantLotNo != null) binding.disinfectantLotNo.setText(it.DisinfectantLotNo.toString())
                if (it.DisinfectantChangedDate != null) binding.disinfectantChanged.setText(it.DisinfectantChangedDate.toDateString())
                if (it.ScopeDryer != null) binding.scopeDryer.setText(it.ScopeDryer.toString())
                if (it.DryerLevel != null) binding.dryerLevel.setText(it.DryerLevel.toString())
                if (it.Remarks != null) binding.remarks.setText(it.Remarks.toString())
            }
        }
        viewModel.scopeData.observe(viewLifecycleOwner) {
            binding.apply {
                brand.setText(it.scopeBrand)
                model.setText(it.scopeModel)
                serialNo.setText(it.scopeSerial.toString())
            }
        }
    }
}