package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//banco que armazena os dados de sleepnight
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    //conecta o banco ao DAO
    abstract val sleepDatabaseDao: SleepDatabaseDao

    //define um objeto complementar, dando para add fun ao sleepdatabase
    companion object {

        //instance recebe uma referencia do banco e retorna via getinstance | evita dado repetido
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        //função aux para onter dados do banco | se o banco existir pega os dados, caso contrario cria um
        //evita sobrecarga no banco
        fun getInstance(context: Context): SleepDatabase {

        //varias threads solicitam ao banco ao memso tempo | é inicializado somente uma vez
            synchronized(this) {

                var instance = INSTANCE

                // se a instancia for null cria o banco
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"
                    )

                            //limpa e recria ao inves de migrar os dados
                            .fallbackToDestructiveMigration()
                            .build()

                    // atribui instance ao banco recem criado
                    INSTANCE = instance
                }

                // retorna instance caso não seja null
                return instance
            }
        }
    }
}
