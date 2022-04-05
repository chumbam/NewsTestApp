package com.example.newsapptest.utils

import androidx.room.TypeConverter
import com.example.newsapptest.models.Source


class Converter {

/*    Это функция преобразователь из кастомного класса Source рассположенного в модели Article и
помечена специально аннотацией. Она преобразует класс Source в String
*/
    @TypeConverter
    fun fromSourceToString(source: Source): String {
        return source.name
    }

//    Здесь же происходит обратная конвертация
    @TypeConverter
    fun fromStringToSource(string: String):Source {
        return Source(string, string)
    }


}