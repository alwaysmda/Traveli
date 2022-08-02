package ui.travelDetail

import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.TravelDetail
import domain.model.TravelPreview
import domain.usecase.travel.TravelUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import util.extension.log
import javax.inject.Inject


@HiltViewModel
class TravelDetailViewModel @Inject constructor(
    app: ApplicationClass,
    private val travelUseCases: TravelUseCases,
    val exoPlayer: ExoPlayer,
) : BaseViewModel<TravelDetailEvents, TravelDetailAction>(app), TravelDetailAction {


    //local
    private var travelDetails = listOf<TravelDetail>()
    private lateinit var travelPreview: TravelPreview


    override fun onStart(travelPreview: TravelPreview) {
        this.travelPreview = travelPreview
        if (travelDetails.isEmpty()) {
            getTravelDetail(travelPreview)
        } else {
            viewModelScope.launch { _event.send(TravelDetailEvents.UpdateTravelDetail(travelDetails)) }
        }

    }

    override fun onLinkClick(url: String) {
        viewModelScope.launch {
            _event.send(TravelDetailEvents.OpenUrl(url))
        }
    }

    override fun onBackPress() {
        viewModelScope.launch {
            _event.send(TravelDetailEvents.NavBack)
        }
    }


    private fun getTravelDetail(travelPreview: TravelPreview) {

        viewModelScope.launch {
            travelUseCases.getTravelDetailUseCase(travelPreview.id).onEach {
                when (it) {
                    is DataState.Failure -> {
                        log("TRAVELDETAILVM:${it.message}")
                        travelDetails = listOf()
                        _event.send(TravelDetailEvents.TravelDetailError(it.message))
                    }
                    DataState.Loading    -> {
                        travelDetails = listOf()
                        _event.send(TravelDetailEvents.TravelDetailLoading)
                    }
                    is DataState.Success -> {
                        travelDetails = it.data
                        _event.send(TravelDetailEvents.UpdateTravelDetail(it.data))


                    }
                }

            }.launchIn(viewModelScope)
        }
    }


    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()

    }


}