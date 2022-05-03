package com.wonjoon.compose.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonjoon.domain.TodoItem
import com.wonjoon.compose.viewmodel.MainViewModel
import com.wonjoon.compose.ui.theme.ComposeExampleTheme
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    TodoList(viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getList()
    }
}

@Composable
fun TodoList(viewModel: MainViewModel = getViewModel()){

    val listState by viewModel.todoList.observeAsState(listOf())
    val isEmpty by viewModel.errorEvent.observeAsState(false)

    val context = LocalContext.current

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { context.startActivity(Intent(context, WriteTodoActivity::class.java)) }) {
            Icon(Icons.Filled.Add, "")
        }
    }, topBar = {
        TopAppBar(title = { Text(text = "TodoList") }, actions = {
            IconButton(onClick = {
                viewModel.deleteAll()
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "")
            }
        })
    }) {

        if(!isEmpty) {
            LazyColumn{
                items(listState.size){
                    TodoItem(listState[it], viewModel)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Text(
                    text = "Todo is Empty",
                    modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp)
                )
            }
        }
    }
}

@Composable
fun TodoItem(todoItem: TodoItem, viewModel: MainViewModel = getViewModel()){
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(Color.White)
        .clickable(enabled = true, onClick = {
            val intent = Intent(context, EditTodoActivity::class.java)
            intent.putExtra("id", todoItem.id)
            context.startActivity(intent)
        })) {
        Column(
            Modifier
                .fillMaxHeight()
                .widthIn(50.dp, 200.dp)
                .align(Alignment.CenterStart)) {
            Text(text = todoItem.title, modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 5.dp), maxLines = 1)
            Text(text = todoItem.text, modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp), maxLines = 1)
        }

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            Text(text = convertTime(todoItem.createdAt), textAlign = TextAlign.End, modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp))

            Checkbox(checked = todoItem.done, onCheckedChange = {
                viewModel.updateCheck(TodoItem(todoItem.id, todoItem.title, todoItem.text, todoItem.createdAt, it))
            })
        }
    }
}

fun convertTime(time : Long):String{
    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일\nHH시 mm분 ss초")
    return dateFormat.format(Date(time))
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview(){
    ComposeExampleTheme(darkTheme = false) {
        TodoItem(TodoItem(1, "안녕하이하아하이하이하이하이하이", "하이", System.currentTimeMillis(), false))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExampleTheme(darkTheme = true) {
        TodoList()
    }
}