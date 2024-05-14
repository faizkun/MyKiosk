package com.faizdev.mykiosk.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faizdev.mykiosk.R
import com.faizdev.mykiosk.screen.destinations.RegisterScreenDestination
import com.faizdev.mykiosk.ui.theme.poppins

@Composable
fun Login() {

    var enableEmail by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var enablePassword by remember {
        mutableStateOf(TextFieldValue(""))
    }


    Scaffold(
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xFF86B6F6))
        ) {

            Image(
                modifier = Modifier
                    .size(326.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 60.dp),
                painter = painterResource(id = R.drawable.login),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.size(14.dp))
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),

                ) {

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 23.dp),
                    text = "Login",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 29.sp


                )

                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    value = enableEmail,
                    label = {
                        Text(
                            text = "Email",
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

                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = enablePassword,
                    label = {
                        Text(
                            text = "Password",

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
                       // Nantinya akan ditambahkan untuk perpindahan
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
                        text = "Login",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold


                    )

                }

                Divider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp),
                    color = Color.Gray
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
                                text = "Pengguna baru?",
                                color = Color.Gray
                            )

                            Text(
                                modifier = Modifier
                                    .clickable {
                                       //Nantinya ditambahkan atribut untuk perpindahan
                                    },
                                text = " Sign Up",
                                color = Color(0xFF0065EC),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp

                            )
                        }

                    }
                }


            }

        }
    }

}