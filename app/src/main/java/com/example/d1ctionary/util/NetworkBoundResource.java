package com.example.d1ctionary.util;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.d1ctionary.AppExecutors;
import com.example.d1ctionary.requests.responses.ApiResponse;


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private static final String TAG = "NetworkBoundResource";
    private AppExecutors appExecutors;
    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    private void init(){

        result.setValue((Resource<ResultType>)Resource.loading(null));

        final LiveData<ResultType> dbSource = loadFromDb();

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType resultType) {
                result.removeSource(dbSource);
                if(shouldFetch(resultType)){
                    //fetch from network
                    fetchFromNetwork(dbSource);
                }else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType resultType) {
                            setValue(Resource.success(resultType));
                        }
                    });
                }

            }
        });

    }

    private void setValue(Resource<ResultType> value){
        if(result.getValue() != value){
            result.setValue(value);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource){
        Log.d(TAG, "fetchFromNetwork: Called");

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType resultType) {
                setValue(Resource.loading(resultType));
            }
        });

        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(ApiResponse<RequestType> requestTypeApiResponse) {
                result.removeSource(dbSource);
                result.removeSource(apiResponse);

                if(requestTypeApiResponse instanceof ApiResponse.ApiSuccessResponse){
                    Log.d(TAG, "onChanged: ApiSuccessfulRes");
                    Log.d(TAG, "onChanged:  WORD: " + requestTypeApiResponse);
                    appExecutors.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            saveCallResult((RequestType) processResponse((ApiResponse.ApiSuccessResponse)requestTypeApiResponse));


                            appExecutors.getMainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    result.addSource(loadFromDb(), new Observer<ResultType>() {
                                        @Override
                                        public void onChanged(ResultType resultType) {
                                            setValue(Resource.success(resultType));
                                            Log.d(TAG, "onChanged: loadFromDB called");
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else if (requestTypeApiResponse instanceof ApiResponse.ApiErrorResponse){
                    //
                    Log.d(TAG, "onChanged: APiErrorRes");
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType resultType) {
                            setValue(Resource.error(((ApiResponse.ApiErrorResponse)requestTypeApiResponse).getErrorMsg(),resultType));
                        }
                    });
                } else if (requestTypeApiResponse instanceof ApiResponse.ApiEmptyResponse){
                    //

                }
            }
        });

    }

    private RequestType processResponse(ApiResponse.ApiSuccessResponse apiSuccessResponse){
        return (RequestType) apiSuccessResponse.getBody();
    }
    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached data from the database.
    @NonNull @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData(){
        return result;
    };


}