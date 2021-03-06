package org.example.Model.MazeGenerator;

import org.example.Model.Direction;
import org.example.Model.Maze;
import org.example.Model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Algorithm's high-level description (from Wikipedia: https://en.wikipedia.org/wiki/Maze_generation_algorithm)
 *
 * Randomized Prim's Algorithm:
 *
 * - Start with a grid full of walls
 * - Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list
 * - While there are walls in the list:
 *       - Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:
 *           - Make the wall a passage and mark the unvisited cell as part of the maze
 *           - Add the neighboring walls of the cell to the wall list
 *       - Remove the wall from the list
 */
public class RandomizedPrim implements MazeGenerator{

    @Override
    public void generateMaze(Maze maze) {
        List<WallOpt> wallList = new ArrayList<>();
        Random rand = new Random();
        Position nextPos, curPos = new Position(rand.nextInt(maze.getDimension()), rand.nextInt(maze.getDimension()));
        WallOpt pickedWall;
        maze.setCellAsVisited(curPos, false);
        addWalls(maze, curPos, wallList);

        while(!wallList.isEmpty()){
            pickedWall = wallList.get(rand.nextInt(wallList.size()));
            curPos = pickedWall.getCellPos();
            nextPos = pickedWall.getAdjacentCellPos();

            if(maze.isAcceptablePos(nextPos)){
                if(maze.cellWasVisited(curPos) && !maze.cellWasVisited(nextPos)){
                    maze.breakWalls(curPos, pickedWall.getWallDir());
                    maze.setCellAsVisited(nextPos, false);
                    addWalls(maze, nextPos, wallList);
                }
            }

            wallList.remove(pickedWall);

        }

        maze.resetMazeVisited();

    }

    @Override
    public boolean wallSetup() {
        return true;
    }

    @Override
    public String toString() {
        return "Randomized Prim";
    }

    public void addWalls(Maze m, Position p, List<WallOpt> list){
        for(int i=0; i<4; i++){
            if(!m.canMoveTo(p, Direction.getDir(i)))
                list.add(new WallOpt(p, Direction.getDir(i)));
        }

    }

}
