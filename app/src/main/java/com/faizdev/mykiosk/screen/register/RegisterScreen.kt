package com.faizdev.mykiosk.screen.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.faizdev.mykiosk.data.kotpref.Kotpref
import com.faizdev.mykiosk.screen.destinations.DashboardScreenDestination
import com.faizdev.mykiosk.screen.destinations.LoginScreenDestination
import com.faizdev.mykiosk.ui.theme.poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {


    LaunchedEffect(true) {
        if (Kotpref.id.isNotEmpty()) {
            navigator.navigate(DashboardScreenDestination)
        }
    }

    val registerState = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(


    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xFF86B6F6))
        ) {

            var enableUsername by remember {
                mutableStateOf("")
            }
            var enableEmail by remember {
                mutableStateOf(TextFieldValue(""))
            }
            var enablePassword by remember {
                mutableStateOf(TextFieldValue(""))
            }



            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 55.dp),
                text = "Create Your Account",
                fontSize = 35.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold


            )


            Spacer(modifier = Modifier.size(35.dp))

            Card(
                modifier = Modifier
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),

                ) {
                Spacer(modifier = Modifier.size(35.dp))
                OutlinedTextField(
                    value = enableUsername,
                    label = {
                        Text(
                            text = "Username",
                            fontFamily = poppins,
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "")
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = {
                        enableUsername = it
                    },

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                )


                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    value = enableEmail,
                    label = {
                        Text(
                            text = "Email",
                            fontFamily = poppins,
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Mail, contentDescription = "")
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = {
                        enableEmail = it

                    },

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

                )


                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    value = enablePassword,
                    label = {
                        Text(
                            text = "Password",
                            fontFamily = poppins,
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(horizontal = 16.dp),
                    onValueChange = {
                        enablePassword = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                )

                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    onClick = {
                        viewModel.register(
                            enableUsername,
                            enableEmail.text,
                            enablePassword.text
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF86B6F6)
                    ),
                    shape = RoundedCornerShape(11.dp)

                ) {
                    Text(
                        text = "Sign Up",
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold


                    )

                }

                Divider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp),
                    color = Color.DarkGray
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 18.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row {
                            Text(
                                text = "Sudah memiliki akun?",
                                fontFamily = poppins,
                                color = Color.DarkGray
                            )

                            Text(
                                modifier = Modifier.clickable {
                                    navigator.navigate(LoginScreenDestination)
                                },
                                text = " Log In",
                                fontFamily = poppins,
                                color = Color(0xFF0065EC),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp


                            )
                        }

                    }
                }


            }
        }

        registerState.value.DisplayResult(
            onLoading = { /*TODO*/ },
            onSuccess = { navigator.navigate(DashboardScreenDestination) },
            onError = {
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        )


    }

}


