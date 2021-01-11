package edu.upc.dsa.firefighteradventure.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.MyAdapter;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.ExampleModel;
import edu.upc.dsa.firefighteradventure.models.RepoModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment2 extends Fragment{

    private View view;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment2, container, false);
        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        mainActivity.setBackActivated(true);

        if (!mainActivity.isNetworkConnected()) {

            Navigation.findNavController(view).navigate(R.id.noInternetConnectionFragment);
            return;

        }

        exampleRequest();

    }

    public void getFollowers() {

        String username = getArguments().getString("username"); //Recogemos la variable username

        mainActivity.setLoadingData(true); //Empieza el loadingbar

        Call<List<RepoModel>> resp = mainActivity.getExampleService().getUserRepos(username); //recogemos el resultado

        resp.enqueue(new Callback<List<RepoModel>>() {

            @Override
            public void onResponse(Call<List<RepoModel>> call, Response<List<RepoModel>> response) {

                mainActivity.setLoadingData(false); //Desctivamos la barra de carga

                switch (response.code()) { //Segun la respuesta nos da un resultado o otro

                    case 200:
                        List<RepoModel> repoModels = response.body();

                        RecyclerView recyclerView; //Creamos el RecyclerView

                        recyclerView = view.findViewById(R.id.my_recycler_view);

                        recyclerView.setHasFixedSize(true);

                        RecyclerView.LayoutManager layoutManager;
                        layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);

                        // define an adapter

                        RecyclerView.Adapter mAdapter;
                        mAdapter = new MyAdapter(repoModels);
                        recyclerView.setAdapter(mAdapter);

                        break;

                    default:
                        Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);
                        break;

                }

            }

            @Override
            public void onFailure(Call<List<RepoModel>> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }

    public void exampleRequest() {

        String username = getArguments().getString("username");

        mainActivity.setLoadingData(true);

        Call<ExampleModel> resp = mainActivity.getExampleService().getUser(username);

        resp.enqueue(new Callback<ExampleModel>() {

            @Override
            public void onResponse(Call<ExampleModel> call, Response<ExampleModel> response) {

                mainActivity.setLoadingData(false);

                switch (response.code()) {

                    case 200:

                        ExampleModel exampleModel = response.body();

                        TextView tvFollowing = view.findViewById(R.id.textView2); //Declaramos los text para poder editarlos
                        TextView tvFollowers = view.findViewById(R.id.textView6);
                        TextView tvRepositories = view.findViewById(R.id.textView3);
                        TextView tvName = view.findViewById(R.id.textView8);


                        ImageView ivProfilePhoto = view.findViewById(R.id.imageView); //Declaramos las imagenes para poder editarlos

                        tvFollowing.setText("Following: " + String.valueOf(exampleModel.getFollowing()));
                        tvFollowers.setText("Followers: " + String.valueOf(exampleModel.getFollowers()));
                        tvRepositories.setText("Repositories: " + String.valueOf(exampleModel.getPublic_repos()));
                        tvName.setText(username);

                        Picasso.get().load(exampleModel.getAvatar_url()).into(ivProfilePhoto);

                        getFollowers();

                        break;

                    case 404:
                        Toast.makeText(getContext(), "User not exists", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);
                        break;

                }

            }

            @Override
            public void onFailure(Call<ExampleModel> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }
}
