package com.linesapp.newsappjetpackcompose.db

import androidx.room.TypeConverter
import com.linesapp.newsappjetpackcompose.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}