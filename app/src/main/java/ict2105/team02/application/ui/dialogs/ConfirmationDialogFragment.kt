package ict2105.team02.application.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ict2105.team02.application.R

class ConfirmationDialogFragment(private val message: String, private val onConfirm: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.dialog_confirm)) { _, _ ->
                onConfirm()
            }
            .setNegativeButton(resources.getString(R.string.dialog_cancel), null)
            .create()
    }
}
