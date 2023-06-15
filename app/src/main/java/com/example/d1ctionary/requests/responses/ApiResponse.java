package com.example.d1ctionary.requests.responses;

import java.io.IOException;

import retrofit2.Response;

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
public class ApiResponse<T> {

    public ApiResponse<T> create(Throwable error){
        return new ApiErrorResponse<>(!error.getMessage().equals("") ? error.getMessage() : "unknown error");
    }

    public ApiResponse<T> create(Response<T> response){

        if(response.isSuccessful()){
            T body = response.body();

            if(body == null || response.code() == java.net.HttpURLConnection.HTTP_NO_CONTENT){
                return new ApiEmptyResponse<>();
            }
            else{
                return new ApiSuccessResponse<>(body);
            }
        }
        else{
            String errorMsg = "";
            try {
                errorMsg = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                errorMsg = response.message();
            }
            return new ApiErrorResponse<>(errorMsg);
        }
    }


    public class ApiSuccessResponse<T> extends ApiResponse<T> {

        private T body;

        ApiSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }

    }


    public class ApiErrorResponse<T> extends ApiResponse<T> {

        private String errorMsg;

        ApiErrorResponse(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

    }


    public class ApiEmptyResponse<T> extends ApiResponse<T> { }
}
