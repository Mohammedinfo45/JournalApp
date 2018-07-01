package com.example.android.journalapp;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.android.journalapp.database.DatabaseHelper;
import com.example.android.journalapp.database.model.Diary;
import com.example.android.journalapp.presenter.DiariesAdapter;
import com.example.android.journalapp.utilities.MyDividerItemDecoration;
import com.example.android.journalapp.utilities.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private DiariesAdapter mAdapter;
    private List<Diary> diariesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView nodiariesView;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Toolbar toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);


        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        nodiariesView = findViewById(R.id.empty_diaries_view);

        db = new DatabaseHelper(this);

        diariesList.addAll(db.getAllDiaries());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaryDialog(false, null, -1);
            }
        });

        mAdapter = new DiariesAdapter(this, diariesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyDiaries();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        menuInflater.inflate(R.menu.logging_menu,menu);
        menuInflater.inflate(R.menu.sync_menu,menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Inserting new Diary in db
     * and refreshing the list
     */
    private void createDiary(String desc) {
        // inserting Diary in db and getting
        // newly inserted Diary id
        long id = db.insertDiary(desc);

        // get the newly inserted Diary from db
        Diary n = db.getDiary(id);

        if (n != null) {
            // adding new Diary to array list at 0 position
            diariesList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyDiaries();
        }
    }
//
//    /**
//     * Updating Diary in db and updating
//     * item in the list by its position
//     */
    private void updateDiary(String diary, int position) {
        Diary n = diariesList.get(position);
        // updating Diary text
        n.setDesc(diary);

        // updating Diary in db
        db.updateDiary(n);

        // refreshing the list
        diariesList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyDiaries();
    }
//
//    /**
//     * Deleting Diary from SQLite and removing the
//     * item from the list by its position
//     */
    private void deleteDiary(int position) {
        // deleting the Diary from db
        db.deleteDiary(diariesList.get(position));

        // removing the Diary from the list
        diariesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyDiaries();
    }
//
//    /**
//     * Opens dialog with Edit - Delete options
//     * Edit - 0
//     * Delete - 0
//     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showDiaryDialog(true, diariesList.get(position), position);
                } else {
                    deleteDiary(position);
                }
            }
        });
        builder.show();
    }

//
//    /**
//     * Shows alert dialog with EditText options to enter / edit
//     * a Diary.
//     * when shouldUpdate=true, it automatically displays old Diary and changes the
//     * button text to UPDATE
//     */
    private void showDiaryDialog(final boolean shouldUpdate, final Diary diary, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.diary_content, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputDiary = view.findViewById(R.id.diary_editor);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_diary_title) : getString(R.string.lbl_edit_diary_title));

        if (shouldUpdate && diary != null) {
            inputDiary.setText(diary.getDesc());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputDiary.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter diary!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating Diary
                if (shouldUpdate && diary != null) {
                    // update Diary by it's id
                    updateDiary(inputDiary.getText().toString(), position);
                } else {
                    // create new Diary
                    createDiary(inputDiary.getText().toString());
                }
            }
        });
    }

//    /**
//     * Toggling list and empty Diaries view
//     */
    private void toggleEmptyDiaries() {
        // you can check diariesList.size() > 0

        if (db.getDiaryCount() > 0) {
            nodiariesView.setVisibility(View.GONE);
        } else {
            nodiariesView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

