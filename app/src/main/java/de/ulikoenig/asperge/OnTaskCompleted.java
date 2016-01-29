package de.ulikoenig.asperge;

/**
 * Created by ukoenig on 09.12.15.
 */
public interface OnTaskCompleted{
    void onTaskCompleted(Essen[] essen);

    void onTaskCompleted(String s);
}