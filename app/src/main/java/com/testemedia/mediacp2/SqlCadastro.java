package com.testemedia.mediacp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SqlCadastro extends SQLiteOpenHelper {
	// All Static variables
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// ---- Table name
	private static final String TABLE_MATERIAS = "Materias";

	// ---- Table columns
	private static final String _ID = "ID";
	private static final String KEY_NOME = "Nome";
	private static final String KEY_NOTA1 = "Tri1";
	private static final String KEY_NOTA2 = "Tri2";
	private static final String KEY_NOTA3 = "Tri3";
	private static final String KEY_PRENOTA3 = "Est3Tri";
	private static final String KEY_MA = "MA";
	private static final String KEY_PREPFV = "EstPFV";
	private static final String KEY_PFV = "PFV";

	public SqlCadastro(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MATERIAS + "("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOME
				+ " TEXT," + KEY_NOTA1 + " FLOAT," + KEY_NOTA2 + " FLOAT,"
				+ KEY_PRENOTA3 + " FLOAT," + KEY_NOTA3 + " FLOAT," + KEY_MA
				+ " FLOAT," + KEY_PREPFV + " FLOAT, " + KEY_PFV + " FLOAT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAS);
		onCreate(db);
	}

	public void addMateria(Materias materia) {
		//Abre a DB
		SQLiteDatabase db = this.getWritableDatabase();

		// Declara uma variavel para os valores.
		ContentValues values = new ContentValues();
		
		//Inserindo as informacoes do parametro na variavel.
		values.put(KEY_NOME, materia.getNome());
		values.put(KEY_NOTA1, Float.toString(materia.getNota1()));
		values.put(KEY_NOTA2, Float.toString(materia.getNota2()));
		values.put(KEY_PRENOTA3, Float.toString(materia.getPreNota3()));
		values.put(KEY_NOTA3, Float.toString(materia.getNota3()));
		values.put(KEY_MA, Float.toString(materia.getMA()));
		values.put(KEY_PREPFV, Float.toString(materia.getPrePFV()));
		values.put(KEY_PFV, Float.toString(materia.getPFV()));

		// Inserindo os valores na tabela.
		db.insertOrThrow(TABLE_MATERIAS, null, values);
		
		//Fecha a DB
		db.close();
	}

	public void deleteMateria(Materias materia) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// Checa se o nome nao e nulo nem esta vazio.
		if (materia.getNome() != null && materia.getNome() != "")
			db.delete(TABLE_MATERIAS, "Nome = ?",
					new String[] { materia.getNome() }); // args

		// Fechando a DB
		db.close();
	}

	public int buscarIdPorNome(String nome) {

		SQLiteDatabase db = this.getWritableDatabase();

		int id;

		// Define e faz a selecao na tabela.
		String selection = "SELECT " + _ID + " FROM " + TABLE_MATERIAS
				+ " WHERE Nome = '" + nome + "'";
		Cursor cursor = db.rawQuery(selection, null);

		// Move o cursor para o primeiro (e unico) resultado.
		if (cursor.moveToFirst()) {
			// Pega o valor do ID e retorna-o.
			id = cursor.getInt(cursor.getColumnIndex(_ID));
			db.close();
			cursor.close();
			return id;
		} else {
			// Caso nao retorne resultados, o valor sera -1.
			db.close();
			cursor.close();
			return -1;
		}

	}

	public String[] ListarMaterias() {

		SQLiteDatabase db = this.getWritableDatabase();

		String[] listaMaterias = new String[50];
		int i = 0;
		String nome;

		// Seleciona todos os nomes da tabela e faz a busca.
		String selectQuery = "SELECT Nome FROM " + TABLE_MATERIAS;
		Cursor cursor1 = db.rawQuery(selectQuery, null);

		// Move o cursor ao primeiro nome, caso nao esteja vazia.
		if (cursor1.moveToFirst()) {
			do {
				// Se o nome estiver vazio, preenche com espaco.
				if (cursor1.getString(cursor1.getColumnIndex("Nome")) == null) {
					nome = "";
				} else {
					// Se nao estiver vazio, passa o nome,
					nome = cursor1.getString(cursor1.getColumnIndex("Nome"));
				}
				// Coloca o nome pego na variavel.
				listaMaterias[i] = nome;
				i++;
			} while (cursor1.moveToNext()); // Move cursor para o proximo nome.
		}

		// ---- Preenche os espacos vazios da lista.
		// ---- Gambiarra
		for (i = 0; i < 50; i++)
			if (listaMaterias[i] == null)
				listaMaterias[i] = "";

		// Fecha as variaveis.
		cursor1.close();
		db.close();

		// Retorna o valor
		return listaMaterias;
	}

	public int ContarMaterias() {
		SQLiteDatabase db = this.getWritableDatabase();
		String contar = "SELECT COUNT(*) FROM " + TABLE_MATERIAS; // Definicao
																	// da
																	// consulta.
																	// 1
		SQLiteStatement statement = db.compileStatement(contar); // Faz a
																	// consulta.
																	// 2
		float count = statement.simpleQueryForLong(); // Retorna o valor da
														// consulta 3
														// como um Long (ou
		db.close(); // float). 4
		return (int) count;
	}

	public Materias buscarId(int ID) {

		SQLiteDatabase db = this.getWritableDatabase();
		// Faz uma busca retornando apenas as colunas listadas onde
		// a coluna _ID tenha o valor passado de parametro.
		Cursor cursor = db.query(TABLE_MATERIAS, new String[] { _ID, KEY_NOME,
				KEY_NOTA1, KEY_NOTA2, KEY_NOTA3, KEY_PFV }, _ID + "=" + ID,
				null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			Materias materia = new Materias(Integer.parseInt(cursor
					.getString(0)), cursor.getString(1),
					Float.parseFloat(cursor.getString(2)),
					Float.parseFloat(cursor.getString(3)),
					Float.parseFloat(cursor.getString(4)),
					Float.parseFloat(cursor.getString(5)));
			cursor.close();
			db.close();
			return materia;
		} else {
			Materias materia = new Materias();
			cursor.close();
			db.close();
			return materia;
		}
	}

	public void atualizarMateria(Materias materia, int ID) {

		// Passa essa database dessa classe (SqlCadastro) para uma do tipo
		// SQLiteDatabase(padrao)
		SQLiteDatabase db = this.getWritableDatabase();

		// Cria uma variavel para armazenar os valores.
		ContentValues values = new ContentValues();

		// Adicionando os valores da materia recebida como parametro
		// em cada coluna determinada.
		values.put(_ID, ID);
		values.put(KEY_NOME, materia.getNome());
		values.put(KEY_NOTA1, Float.toString(materia.getNota1()));
		values.put(KEY_NOTA2, Float.toString(materia.getNota2()));
		values.put(KEY_PRENOTA3, Float.toString(materia.getPreNota3()));
		values.put(KEY_NOTA3, Float.toString(materia.getNota3()));
		values.put(KEY_MA, Float.toString(materia.getMA()));
		values.put(KEY_PREPFV, Float.toString(materia.getPrePFV()));
		values.put(KEY_PFV, Float.toString(materia.getPFV()));

		// Atualiza o registro na tabela.
		db.replace(TABLE_MATERIAS, null, values);

		db.close(); // Fechando a database.
	}

}