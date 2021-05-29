package com.android_app_project.app.ui.view.sensor

// Définition de la Class qui sera dans notre RecyclerView
// Objet avec pour paramètres : un nom, un icone et un clic
data class SensorItem(val name: String, val icon: Int, val onClick: (() -> Unit))