package com.wonjoon.compose.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.wonjoon.compose.viewmodel.EditTodoVIewModel
import com.wonjoon.compose.ui.theme.ComposeExampleTheme
import com.wonjoon.compose.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTodoActivity: ComponentActivity() {

    private val viewModel : EditTodoVIewModel by viewModel()
    val id : Int by lazy {
        intent.getIntExtra("id", -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EditTodo(
                        viewModel = viewModel,
                        id = id
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(this@EditTodoActivity.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is Event.FinishEvent -> {
                            finish()
                        }
                        is Event.ErrorEvent -> {
                            finish()
                        }
                        is Event.BlankEvent -> {
                            Toast.makeText(this@EditTodoActivity, "빈칸이 있습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        viewModel.getTodo(id)
    }
}

@Composable
fun EditTodo(viewModel : EditTodoVIewModel = getViewModel(), id : Int){
    val title = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }

    Log.d("ID", id.toString())

    val todo = viewModel.todoItem.observeAsState(null)

    title.value = todo.value?.title?:""
    text.value = todo.value?.text?:""


    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "TodoList") }, actions = {
            IconButton(onClick = {
                viewModel.updateTodo(com.mindlogic.domain.TodoItem(id, title.value, text.value, todo.value?.createdAt?:System.currentTimeMillis(), todo.value?.done?:false))
            }) {
                Icon(Icons.Filled.Edit, contentDescription = "")
            }

            IconButton(onClick = {
                viewModel.deleteTodo(id)
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "")
            }
        }, navigationIcon = {
            IconButton(onClick = {
                viewModel.finish()
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
                .fillMaxSize(), textStyle = TextStyle(color = Color.Black, fontSize = 10.sp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditPreview(){
    ComposeExampleTheme {
        EditTodo(getViewModel(), 1)
    }
}