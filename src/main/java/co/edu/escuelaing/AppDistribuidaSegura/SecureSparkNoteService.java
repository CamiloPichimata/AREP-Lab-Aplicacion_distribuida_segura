package co.edu.escuelaing.AppDistribuidaSegura;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static spark.Spark.*;
import static spark.Spark.get;

public class SecureSparkNoteService {

    public static void main(String[] args) {
        AtomicReference<ArrayList<Note>> notes = new AtomicReference<>(new ArrayList<>());

        System.out.println("Inicializando Servidor...");
        // Estabaleciendo una conexión segura
        // secure(getKeystore(), "123456", null, null);
        port(getPort());

        get("/Hello", (req, res)-> "Hello from a Secure Spark Note Service");

        get("/getNotes", (req, res) -> {
            //JSONObject jsonObject = new JSONObject(notes);
            System.out.println("NOTE SERVICE\n    /getNotes: " + new JSONObject(notes));
            return new JSONObject(notes);
        });

        put("/putNotes", (req, res) -> {
            ArrayList<Note> tempNotes = new ArrayList<>();
            String reqBody = req.body();
            JSONArray arrayNotes = new JSONArray(reqBody);
            for (int i=0; i<arrayNotes.length(); i++) {
                tempNotes.add(new Note(arrayNotes.getJSONObject(i).getString("title"),
                        arrayNotes.getJSONObject(i).getString("content")));
            }
            notes.set(tempNotes);
            res.status(200);
            return null;
        });
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
    }

    static String getKeystore() {
        if (System.getenv("KEYSTORE") != null) {
            return System.getenv("keystore");
        }
        return "";
    }
}
