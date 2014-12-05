package com.example.binet.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    EditText somme = null;
    Button buttonSub1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        somme = (EditText) findViewById(R.id.editNameBook);
        buttonSub1 = (Button) findViewById(R.id.buttonSubmit1);
        buttonSub1.setOnClickListener(envoyerListenner);



        //Création d'une instance de ma classe LivresBDD
        LivresBDD livreBdd = new LivresBDD(this);

        //Création d'un livre
        Livre livre = new Livre("123456789", "Programmez pour Android");

        //On ouvre la base de données pour écrire dedans
        livreBdd.open();
        //On insère le livre que l'on vient de créer
        livreBdd.insertLivre(livre);

        //Pour vérifier que l'on a bien créé notre livre dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        Livre livreFromBdd = livreBdd.getLivreWithTitre(livre.getTitre());
        //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
        if(livreFromBdd != null){
            //On affiche les infos du livre dans un Toast
            Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
            //On modifie le titre du livre
            livreFromBdd.setTitre("J'ai modifié le titre du livre");
            //Puis on met à jour la BDD
            livreBdd.updateLivre(livreFromBdd.getId(), livreFromBdd);
        }

        //On extrait le livre de la BDD grâce au nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        //S'il existe un livre possédant ce titre dans la BDD
        if(livreFromBdd != null){
            //On affiche les nouvelles informations du livre pour vérifier que le titre du livre a bien été mis à jour
            Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
            //on supprime le livre de la BDD grâce à son ID
            livreBdd.removeLivreWithID(livreFromBdd.getId());
        }

        //On essaye d'extraire de nouveau le livre de la BDD toujours grâce à son nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        //Si aucun livre n'est retourné
        if(livreFromBdd == null){
            //On affiche un message indiquant que le livre n'existe pas dans la BDD
            Toast.makeText(this, "Ce livre n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }
        //Si le livre existe (mais normalement il ne devrait pas)
        else{
            //on affiche un message indiquant que le livre existe dans la BDD
            Toast.makeText(this, "Ce livre existe dans la BDD", Toast.LENGTH_LONG).show();
        }

        livreBdd.close();

    }

    public View.OnClickListener envoyerListenner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String t =somme.getText().toString();
            //resultat.setText(somme+" $ vaut "+ prix + " € ");


            LivresBDD livreBdd = new LivresBDD(getApplicationContext());
            Livre livre = new Livre(t, "Programmez pour Android");

            //On ouvre la base de données pour écrire dedans
            livreBdd.open();
            //On insère le livre que l'on vient de créer
            livreBdd.insertLivre(livre);

            //Pour vérifier que l'on a bien créé notre livre dans la BDD
            //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
            Livre livreFromBdd = livreBdd.getLivreWithTitre(livre.getTitre());
            //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
            if(livreFromBdd != null) {
                //On affiche les infos du livre dans un Toast
                Toast.makeText(getApplicationContext(), livreFromBdd.toString(), Toast.LENGTH_LONG).show();
            }
            livreBdd.close();

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
