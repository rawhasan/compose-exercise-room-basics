package com.example.roombasics

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roombasics.data.Word

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wordViewModel by viewModels<WordViewModel> {
            WordViewModelFactory((application as WordsApplication).repository)
        }

        setContent {
            WordBookApp(
                wordViewModel = wordViewModel,
                onAddWord = { wordViewModel.addWord(it) },
                onUpdateWord = { wordViewModel.updateWord(it) },
                onDeleteWord = { wordViewModel.deleteWord(it) },
                onClearWords = { wordViewModel.clearWords() }
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun WordBookApp(
    wordViewModel: WordViewModel,
    onAddWord: (Word) -> Unit,
    onUpdateWord: (Word) -> Unit,
    onDeleteWord: (Word) -> Unit,
    onClearWords: () -> Unit
) {
    val words: List<Word> by wordViewModel.allWords.observeAsState(listOf())

    var newWord by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

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
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            // Add Button
            Button(
                onClick = {
                    if (newWord.trim().isNotEmpty()) {
                        onAddWord(Word(newWord.trim()))
                        newWord = ""
                        keyboardController?.hide()
                        Toast.makeText(context, "Word added", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .height(56.dp)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text("Add", modifier = Modifier.padding(start = 8.dp))
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(words) { word ->
                WordItemLayout(word, onSaveUpdatedWord = { onUpdateWord(it) })
            }
        }

        // Clear Button
        Button(
            onClick = { onClearWords() },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            Text("Clear Words", modifier = Modifier.padding(start = 16.dp))
        }
    }
}

@Composable
fun WordItemLayout(word: Word, onSaveUpdatedWord: (Word) -> Unit) {
    var showEditForm by remember { mutableStateOf(false) }
    var editedWord by remember { mutableStateOf(word.word) }
    val context = LocalContext.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant)
                .padding(vertical = 20.dp, horizontal = 24.dp)
                .clickable {
                    // onWordClicked(word)
                    showEditForm = !showEditForm
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(R.drawable.ic_star), contentDescription = null)
            Text(
                text = word.word,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // word edit form
        if (showEditForm) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                TextField(
                    value = editedWord,
                    onValueChange = { editedWord = it },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White) // TextField Background Color
                )
                Button(
                    onClick = {
                        val updatedWord: Word = word

                        if (updatedWord.word != editedWord.trim()) {
                            updatedWord.word = editedWord.trim()
                            onSaveUpdatedWord(updatedWord)
                            Toast.makeText(context, "Word updated", Toast.LENGTH_SHORT).show()
                            editedWord = word.word
                        }

                        showEditForm = false
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Update Word")
                }
            }
        }
    }
}

