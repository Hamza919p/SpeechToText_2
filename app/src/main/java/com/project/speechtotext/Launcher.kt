package com.project.speechtotext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import com.project.speechtotext.ui.activities.*
import java.util.*


class Launcher {
    companion object {
        fun startFirstOnBoardingScreen(activity: Activity) {
            activity.startActivity(Intent(activity, FirstOnBoardingScreenActivity::class.java))
        }

        fun startSecondOnBoardingScreen(activity: Activity) {
            activity.startActivity(Intent(activity, SecondOnBoardingScreenActivity::class.java))
        }

        fun startMainActivity(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }

        fun startEditNoteActivity(activity: Activity, note: String) {
            activity.startActivity(Intent(activity, EditNoteActivity::class.java).putExtra("note", note))
        }

        fun startFeedbackActivity(activity: Activity) {
            activity.startActivity(Intent(activity, FeedbackActivity::class.java))
        }

        fun startSpeechRecognizer() : Intent {

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
            intent.putExtra("android.speech.extra.GET_AUDIO", true);
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text")

            return intent
        }

        fun shareText(text: String, activity: Activity) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            activity.startActivity(shareIntent)
        }

        fun navigateToPlayStore(activity: Activity) {
            val appPackageName = BuildConfig.APPLICATION_ID
            try {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=galaxy+smart+app")
                    )
                )
            } catch (exception: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=galaxy+smart+app")
                    )
                )
            }
            activity.finish()
        }

    }
}