package com.wonjoon.compose.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonjoon.compose.viewmodel.WriteTodoViewModel
import com.wonjoon.compose.ui.theme.ComposeExampleTheme
import org.koin.androidx.compose.getViewModel

class WriteTodoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WriteTodo()
                }
            }
        }
    }
}

@Composable
fun WriteTodo(viewModel : WriteTodoViewModel = getViewModel()){
    val title = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    val finish = viewModel.errorEvent.observeAsState(false)

    val context = LocalContext.current

    if(finish.value){
        if(context.findActivity() != null) {
            context.findActivity()!!.finish()
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "TodoList") }, actions = {
            IconButton(onClick = {
                if(title.value.isNotBlank() && text.value.isNotBlank()){
                    viewModel.writeTodo(title.value, text.value)
                } else {
                    Toast.makeText(context, "빈칸이 있습니다", Toast.LENGTH_SHORT).show()
                }
            }) {
                Icon(Icons.Filled.Edit, contentDescription = "")
            }
        }, navigationIcon = {
            IconButton(onClick = {
                if(context.findActivity() != null) {
                    context.findActivity()!!.finish()
                }
            }) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        })
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(value = title.value, onValueChange = {
                title.value = it
            }, modifier = Modifier
                .fillMaxWidth()
                .heightIn(20.dp, 100.dp)
                .padding(5.dp), textStyle = TextStyle(color = Color.Black, fontSize = 14.sp)
            )

            OutlinedTextField(value = text.value, onValueChange = {
                text.value = it
            }, modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(), textStyle = TextStyle(color = Color.Black, fontSize = 10.sp))
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true)
@Composable
fun WriteTodoPreview() {
    ComposeExampleTheme {
        OutlinedTextField(value = "title.value", onValueChange = {

        }, modifier = Modifier
            .fillMaxWidth()
            .heightIn(20.dp, 100.dp)
            .padding(10.dp), textStyle = TextStyle(color = Color.Black, fontSize = 14.sp)
        )
    }
}