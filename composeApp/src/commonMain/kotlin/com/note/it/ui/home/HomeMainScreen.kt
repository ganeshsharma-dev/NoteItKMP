package com.note.it.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.note.it.app.ViewModels.HomeViewModel
import com.note.it.app.ViewModels.ProfileViewModel
import com.note.it.app.network.ChangePasswordRequest
import com.note.it.app.network.DeleteAccountRequest
import com.note.it.app.network.HelpRequest
import com.note.it.app.network.Note
import com.note.it.app.network.NotesRequest
import com.note.it.app.network.ShareNoteRequest
import com.note.it.app.network.SharedNoteData
import com.note.it.app.network.UpdateProfileRequest
import com.note.it.core.presentation.asPlainString
import com.note.it.platform.HandleBackPress
import com.note.it.platform.exitApp
import com.note.it.platform.provideSettings
import com.note.it.platform.shareNote
import com.note.it.ui.dialogs.NoteDialog
import com.note.it.ui.home.favorite.FavoriteScreen
import com.note.it.ui.home.notes.NotesScreen
import com.note.it.ui.home.notes.ShareWithinAppScreen
import com.note.it.ui.home.profile.AppInfoScreen
import com.note.it.ui.home.profile.ChangePasswordScreen
import com.note.it.ui.home.profile.DeleteAccountScreen
import com.note.it.ui.home.profile.HelpScreen
import com.note.it.ui.home.profile.ProfileScreen
import com.note.it.ui.home.profile.SecuritySettingsScreen
import com.note.it.ui.home.profile.UpdateProfileScreen
import com.note.it.ui.home.shared.SharedNoteDetailSheet
import com.note.it.ui.home.shared.SharedScreen
import com.note.it.ui.utils.CommonDialog
import com.note.it.ui.utils.NoteAction
import com.note.it.ui.utils.NoteDialogAction
import com.note.it.ui.utils.ProfileAction
import com.note.it.ui.utils.UserSession
import org.koin.compose.koinInject
import kotlin.math.log


sealed class HomeScreen {
    object Main : HomeScreen()
    object ChangePassword : HomeScreen()
    object UpdateProfile : HomeScreen()
    object SecuritySetting : HomeScreen()
    object DeleteAccount : HomeScreen()
    object AppInfo : HomeScreen()
    object Help : HomeScreen()

    //  data class ShareWithinApp(val note: Note) : HomeScreen()

    object ShareWithinApp : HomeScreen()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainScreen(onLogout: () -> Unit = {}) {

    var selectedTab by remember { mutableStateOf<HomeTab>(HomeTab.Notes) }
    var dialogMode by remember { mutableStateOf<NoteDialogAction?>(null) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }


    val homeViewModel = koinInject<HomeViewModel>()
    val profileViewModel = koinInject<ProfileViewModel>()

    var shareNoteDialog by remember { mutableStateOf<Note?>(null) }


    var currentScreen by remember { mutableStateOf<HomeScreen>(HomeScreen.Main) }
    var showExitDialog by remember { mutableStateOf(false) }

    var errorDialogMessage by remember { mutableStateOf<String?>(null) }

    var oldPwdError by remember { mutableStateOf<String?>(null) }
    var newPwdError by remember { mutableStateOf<String?>(null) }
    var confirmPwdError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val session = remember { UserSession(provideSettings()) }

    var showPwdDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    var selectedSharedNote by remember { mutableStateOf<SharedNoteData?>(null) }





    HandleBackPress(onBack = {
        if (currentScreen != HomeScreen.Main) {
            currentScreen = HomeScreen.Main
        } else {
            showExitDialog = true
        }
    })


