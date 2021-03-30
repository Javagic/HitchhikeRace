package me.javagic.hitchhikerace.utils

import android.content.Context
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.library.simpleKML.Serializer
import me.javagic.hitchhikerace.library.simpleKML.model.Coordinate
import me.javagic.hitchhikerace.library.simpleKML.model.Kml
import me.javagic.hitchhikerace.library.simpleKML.model.Placemark
import me.javagic.hitchhikerace.library.simpleKML.model.Point
import java.io.File
import java.io.FileOutputStream

//TODO open KML files
fun openMap(context: Context, eventList: List<RaceEventEntity>) {
    Kml().apply {
        feature = Placemark().apply {
            geometryList = eventList.map {
                Point().apply {
                    coordinates =
                        Coordinate(
                            it.longitude,
                            it.latitude,
                            15.toDouble()
                        )
                }
            }
        }
    }
        .let { writeFileOnInternalStorage(context, "race1.kml", it) }

}

fun writeFileOnInternalStorage(mcoContext: Context, sFileName: String, kml: Kml) {
    val kmlSerializer = Serializer()
    val file = File(mcoContext.filesDir, "map")
    if (!file.exists()) {
        file.mkdir()
    }
    try {
        val gpxfile = File(file, sFileName)
        val out = FileOutputStream(gpxfile)
        kmlSerializer.write(kml, out)
        out.flush()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}