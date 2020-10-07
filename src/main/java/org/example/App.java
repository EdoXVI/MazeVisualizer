package org.example;

import org.example.Model.MazeGenerator.*;
import org.example.Model.Model;
import org.example.View.View;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        View view = new View();
        Model model = new Model(view);

        model.createMaze(8, new RecursiveDivision());
        model.killAll();
    }
}
