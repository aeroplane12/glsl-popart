package com.glsl.popart.utils;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.InputStream;

public class TextureUtils {

    // Methode zum Laden einer Textur
    public static Texture loadTexture(String path) throws Exception {
        // Überprüfen, ob der Pfad korrekt ist und die Textur geladen werden kann
        InputStream stream = TextureUtils.class.getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Bild nicht gefunden: " + path);
            return null;
        }
        return TextureIO.newTexture(stream, false, "jpg"); // Bild im JPG-Format laden
    }

}
