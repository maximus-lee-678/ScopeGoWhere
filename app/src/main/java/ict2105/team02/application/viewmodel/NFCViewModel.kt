package ict2105.team02.application.viewmodel

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ict2105.team02.application.utils.NFCStatus
import kotlinx.coroutines.launch
import kotlin.experimental.and

class NFCViewModel: ViewModel() {
    private val TAG = this::class.simpleName!!
    private val _nfcStatus = MutableLiveData<NFCStatus>().apply {
        value = NFCStatus.NoOperation
    }
    var authUser:List<Long> = listOf(-1454180711 , -1195384119, -1194713799, -1194525767)

    private fun getNFCFlags(): Int{
        return NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B or NfcAdapter.FLAG_READER_NFC_F or NfcAdapter.FLAG_READER_NFC_V or NfcAdapter.FLAG_READER_NFC_BARCODE
    }
    private fun getExtras(): Bundle {
        val options = Bundle()
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 30000)
        return options
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun enableReaderMode(context: Context, activity: Activity, callback: NfcAdapter.ReaderCallback){
        val flags:Int = getNFCFlags()
        val extras: Bundle = getExtras()
        try {
            NfcAdapter.getDefaultAdapter(context).enableReaderMode(activity, callback, flags, extras)
        } catch (ex: UnsupportedOperationException){
            Log.e(TAG, "UnsupportedOperation ${ex.message}", ex)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun disableReaderMode(context: Context, activity: Activity){
        NfcAdapter.getDefaultAdapter(context).disableReaderMode(activity)
    }

    fun checkEnabled(context: Context): Boolean{
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        return !(nfcAdapter == null || nfcAdapter?.isEnabled == false)
    }

    fun readTag(tag: Tag){
        viewModelScope.launch {
            val id: ByteArray = tag.id
            if (getDec(id) in authUser){
                changeStatus(NFCStatus.Auth)
            } else {
                changeStatus(NFCStatus.NotAuth)
            }
        }
    }

    private fun getDec(bytes : ByteArray) : Long {
        var result : Long = 0
        var factor : Long = 1
        for (i in bytes.indices) {
            val value : Long = bytes[i].and(0xffL.toByte()).toLong()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    fun observeTag() : MutableLiveData<NFCStatus> {
        return _nfcStatus
    }

    private fun changeStatus(status: NFCStatus){
        _nfcStatus.value = status
    }
}