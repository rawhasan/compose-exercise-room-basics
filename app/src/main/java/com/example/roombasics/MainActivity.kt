package com.example.roombasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roombasics.data.Word

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wordViewModel by viewModels<WordViewModel> {
            WordViewModelFactory((this.applicationContext as WordsApplication).repository)
        }

        setContent {
            WordBookApp(wordViewModel)
        }
    }
}

@Composable
fun WordBookApp(wordViewModel: WordViewModel) {
    val words: List<Word> by wordViewModel.allWords.observeAsState(listOf())

    var newWord by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = newWord,
                onValueChange = { newWord = it },
                label = { Text("New Word") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newWord.trim().isNotEmpty())
                        wordViewModel.insert(Word(newWord))
                },
                modifier = Modifier
                    .height(56.dp)
                    .padding(start = 8.dp)
            ) {
                Text("Add")
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(words) { word ->
                WordItemLayout(word)
            }
        }

        Button(onClick = { /*TODO*/ }) {
            Text(
                "Clear Words",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WordItemLayout(word: Word) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primaryVariant)
            .padding(vertical = 20.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(R.drawable.ic_star), contentDescription = null)
        Text(
            text = word.word,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //WordBookApp(wordViewModel)
}

