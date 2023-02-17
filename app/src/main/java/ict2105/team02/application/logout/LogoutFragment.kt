package ict2105.team02.application.logout

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class LogoutFragment : DialogFragment() {
    internal interface LogoutListener{
        fun onLogout(result: Boolean)
    }
    private var listener: LogoutListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as LogoutListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm Logout")
            .setPositiveButton("Yes"){
                    _, _ ->
                listener?.onLogout(true)
            }
            .setNegativeButton("No"){
                    _, _ ->
                listener?.onLogout(false)
            }
        return builder.create()
    }
}