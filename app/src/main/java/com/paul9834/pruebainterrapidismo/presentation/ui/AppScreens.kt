package com.paul9834.pruebainterrapidismo.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paul9834.pruebainterrapidismo.presentation.viewmodel.MainViewModel

val InterOrange = Color(0xFFFF6600)
val InterBlue = Color(0xFF003366)
@Composable
fun HomeScreen(
    vm: MainViewModel,
    onGoToTables: () -> Unit,
    onGoToLocations: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Perfil", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = InterBlue)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow("Usuario", vm.user?.usuario ?: "Cargando...")
                InfoRow("ID", vm.user?.identificacion ?: "Cargando...")
                InfoRow("Nombre", vm.user?.nombre ?: "Cargando...")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onGoToTables,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = InterOrange),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Ver Tablas Sincronizadas", fontSize = 16.sp) }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onGoToLocations,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Consultar Localidades", color = InterBlue) }

        Spacer(modifier = Modifier.weight(1f))
        Text(vm.statusMessage, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, fontWeight = FontWeight.Light, fontSize = 12.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 16.sp)
    }
}

@Composable
fun TablesScreen(vm: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Esquema de Tablas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = InterBlue)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(vm.tables) { table ->
                ListItem(headlineContent = { Text(table.nombreTabla) })
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun LocationsScreen(vm: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Localidades Recogidas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = InterOrange)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(vm.locations) { loc ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(loc.abreviacionCiudad, fontWeight = FontWeight.Bold)
                        Text(loc.nombreCompleto)
                    }
                }
            }
        }
    }
}