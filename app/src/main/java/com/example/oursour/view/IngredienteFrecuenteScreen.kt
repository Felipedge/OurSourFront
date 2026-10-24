package com.example.oursour.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.oursour.viewmodel.IngredienteFrecuenteViewModel


@Composable
fun IngredienteFrecuenteScreen(
    onVerRecetasClick: () -> Unit,
    onVolver: () -> Unit,
    ingredienteFrecuenteViewModel: IngredienteFrecuenteViewModel = viewModel()
) {
    Column(
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Center,
modifier = Modifier
.fillMaxWidth()
.padding(16.dp)
    ) {
        // titulo
        Text(
            text = ingredienteFrecuenteViewModel.Titulo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp, bottom = 12.dp)

        )
        // estos son los ingredientes mas usados
        Text(text = ingredienteFrecuenteViewModel.ingrediente1)
        Text(text = ingredienteFrecuenteViewModel.ingrediente2)
        Text(text = ingredienteFrecuenteViewModel.ingrediente3)

        // ahora vamos con el botton
        Button(
            onClick = onVerRecetasClick,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Ver recetas")
        }
        //bottom para volver
        Button(
            onClick = onVolver,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Volver")
        }
    }

}


