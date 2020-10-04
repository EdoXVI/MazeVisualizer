package org.example.Model.MazeGenerator;

import org.example.Model.Direction;
import org.example.Model.Maze;
import org.example.Model.Model;
import org.example.Model.Position;

import java.util.Random;
import java.util.Stack;

public class IterativeBacktracking implements MazeGenerator {
    @Override
    public void generateMaze(Model model, Maze maze){
        Stack<Position> stackPos = new Stack<>();
        Direction nextDir;
        Position p;
        maze.setStart();
        stackPos.push(maze.getCurPos());

        while(!stackPos.empty()){
            if(model.getDelay() > 0){
                try{
                    Thread.sleep(model.getDelay());
                } catch(IllegalArgumentException | InterruptedException e){
                    System.err.println("Something went wrong in the sleep");
                }
            }

            nextDir = getNextDir(maze);
            if(nextDir != null){
                stackPos.push(maze.getCurPos());
                maze.breakWalls(nextDir);
                p = maze.getCurPos().add(nextDir.getVector());
                maze.setCellAsVisited(p);
            } else {
                p = stackPos.pop();
            }
            
            maze.setCurPos(p);
        }

        maze.print();
    }

    private Direction getNextDir(Maze maze) {
        Position curPos, nextPos;
        Direction nextDir;
        Random rand = new Random();

        curPos = maze.getCurPos();
        nextDir = Direction.getDir(rand.nextInt(4));

        for(int start=0; start<4; start++){
            curPos = maze.getCurPos();
            nextPos = curPos.add(nextDir.getVector());

            if(maze.isAcceptablePos(nextPos)){
                if(!maze.cellWasVisited(nextPos)){
                    return nextDir;
                }

            }

            nextDir = Direction.nextDir(nextDir);
        }

        return null;
    }
}
