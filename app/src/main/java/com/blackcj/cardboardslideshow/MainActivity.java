package com.blackcj.cardboardslideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.vrtoolkit.cardboard.CardboardActivity;

import org.rajawali3d.cardboard.RajawaliCardboardView;


public class MainActivity extends CardboardActivity implements View.OnTouchListener {

    private SlideShowRenderer renderer;
    private CardboardOverlayView overlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.common_ui);
        RajawaliCardboardView view = (RajawaliCardboardView)findViewById(R.id.cardboard_view);

        setCardboardView(view);

        renderer = new SlideShowRenderer(this);
        view.setRenderer(renderer);
        view.setSurfaceRenderer(renderer);

        // Used for debugging in the emulator and for devices that don't support
        // the Google Cardboard magnet.
        view.setOnTouchListener(this);

        overlayView = (CardboardOverlayView) findViewById(R.id.overlay);
        overlayView.show3DToast("Pull the magnet to advance slides.");
    }

    /**
     * Called when the Cardboard trigger is pulled.
     */
    @Override
    public void onCardboardTrigger() {
        //overlayView.show3DToast("New image.");
        System.gc(); // Simulator was crashing without this
        renderer.changeTexture();
    }

    /**
     * Called when the screen is tapped.
     *
     * @param v View
     * @param event MotionEvent
     * @return bool
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        onCardboardTrigger();
        return false;
    }
}
