package ict2105.team02.application.ui.sample

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ict2105.team02.application.R
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.utils.NFCStatus
import ict2105.team02.application.viewmodel.NFCViewModel

class ScanDialogFragment : DialogFragment(), NfcAdapter.ReaderCallback {
    private val nfcViewModel: NFCViewModel by viewModels {
        ViewModelFactory(
            "NFCViewModel",
            activity?.application as MainApplication
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (nfcViewModel.checkEnabled(requireContext())) {
            nfcViewModel.enableReaderMode(
                requireContext(),
                requireActivity(),
                this@ScanDialogFragment
            )
            nfcViewModel.observeTag().observe(this) {
                if (it == NFCStatus.Auth) {
                    nfcViewModel.disableReaderMode(requireContext(), requireActivity())
                    val intent = Intent(activity, SampleActivity::class.java)
                    activity?.startActivity(intent)
                } else {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.login_nfc_invalid),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.login_nfc_disabled),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(resources.getString(R.string.login_nfc_prompt))
            .setNeutralButton(resources.getString(R.string.login_manual)) { _, _ -> }
//            .setPositiveButton(resources.getString(R.string.login_pass_scanned)) { _,_ ->
//                val intent = Intent(getActivity(), SampleActivity::class.java)
//                getActivity()?.startActivity(intent)
//            }
            .create()

    override fun onTagDiscovered(tag: Tag) {
        nfcViewModel.readTag(tag)
    }
}