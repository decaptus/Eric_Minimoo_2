package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class MainMenuFragment extends Fragment {

    private View view;
    private MainActivity mainActivity;
    EditText editTextTextPersonName;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    //Esperamos a que la vista de la app este bien creada para que no pete el programa
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return view;

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTextPersonName = view.findViewById(R.id.editTextTextPersonName); //Recogemos lo que pone en el text

        mainActivity = (MainActivity) getActivity();

        mainActivity.setBackActivated(false);

        if (!mainActivity.isNetworkConnected()) {

            Navigation.findNavController(view).navigate(R.id.noInternetConnectionFragment);
            return;

        }

        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() { //Boton
            @Override
            public void onClick(View view) {

                String username = editTextTextPersonName.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("username", username);

                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_fragment24, bundle);
            }
        });
    }

}