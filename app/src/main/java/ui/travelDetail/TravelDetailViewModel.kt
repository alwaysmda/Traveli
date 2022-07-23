package ui.travelDetail

import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.travel.TravelDetail
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
    val exoPlayer: ExoPlayer
) : BaseViewModel<TravelDetailEvents, TravelDetailAction>(app), TravelDetailAction {


    //local
    private var travelDetails = listOf<TravelDetail>()

    override fun onStart() {
        if (travelDetails.isEmpty()) {
            getTravelDetail()
        } else {
            viewModelScope.launch { _event.send(TravelDetailEvents.UpdateTravelDetail(travelDetails)) }
        }

    }

    override fun onLinkClick(url: String) {
        viewModelScope.launch {
            _event.send(TravelDetailEvents.OpenUrl(url))
        }
    }


    private fun getTravelDetail() {

        viewModelScope.launch {
            travelUseCases.getTravelDetail(0).onEach {
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
                        exoPlayer.setMediaItem(MediaItem.fromUri(travelDetails[4].video))
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    override fun onCleared() {
        super.onCleared()

    }


}