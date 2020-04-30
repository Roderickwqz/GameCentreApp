package csc207.fall2018.gamecentreapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import csc207.fall2018.gamecentreapp.DataBase.GameStateDataBase;
import csc207.fall2018.gamecentreapp.DataBase.ScoreBoard;
import csc207.fall2018.gamecentreapp.DataBase.UserDataBase;
import csc207.fall2018.gamecentreapp.GameCentreActivity.MainActivity;
import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.Session;

public class DeleteUserDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_user_dialog, null);

        builder.setView(view).setTitle("Delete User Info")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUserInfo(getContext());
                        Session session = Session.getInstance(getContext());
                        session.logOut();
                        Intent returnIntent = new Intent(getContext(), MainActivity.class);
                        startActivity(returnIntent);
                    }
                });
        return builder.create();
    }

    private void deleteUserInfo(Context context){
        GameStateDataBase gameStateDataBase = new GameStateDataBase(context);
        ScoreBoard scoreBoard = new ScoreBoard(context);
        UserDataBase userDataBase = new UserDataBase(context);
        Session session = Session.getInstance(context);
        gameStateDataBase.deleteUser(session.getCurrentUserName());
        scoreBoard.deleteUser(session.getCurrentUserName());
        userDataBase.deleteUser(session.getCurrentUserName());
    }

}
