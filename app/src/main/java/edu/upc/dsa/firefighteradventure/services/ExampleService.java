package edu.upc.dsa.firefighteradventure.services;


import java.util.List;

import edu.upc.dsa.firefighteradventure.models.ExampleModel;
import edu.upc.dsa.firefighteradventure.models.RepoModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ExampleService {

    @GET("users/{username}")
    Call<ExampleModel> getUser(
            @Path("username") String username
    );

    @GET("users/{username}/repos")
    Call<List<RepoModel>> getUserRepos(
            @Path("username") String username
    );

}