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

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.testemedia.mediacp2.R;

public class EditarLista extends AppCompatActivity {

    ListView lista;
    private String[] materias;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editarlista_layout);

        materias = new String[50];
        SqlCadastro db = new SqlCadastro(this);
        materias = db.listarMaterias();

        lista = (ListView) findViewById(R.id.lista);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.itemlista, materias);
        lista.setAdapter(adapter);
        registerForContextMenu(lista);
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
        SqlCadastro db = new SqlCadastro(this);
        Materias materia = new Materias();
        int menuItemIndex = item.getItemId();
        EasyTracker tracker = EasyTracker.getInstance(EditarLista.this);

        String[] menuItems = {"Editar", "Remover"};
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = materias[info.position];
        TextView text = (TextView) findViewById(R.id.footer);
        text.setText(String.format("Você selecionou %s a matéria %s",
                menuItemName, listItemName));
        materia.setNome(listItemName);

        if (menuItemName == "Remover") {
            db.deleteMateria(materia);
            db.close();
            // Toast de remo��o
            Context context = getApplicationContext();
            CharSequence ToastText = "Matéria " + listItemName
                    + " removida com sucesso.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, ToastText, duration);
            toast.show();

            //Google Analytics
            tracker.send(MapBuilder.createEvent("Boletim", "ListaDeEdicao",
                    "MateriaRemovida", null).build());

            // Atualiza a atividade ap�s a remo��o.
            onCreate(null);
        } else if (menuItemName == "Editar") {
            int id = db.buscarIdPorNome(materia.getNome());
            Log.i("ID Enviado:", "" + id);
            Intent intent = new Intent(this, EditarMateria.class);
            intent.putExtra("id", id);

            //Google Analytics
            tracker.send(MapBuilder.createEvent("Boletim", "ListaDeEdicao",
                    "IrEditarMateria", null).build());
            db.close();
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, Boletim.class);
        startActivity(intent);
        finish();
    }

}