    when (currentScreen) {

        is HomeScreen.Main -> {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(selectedTab.title) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant, // Light grey/tinted color
                            titleContentColor = MaterialTheme.colorScheme.primary,     // Primary color for text
                        )
                    )
                },
                bottomBar = {
                    BottomNavigation(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )
                },
                floatingActionButton = {
                    if (selectedTab is HomeTab.Notes) {
                        FloatingActionButton(onClick = {

                            dialogMode = NoteDialogAction.ADD
                            selectedNote = null
                            //  onAddNote
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Note"
                            )
                        }
                    }
                }
            ) { padding ->

                Box(Modifier.padding(padding)) {


                    val userId = session.getUser()?.userId

                    LaunchedEffect(userId) {
                        userId?.let {
                            homeViewModel.fetchNotes(it) { error ->
                                errorDialogMessage = error?.asPlainString()
                            }
                        }
                    }


                    LaunchedEffect(userId) {
                        userId?.let {
                            homeViewModel.fetchSharedNotes(it) { error ->
                                errorDialogMessage = error?.asPlainString()
                            }
                        }
                    }

                    when (selectedTab) {

                        is HomeTab.Notes -> NotesScreen(
                            notes = homeViewModel.notes,
                            onAction = { note, action ->
                                selectedNote = note

                                when (action) {

                                    NoteAction.VIEW ->
                                        dialogMode = NoteDialogAction.VIEW

                                    NoteAction.EDIT ->
                                        dialogMode = NoteDialogAction.EDIT

                                    NoteAction.DELETE ->
                                        dialogMode = NoteDialogAction.DELETE

                                    NoteAction.SHARE -> {

                                        shareNoteDialog = note
                                        //shareNote(note)
                                    }

                                    NoteAction.TOGGLE_FAVORITE ->
                                        homeViewModel.toggleFavorite(
                                            session.getUser()?.userId!!, note.id, onError =
                                                {
                                                    errorDialogMessage = it

                                                })
                                }
                            }
                        )

                        is HomeTab.Favorite -> FavoriteScreen(
                            notes = homeViewModel.notes,
                            onNoteClick = {
                                selectedNote = it
                                dialogMode = NoteDialogAction.VIEW

                            },
                            onToggleFavorite = {
                                homeViewModel.toggleFavorite(
                                    session.getUser()?.userId!!, it.id, onError =
                                        {
                                            errorDialogMessage = it
                                        })


                            })


                        is HomeTab.Shared -> SharedScreen(
                            homeViewModel.sharedNotes,
                            onNoteClick = { note ->
                                selectedSharedNote = note   // ✅ only update state
                            },
                            session.getUser()?.userId!!
                        )

                        is HomeTab.Profile -> ProfileScreen(
                            userName = session.getUser()?.name!!,
                            userEmail = session.getUser()?.email!!,
                            userMobile = session.getUser()?.mobile!!,
                            totalNotes = homeViewModel.notes.size,
                            favoriteNotes = homeViewModel.getFavoriteNotes().size,
                            sharedNotes = homeViewModel.sharedNotes.size,
                            onAction = {
                                when (it) {
                                    ProfileAction.CHANGE_PASSWORD ->
                                        currentScreen = HomeScreen.ChangePassword

                                    ProfileAction.UPDATE_PROFILE ->
                                        currentScreen = HomeScreen.UpdateProfile

                                    ProfileAction.SECURITY_SETTINGS ->
                                        currentScreen = HomeScreen.SecuritySetting

                                    ProfileAction.DELETE_ACCOUNT ->
                                        currentScreen = HomeScreen.DeleteAccount


                                    ProfileAction.INFO ->
                                        currentScreen = HomeScreen.AppInfo

                                    ProfileAction.HELP ->
                                        currentScreen = HomeScreen.Help

                                    ProfileAction.LOGOUT ->
                                        onLogout()
                                }
                            }
                        )
                    }
                }
            }

        }

        is HomeScreen.ChangePassword -> {

            LaunchedEffect(Unit) {
                oldPwdError = null
                newPwdError = null
                confirmPwdError = null
                isLoading = false
            }

            ChangePasswordScreen(
                isLoading = isLoading,
                errorCurrent = oldPwdError,
                errorNew = newPwdError,
                errorConfirm = confirmPwdError,
                onBack = { currentScreen = HomeScreen.Main },
                onChangePassword = { current, new, confirm ->
                    oldPwdError = null
                    newPwdError = null
                    confirmPwdError = null
                    isLoading = true

                    profileViewModel.changePassword(
                        ChangePasswordRequest(session.getUser()?.userId!!, current, new, confirm),
                        onSuccess = {
                            isLoading = false
                            dialogMessage = it.message ?: "Password changed successfully"
                            showPwdDialog = true

                        },
                        onError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"
                            // errorDialogMessage = msg
                            errorDialogMessage = msg
                            when {
                                msg.contains("Current Password", true) -> oldPwdError = msg
                                msg.contains("New Password", true) -> newPwdError = msg
                                msg.contains("Confirm Password", true) -> confirmPwdError = msg
                                msg.contains("match", true) -> confirmPwdError = msg
                            }

                        }, validationError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"

                            when {
                                msg.contains("Current Password", true) -> oldPwdError = msg
                                msg.contains("New Password", true) -> newPwdError = msg
                                msg.contains("Confirm Password", true) -> confirmPwdError = msg
                                msg.contains("match", true) -> confirmPwdError = msg
                                else -> {
                                    errorDialogMessage = msg
                                }
                            }
                        }
                    )

                }

            )
        }


        is HomeScreen.UpdateProfile ->
            UpdateProfileScreen(
                onBack = { currentScreen = HomeScreen.Main },
                onUpdateProfile = { name, email, mobile ->
                    profileViewModel.updateProfile(
                        request = UpdateProfileRequest(
                            id = session.getUser()?.userId!!,
                            name = name,
                            email = email,
                            mobile = mobile
                        ),
                        onSuccess = {
                            isLoading = false
                            dialogMessage = it.message ?: "Profile Updated successfully"
                            showPwdDialog = true
                            session.saveUser(it.data)

                        },
                        onError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"
                            errorDialogMessage = msg
                        },
                        validationError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"

                            errorDialogMessage = msg


                        },


                        )

                },
                session.getUser()?.name!!,
                session.getUser()?.email!!,
                session.getUser()?.mobile!!
            )

        is HomeScreen.SecuritySetting ->
            SecuritySettingsScreen(
                isAuthEnabled = false,
                onToggleChanged = {
                    // Call your ViewModel API here
                },
                onBack = { currentScreen = HomeScreen.Main }
            )


        is HomeScreen.DeleteAccount -> {
            DeleteAccountScreen(
                onBack = { currentScreen = HomeScreen.Main },
                onDeleteAccount = { password ->

                    isLoading = true
                    profileViewModel.deleteAccount(
                        request = DeleteAccountRequest(session.getUser()?.userId!!, password),
                        onSuccess = {
                            session.clearSession()
                            currentScreen = HomeScreen.Main

                        },
                        onError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"

                            errorDialogMessage = msg
                        },
                        validationError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"

                            errorDialogMessage = msg
                        })
                })
        }

        is HomeScreen.AppInfo ->
            AppInfoScreen(
                onBack = { currentScreen = HomeScreen.Main }
            )

        is HomeScreen.Help ->
            HelpScreen(
                onBack = { currentScreen = HomeScreen.Main },
                onSubmitSuggestion = { suggestion ->
                    isLoading = true
                    profileViewModel.sendHelp(
                        request = HelpRequest(session.getUser()?.userId!!, suggestion),
                        onSuccess = {
                            dialogMessage = it.message ?: "Suggestions send successfully"
                            showPwdDialog = true

                        },
                        onError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"

                            errorDialogMessage = msg
                        },
                        validationError = {
                            isLoading = false
                            val msg = it?.asPlainString() ?: "Unknown error"

                            errorDialogMessage = msg
                        })

                }
            )

        is HomeScreen.ShareWithinApp -> {

            //val screen = currentScreen as HomeScreen.ShareWithinApp


            ShareWithinAppScreen(
                recentContacts = homeViewModel.recentContacts(),
                onBack = { currentScreen = HomeScreen.Main },
                onShare = true,
                note = selectedNote!!,
                sendToUser = {
                    homeViewModel.shareNoteToUser(it, onSuccess = {
                        currentScreen = HomeScreen.Main

                    }, onError = {
                        errorDialogMessage = it

                    },)
                }

            )

        }
    }






    dialogMode?.let { mode ->
        NoteDialog(
            mode = mode,
            note = selectedNote,
            onDismiss = { dialogMode = null },
            onSave = { updatedNote ->
                if (mode == NoteDialogAction.ADD) {
                    homeViewModel.addNote(
                        NotesRequest(
                            session.getUser()?.userId!!,
                            updatedNote.title,
                            updatedNote.content,
                            updatedNote.isFavorite
                        ), onError = {
                            errorDialogMessage = it
                        }
                    )
                } else if (mode == NoteDialogAction.EDIT)
                    homeViewModel.updateNote(
                        updatedNote.id, NotesRequest(
                            session.getUser()?.userId!!,
                            updatedNote.title,
                            updatedNote.content,
                            updatedNote.isFavorite
                        ), onError = {
                            errorDialogMessage = it
                        })
                else if (mode == NoteDialogAction.DELETE) {

                } else {

                }

            },
            onDelete = { note ->
                homeViewModel.deleteNote(session.getUser()?.userId!!, note.id, onError = {
                    errorDialogMessage = it
                })
            }
        )
    }

    shareNoteDialog?.let { note ->

        AlertDialog(
            onDismissRequest = { shareNoteDialog = null },
            title = {
                Text(
                    text = "Share Note",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        "Choose how you want to share this note.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Share within app
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                shareNoteDialog = null
                                currentScreen = HomeScreen.ShareWithinApp
                            }
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Share within app"
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text("Share within App")
                    }

                    Divider()

                    // Share external
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                shareNoteDialog = null
                                shareNote(note)
                            }
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowOutward,
                            contentDescription = "Share externally"
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text("Other Apps")
                    }
                }
            },
            confirmButton = {}, // remove default buttons
            dismissButton = {}
        )
    }

    if (showExitDialog) {
        CommonDialog(
            title = "Exit App",
            message = "Are you sure you want to exit the app?",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                showExitDialog = false
                exitApp()
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }
    errorDialogMessage?.let { msg ->
        CommonDialog(
            title = "Error",
            message = msg,
            confirmText = "OK",
            onConfirm = {
                errorDialogMessage = null
            }
        )
    }


    if (showPwdDialog) {
        CommonDialog(
            title = "Profile",
            message = dialogMessage,
            confirmText = "OK",
            onConfirm = {
                showPwdDialog = false
                currentScreen = HomeScreen.Main
            }
        )
    }


    selectedSharedNote?.let { note ->
        SharedNoteDetailSheet(
            note = note,
            onDismiss = { selectedSharedNote = null },
            onRevokeClick = { sharedNote ->
                sharedNote.shareId?.let { id ->
                    val userId = session.getUser()?.userId ?: return@let
                    homeViewModel.revokeShare(
                        shareId = id,
                        ownerId = userId,
                        onError = { errorDialogMessage = it }
                    )
                }
                selectedSharedNote = null
            }
        )
    }

}




