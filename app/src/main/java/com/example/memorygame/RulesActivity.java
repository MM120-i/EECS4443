package com.example.memorygame;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity displays the rules of the Memory Game and provides an option to read them aloud using Text-to-Speech.
 */
public class RulesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextToSpeech textToSpeech;
    private String rulesText;
    private boolean isTTSActive = false;

    /**
     * This method is called when the activity is first created. It initializes the layout, retrieves the rules text from resources,
     * sets up the Text-to-Speech engine, and sets a click listener on the "Read Rules" button to toggle Text-to-Speech functionality.
     *
     * @param savedInstanceState a Bundle containing the activity's previously saved state, or null if there is no saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        // Find the TextView for displaying rules
        TextView rulesTextView = findViewById(R.id.rulesTextView);

        // Retrieve rules text from resources
        rulesText = getString(R.string.rules_text);

        // Convert HTML string to Spanned object
        Spanned spannedText = Html.fromHtml(rulesText, Html.FROM_HTML_MODE_COMPACT);
        rulesTextView.setText(spannedText);

        // Initialize Text-to-Speech engine
        textToSpeech = new TextToSpeech(this, this);

        // Find the "Read Rules" button and set a click listener
        Button readRulesButton = findViewById(R.id.readRulesButton);
        readRulesButton.setOnClickListener(v -> toggleTextToSpeech(rulesText));
    }

    /**
     * Toggles the Text-to-Speech functionality. If Text-to-Speech is currently active, it stops the speech.
     * If Text-to-Speech is not active, it starts reading the provided text.
     *
     * @param text The text to be read out loud by the Text-to-Speech engine
     */
    private void toggleTextToSpeech(String text){

        if(isTTSActive){         // If Text-to-Speech is active, stop speech
            stopTextToSpeech();
            isTTSActive = false;
        }
        else{                   // If Text-to-Speech is not active, start reading the provided text
            startTextToSpeech(text);
            isTTSActive = true;
        }
    }

    /**
     * Called to signal the completion of Text-to-Speech engine initialization.
     *
     * @param status {@link TextToSpeech#SUCCESS} if the initialization is successful,
     *               {@link TextToSpeech#ERROR} otherwise.
     */
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            // Set language for text-to-speech
            int result = textToSpeech.setLanguage(Locale.CANADA);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Text to speech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Starts the text-to-speech engine to read out the provided text.
     * If the text-to-speech engine is initialized, the provided text is spoken out loud.
     * A toast message is displayed to indicate that the speech has started.
     *
     * @param text The text to be spoken out loud.
     */
    private void startTextToSpeech(String text) {

        // Check if TextToSpeech is initialized
        if (textToSpeech != null) {
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    // Display toast message indicating speech has started
                    runOnUiThread(() -> Toast.makeText(RulesActivity.this, "Text-to-speech started. Click 'Read Rules' again to stop.", Toast.LENGTH_LONG).show());
                }

                @Override
                public void onDone(String utteranceId) {/*Nothing*/}

                @Override
                public void onError(String utteranceId) {
                    // Display toast message in case of speech error
                    runOnUiThread(() -> Toast.makeText(RulesActivity.this, "Error occurred during text-to-speech.", Toast.LENGTH_SHORT).show());
                }
            });

            // Speak out the stripped text (without HTML tags)
            textToSpeech.speak(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString(), TextToSpeech.QUEUE_FLUSH, null, "rules");
        }
    }

    /**
     * Stops the text-to-speech engine if it is currently speaking.
     */
    private void stopTextToSpeech(){
        if(textToSpeech != null){
            textToSpeech.stop();
        }
    }

    /**
     * Called when the window has lost or gained focus.
     * This method is not relevant to the current implementation.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    /**
     * Called when the activity is about to be destroyed.
     * Shuts down the TextToSpeech engine when the activity is destroyed to release resources.
     * Stops any ongoing speech and releases the TextToSpeech engine.
     */
    @Override
    protected void onDestroy() {

        // Shutdown TextToSpeech when the activity is destroyed
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }
}
