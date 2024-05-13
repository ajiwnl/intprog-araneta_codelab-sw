package com.araneta.codelab_sw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mMessageEditText: EditText
    private lateinit var mReplyHeadTextView: TextView
    private lateinit var mReplyTextView: TextView

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mReplyHeadTextView!!.visibility == View.VISIBLE) {
            outState.putBoolean("reply_visible", true)
            outState.putString("reply_text", mReplyTextView!!.text.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Log the start of the onCreate() method.
        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")

        // Initialize all the view variables.
        mMessageEditText = findViewById<EditText>(R.id.editText_main)
        mReplyHeadTextView = findViewById<TextView>(R.id.text_header_reply)
        mReplyTextView = findViewById<TextView>(R.id.text_message_reply)

        // Restore the state.
        if (savedInstanceState != null) {
            val isVisible = savedInstanceState
                .getBoolean("reply_visible")
            if (isVisible) {
                mReplyHeadTextView.setVisibility(View.VISIBLE)
                mReplyTextView.setText(
                    savedInstanceState
                        .getString("reply_text")
                )
                mReplyTextView.setVisibility(View.VISIBLE)
            }
        }
    }

    fun launchSecondActivity(view: View?) {
        Log.d(LOG_TAG, "Button clicked!")
        val intent = Intent(this, SecondActivity::class.java)
        val message = mMessageEditText!!.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Test for the right intent reply.
        if (requestCode == TEXT_REQUEST) {
            // Test to make sure the intent reply result was good.
            if (resultCode == RESULT_OK) {
                val reply = data!!.getStringExtra(SecondActivity.EXTRA_REPLY)

                // Make the reply head visible.
                mReplyHeadTextView!!.visibility = View.VISIBLE

                // Set the reply and make it visible.
                mReplyTextView!!.text = reply
                mReplyTextView!!.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(LOG_TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }

    companion object {
        // Class name for Log tag
        private val LOG_TAG = MainActivity::class.java.simpleName

        // Unique tag required for the intent extra
        const val EXTRA_MESSAGE = "Message here"

        // Unique tag for the intent reply
        const val TEXT_REQUEST = 1
    }
}