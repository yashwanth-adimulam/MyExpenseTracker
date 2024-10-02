package com.example.myexpensetracker.feature.transactionlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myexpensetracker.R
import com.example.myexpensetracker.feature.add_expense.ExpenseDropDown
import com.example.myexpensetracker.feature.home.TransactionItem
import com.example.myexpensetracker.utils.Utils
import com.example.myexpensetracker.feature.home.HomeViewModel
import com.example.myexpensetracker.ui.theme.LightBlue
import com.example.myexpensetracker.ui.theme.LightRed
import com.example.myexpensetracker.ui.theme.Teal80
import com.example.myexpensetracker.widget.ExpenseTextView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionListScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.expenses.collectAsState(initial = emptyList())
    var filterType by remember { mutableStateOf("All") }
    var dateRange by remember { mutableStateOf("All Time") }
    var menuExpanded by remember { mutableStateOf(false) }

    val filteredTransactions = when (filterType) {
        "Expense" -> state.value.filter { it.type == "Expense" }
        "Income" -> state.value.filter { it.type == "Income" }
        else -> state.value
    }

    val filteredByDateRange = filteredTransactions.filter { transaction ->
        true
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { navController.popBackStack() },
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Teal80)
                )

                ExpenseTextView(
                    text = "Transactions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { menuExpanded = !menuExpanded },
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    AnimatedVisibility(
                        visible = menuExpanded,
                        enter = slideInVertically(initialOffsetY = { -it / 2 }),
                        exit = slideOutVertically(targetOffsetY = { -it  }),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Column {
                            ExpenseDropDown(
                                listOfItems = listOf("All", "Expense", "Income"),
                                onItemSelected = { selected ->
                                    filterType = selected
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            ExpenseDropDown(
                                listOfItems = listOf( "Yesterday", "Today", "Last 30 Days", "Last 90 Days", "Last Year"),
                                onItemSelected = { selected ->
                                    dateRange = selected
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                }
                items(filteredByDateRange) { item ->
                    val icon = Utils.getItemIcon(item)
                    TransactionItem(
                        title = item.title,
                        amount = item.amount.toString(),
                        icon = icon!!,
                        date = item.date,
                        color = if (item.type == "Income") LightBlue else LightRed,
                        Modifier.animateItemPlacement(tween(100))
                    )
                }
            }
        }
    }
}
