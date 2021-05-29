package com.android_app_project.app.ui.view.sensor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android_app_project.app.R

class AdapterSensor(private val deviceList: Array<SensorItem>, private val onClick: ((selectedDevice: SensorItem) -> Unit)? = null) : RecyclerView.Adapter<AdapterSensor.ViewHolder>() {

    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showItem(device: SensorItem, onClick: ((selectedDevice: SensorItem) -> Unit)? = null) {
            itemView.findViewById<TextView>(R.id.nameAction).text = device.name
            itemView.findViewById<ImageView>(R.id.iconAction).setImageResource(device.icon)

            itemView.setOnClickListener {
                device.onClick.invoke()
            }
        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sensor_item_list, parent, false)
        return ViewHolder(
            view
        )
    }

    // Connect la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(deviceList[position], onClick)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

}