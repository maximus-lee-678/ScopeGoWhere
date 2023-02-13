package ict2105.team02.application.endoscope

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ict2105.team02.application.databinding.FragmentScopeDetailBinding

class ScopeDetailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScopeDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScopeDetailBinding.inflate(inflater)

        binding.scanAgainButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}