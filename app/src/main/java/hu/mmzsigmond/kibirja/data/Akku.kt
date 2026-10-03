package hu.mmzsigmond.kibirja.data

import android.content.Context
import android.os.BatteryManager

// null, ha a telefon nem tudja megmondani
fun akkuSzazalek(context: Context): Int? {
    val bm = context.getSystemService(BatteryManager::class.java) ?: return null
    val ertek = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    return if (ertek in 0..100) ertek else null
}
