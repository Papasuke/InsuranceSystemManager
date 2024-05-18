package org.system.utils;

import javafx.scene.effect.*;
import javafx.stage.Stage;

public class UIEffects {
    public static void applyBlurEffect(Stage stage) {
        // Create a darkening effect (black overlay)
        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-0.5); // Adjust for desired darkness

        // Create the Gaussian blur effect
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(15); // Adjust for desired blur intensity

        // Combine the darkening and blur effects
        Effect combinedEffect = new Blend(
                BlendMode.SRC_OVER,
                darken,
                blur
        );

        stage.getScene().getRoot().setEffect(combinedEffect);
    }

    public static void removeBlurEffect(Stage stage) {
        stage.getScene().getRoot().setEffect(null);
    }
}
