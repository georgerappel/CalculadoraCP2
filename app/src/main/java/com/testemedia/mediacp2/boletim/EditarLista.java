package com.testemedia.mediacp2.boletim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.testemedia.mediacp2.R;

public class EditarLista extends AppCompatActivity {

    ListView lista;
    private String[] materias;
    private SqlCadastro db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editarlista_layout);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        lista = (ListView) findViewById(R.id.lista);

        db = new SqlCadastro(this);
        materias = new String[50];

        listarMaterias();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lista) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(materias[info.position]);
            String[] menuItems = {"Editar", "Remover"};
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Materias materia = new Materias();
        int menuItemIndex = item.getItemId();

        String[] menuItems = {"Editar", "Remover"};
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = materias[info.position];
        TextView text = (TextView) findViewById(R.id.footer);
        text.setText(String.format("Você selecionou %s a matéria %s",
                menuItemName, listItemName));
        materia.setNome(listItemName);

        if(listItemName.trim().isEmpty()){
            return true;
        } else if (menuItemName == "Remover") {
            db.deleteMateria(materia);
            db.close();
            // Toast de remoção
            Context context = getApplicationContext();
            CharSequence ToastText = "Matéria " + listItemName
                    + " removida com sucesso.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, ToastText, duration);
            toast.show();

            // Atualiza a atividade ap�s a remo��o.
//            finish();
//            Intent intent = new Intent(this, EditarLista.class);
//            startActivity(intent);
//            onCreate(null);
            listarMaterias();
        } else if (menuItemName == "Editar") {
            int id = db.buscarIdPorNome(materia.getNome());
            Log.i("ID Enviado:", "" + id);
            Intent intent = new Intent(this, EditarMateria.class);
            intent.putExtra("id", id);

            db.close();
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, Boletim.class);
        startActivity(intent);
        finish();
    }

    private void listarMaterias(){
        materias = db.listarMaterias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.itemlista, materias);
        lista.setAdapter(adapter);
        registerForContextMenu(lista);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
