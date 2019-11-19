package org.arnoid.string.processor.jvm

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.arnoid.string.processor.DictionaryStringProvider

class FsBackedStringProvider(
        dictionary: MutableMap<String, String>,
        emptyValue: String,
        private val fileLookUp: (key: String) -> String,
        private val gson: Gson = Gson()
) : DictionaryStringProvider(dictionary, emptyValue) {


    override fun get(key: String): String {
        if (dictionary.containsKey(key)) {
            return super.get(key)
        } else {
            val filePath = fileLookUp(key)
            val jsonObject = gson.fromJson(filePath, JsonObject::class.java)

            TODO("implement json parsing")
            return jsonObject.toString()
        }
    }

}

