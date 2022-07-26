package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.dto.CountryMapper
import data.remote.MiscApi
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.ResponseCountryDto
import domain.model.Country
import domain.repository.Repository
import main.ApplicationClass

class RepositoryImpl(
    private val miscApi: MiscApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper,
    private val countryMapper: CountryMapper
) : Repository, ApiResponseHandler(app, networkErrorMapper) {
    override suspend fun getCountries(): DataState<ArrayList<Country>> {
        return when (val response = call { miscApi.getCountries() }) {
            is DataState.Failure -> response
            DataState.Loading    -> {
                DataState.Success(countryMapper.fromEntityList(ResponseCountryDto.getFake().countries))
            }
            is DataState.Success -> DataState.Loading
        }
    }
}