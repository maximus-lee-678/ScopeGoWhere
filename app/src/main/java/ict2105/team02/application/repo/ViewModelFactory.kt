package ict2105.team02.application.repo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.viewmodel.*

class ViewModelFactory(
    private val viewModelType: String,
    private val application: MainApplication
) :
    /**
     * Parameters:
     * 0 - String name of ViewModel
     * 1 - MainApplication
     * (application as MainApplication) OR
     * (activity?.application as MainApplication)
     */
    ViewModelProvider.Factory {
    private val TAG: String = this::class.simpleName!!

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(TAG, "creating: $viewModelType")

        when (viewModelType) {
            "AuthViewModel" -> {
                if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                    return AuthViewModel(
                        application.authRepository
                    ) as T
                }
            }

            "CalendarViewModel" -> {
                if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
                    return CalendarViewModel(
                        application.userPreferencesRepository,
                        application.dataRepository
                    ) as T
                }
            }

            "EndoscopeViewModel" -> {
                if (modelClass.isAssignableFrom(EndoscopeViewModel::class.java)) {
                    return EndoscopeViewModel(
                        application.dataRepository
                    ) as T
                }
            }

            "EquipmentListViewModel" -> {
                if (modelClass.isAssignableFrom(EquipmentListViewModel::class.java)) {
                    return EquipmentListViewModel(
                        application.dataRepository
                    ) as T
                }
            }

            "HomeViewModel" -> {
                if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                    return HomeViewModel(
                        application.dataRepository
                    ) as T
                }
            }

            "NFCViewModel" -> {
                if (modelClass.isAssignableFrom(NFCViewModel::class.java)) {
                    return NFCViewModel(
                    ) as T
                }
            }

            "SampleViewModel" -> {
                if (modelClass.isAssignableFrom(SampleViewModel::class.java)) {
                    return SampleViewModel(
                        application.dataRepository
                    ) as T
                }
            }

            "ScheduleInfoViewModel" -> {
                if (modelClass.isAssignableFrom(ScheduleInfoViewModel::class.java)) {
                    return ScheduleInfoViewModel(
                        application.dataRepository
                    ) as T
                }
            }

            "ScopeDetailViewModel" -> {
                if (modelClass.isAssignableFrom(ScopeDetailViewModel::class.java)) {
                    return ScopeDetailViewModel(
                        application.dataRepository
                    ) as T
                }
            }

            "WashViewModel" -> {
                if (modelClass.isAssignableFrom(WashViewModel::class.java)) {
                    return WashViewModel(
                        application.dataRepository
                    ) as T
                }
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        // we wont get here bruh why must have return
        return 0 as T
    }
}
