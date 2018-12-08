package com.example.matej.arkanoid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Leaderboards extends Activity {

    Button mainMenu;
    Intent intent;
    ListView list;
    TextView bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        bestScore = (TextView)findViewById(R.id.bestScore);
        list = (ListView)findViewById(R.id.list);
        mainMenu = (Button)findViewById(R.id.mainMenu);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);


        int i = sp.getInt("i", 0);
        int highscore = sp.getInt("highscore", 0);

        //kontrola pokud i nebylo pridano do sharedpreferences
        if(i == 0){
            i = 1;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("i", i);
            editor.commit();
        }
        //inicializace adapteru a listu hashmap
        List<HashMap<String,String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.tex1,R.id.tex2});

        //pridavani score do listu
        intent = getIntent();
        if(intent.getBooleanExtra("write", false)){
            HashMap<String, String> nameScore = new HashMap<>();
            nameScore.put("Name: " + intent.getStringExtra("name"),"Score: " + intent.getStringExtra("score"));

            saveHashMap(Integer.toString(i), nameScore);

            if(highscore < Integer.parseInt(intent.getStringExtra("score")) ){
                highscore = Integer.parseInt(intent.getStringExtra("score"));
                saveHashMap("bestScore", nameScore);
            }

        }

        //nacitani vysledku z sharedpreferences
        HashMap<String, String> restoredScore = new HashMap<>();
        for(int j = 1; j <= i; j++){
            restoredScore = getHashMap(Integer.toString(j));
            if(restoredScore != null){
                Iterator it2 = restoredScore.entrySet().iterator();
                HashMap<String,String> result = new HashMap<>();
                Map.Entry pair = (Map.Entry)it2.next();
                result.put("First Line", pair.getKey().toString());
                result.put("Second Line", pair.getValue().toString());
                listItems.add(result);
                restoredScore.clear();
            }
        }

        //nacitani bestscore
        HashMap<String, String> bestScoore = new HashMap<>();
        bestScoore = getHashMap("bestScore");
        if(bestScoore != null){
            Iterator it2 = bestScoore.entrySet().iterator();
            HashMap<String,String> result = new HashMap<>();
            Map.Entry pair = (Map.Entry)it2.next();
            result.put("First Line", pair.getKey().toString());
            result.put("Second Line", pair.getValue().toString());
            bestScore.setText("Highscore\n" + pair.getKey().toString() + "   " +pair.getValue().toString());
        }

        list.setAdapter(adapter);
        i++;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("highscore", highscore);
        editor.putInt("i", i);
        editor.commit();

    }

    //metoda na ulozeni hashmapy
    public void saveHashMap(String key , Object obj) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(key,json);
        editor.apply();     // This line is IMPORTANT !!!
    }
    //metoda na ziskani hashmapy
    public HashMap<String,String> getHashMap(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key,"");
        java.lang.reflect.Type type = new TypeToken<HashMap<String,String>>(){}.getType();
        HashMap<String,String> obj = gson.fromJson(json, type);
        return obj;
    }
    //tlacitko do menu
    public void mainmenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
