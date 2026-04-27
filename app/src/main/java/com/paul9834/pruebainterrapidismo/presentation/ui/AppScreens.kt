package com.paul9834.pruebainterrapidismo.presentation.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paul9834.pruebainterrapidismo.presentation.viewmodel.MainViewModel
import com.paul9834.pruebainterrapidismo.presentation.ui.theme.InterOrange
import com.paul9834.pruebainterrapidismo.presentation.ui.theme.InterBlue
import com.paul9834.pruebainterrapidismo.presentation.ui.theme.BackgroundGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: MainViewModel,
    onGoToTables: () -> Unit,
    onGoToLocations: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(BackgroundGray)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = InterOrange.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp).size(48.dp),
                    tint = InterOrange
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "InterRapidismo",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = InterBlue
            )
            Text(
                "Prueba Técnica - Kevin Montealegre",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // User Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountCircle, null, tint = InterOrange, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Perfil de Usuario", fontWeight = FontWeight.Bold, color = InterBlue)
                    }
                    HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = Color.LightGray)

                    InfoRow("Usuario", vm.user?.usuario ?: "No disponible", Icons.Default.AccountBox)
                    InfoRow("Identificación", vm.user?.identificacion ?: "---", Icons.Default.Info)
                    InfoRow("Nombre Completo", vm.user?.nombre ?: "---", Icons.Default.Face)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons
            MenuButton(
                text = "Ver Tablas Sincronizadas",
                icon = Icons.AutoMirrored.Filled.List,
                color = InterOrange,
                onClick = onGoToTables
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "Consultar Localidades",
                icon = Icons.Default.LocationOn,
                color = InterBlue,
                onClick = onGoToLocations
            )

            Spacer(modifier = Modifier.weight(1f))

            // Status Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (vm.statusMessage.contains("Error")) Icons.Default.Warning else Icons.Default.Info,
                        contentDescription = null,
                        tint = if (vm.statusMessage.contains("Error")) Color.Red else InterBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        vm.statusMessage,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Loading Overlay
        if (vm.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = InterOrange)
                        Spacer(Modifier.height(16.dp))
                        Text("Procesando...", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun MenuButton(text: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, null)
            Spacer(Modifier.width(12.dp))
            Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(label, fontWeight = FontWeight.Light, fontSize = 11.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color.DarkGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TablesScreen(vm: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Esquema de Tablas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = InterBlue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundGray)) {
            items(vm.tables) { table ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Menu, null, tint = InterOrange)
                        Spacer(Modifier.width(16.dp))
                        Text(table.nombreTabla, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(vm: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Localidades Recogidas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = InterOrange,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundGray)) {
            if (vm.locations.isEmpty() && !vm.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Place, null, Modifier.size(64.dp), Color.LightGray)
                    Text("No se encontraron localidades", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    items(vm.locations) { loc ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = InterOrange.copy(alpha = 0.1f),
                                        shape = CircleShape
                                    ) {
                                        Text(
                                            loc.abreviacionCiudad,
                                            modifier = Modifier.padding(8.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = InterOrange,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Text(loc.nombreCompleto, fontWeight = FontWeight.Medium)
                                }
                                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.LightGray)
                            }
                        }
                    }
                }
            }

            if (vm.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = InterOrange
                )
            }
        }
    }
}