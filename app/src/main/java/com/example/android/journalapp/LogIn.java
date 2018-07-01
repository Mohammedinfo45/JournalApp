package com.example.android.journalapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

public class LogIn extends Fragment {
    CheckedTextView checkedTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_log_in, container, false);
        checkedTextView=view.findViewById(R.id.here_checked_text_view);
        checkedTextView.setOnClickListener(new View.OnClickListener() {
               @Override
                  public void onClick(View view) {
            ((SignInActivity) getActivity()).showFragment(new SignIn());
            }});
            return view;
    }
}


//    public void ChangeFragment(View view) {
//        btn = view.findViewById(R.id.btn_test);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("TAG", "moh100");
//                SignIn fg = new SignIn();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                ft.replace(R.id.place_view, fg);
//                ft.addToBackStack(null);
//                ft.commit();
//                Log.e("TAG", "moh");
//            }
//        });
//    }}

