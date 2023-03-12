package ict2105.team02.application.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment(private val message: String, private val onConfirm: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Confirm") { _, _ ->
                onConfirm()
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
