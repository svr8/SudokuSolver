import java.io.*;
import java.util.*;

class Main
{
  static int[][] solution = new int[9][9];
  static boolean[][] freeSlot = new boolean[9][9];
  static boolean solutionFound = false;

  public static void main(String args[])throws IOException
  {
    IO io = new IO();

    String line;
    char c;

    for(int i=0;i<9;i++) {
      row[i] = new SudokuContainer();
      col[i] = new SudokuContainer();
      grid[i] = new SudokuContainer();
    }

    for(int i=0;i<9;i++) {
      line = io.next();
      System.out.println(">"+line);
      for(int j=0;j<9;j++) {


        c = line.charAt(j);
        if(c=='*') {
          freeSlot[i][j] = true;
          continue;
        }

        int index = getGridIndex(i,j);
   

        int val = c-48;
        row[i].addVal(val);
        col[j].addVal(val);
        grid[index].addVal(val);

        solution[i][j] = val;
      }
    }

    System.out.println("PUZZLE\n");
    displaySolution();

    solve(0,0);

    System.out.println("SOLUTION\n");
    displaySolution();

    // TESTING THE SOLUTION

    TreeSet<Integer> ts = new TreeSet<>();
    boolean valid;
    int[][] gridList = {
      {0, 0}, {0, 3}, {0, 6},
      {3, 0}, {3, 3}, {3, 6},
      {6, 0}, {6, 3}, {6, 6}
    };

    for(int i=0;i<9;i++) 
    {
      ts.clear();
      for(int j=0;j<9;j++) ts.add(solution[i][j]);
      valid = ts.size()==9;
      System.out.println("ROW "+(i+1)+": "+valid);
    }
    for(int i=0;i<9;i++) 
    {
      ts.clear();
      for(int j=0;j<9;j++) ts.add(solution[j][i]);
      valid = ts.size()==9;
      System.out.println("COL "+(i+1)+": "+valid);
    }
    for(int i=0;i<gridList.length;i++) 
    {
      int startI=gridList[i][0],
          startJ=gridList[i][1];
      ts.clear();

      for(int j=0;j<3;j++)
        for(int k=0;k<3;k++)
          ts.add(solution[startI+j][startJ+k]);
      
      valid = ts.size()==9;
      System.out.println("GRID "+(i+1)+": "+valid);
    }

  }

  static void displaySolution() {
    for(int i=0;i<9;i++) {
      for(int j=0;j<9;j++) {
        System.out.print(solution[i][j]);
        if(j==2 || j==5) System.out.print("| ");
      }
      System.out.print("\n----------------\n");
    }
    System.out.println();
  }

  static int[][] gridIndexList = {
    {0, 0, 0, 1, 1, 1, 2, 2, 2},
    {0, 0, 0, 1, 1, 1, 2, 2, 2},
    {0, 0, 0, 1, 1, 1, 2, 2, 2},

    {3, 3, 3, 4, 4, 4, 5, 5, 5},
    {3, 3, 3, 4, 4, 4, 5, 5, 5},
    {3, 3, 3, 4, 4, 4, 5, 5, 5},

    {6, 6, 6, 7, 7, 7, 8, 8, 8},
    {6, 6, 6, 7, 7, 7, 8, 8, 8},
    {6, 6, 6, 7, 7, 7, 8, 8, 8}
  };

  static SudokuContainer[] row = new SudokuContainer[9],
                    col = new SudokuContainer[9],
                    grid = new SudokuContainer[9];

  static void solve(int i, int j) 
  {

    if(freeSlot[i][j]) 
    {
      for(int val=1;val<=9;val++) 
      {
        if(isValidVal(i,j,val)) 
        {
          row[i].addVal(val);
          col[j].addVal(val);
          grid[getGridIndex(i,j)].addVal(val);
          solution[i][j] = val;

          if(i==8 && j==8) 
          {
            solutionFound = true;
            return;
          }

          int newI = i, newJ = j;
          newJ = (newJ+1)%9;
          if(newJ==0) newI++;

          solve(newI, newJ);

          if(!solutionFound) 
          {
            row[i].removeVal(val);
            col[j].removeVal(val);
            grid[getGridIndex(i,j)].removeVal(val);
            solution[i][j] = 0;
          }
        }
      }      
    }
    else 
    {
      int newI = i, newJ = j;
      newJ = (newJ+1)%9;
      if(newJ==0) newI++;

      solve(newI, newJ);
    }
  }

  static boolean isValidVal(int i, int j, int val) 
  {
    return !row[i].hasVal(val) && !col[j].hasVal(val) && 
           !grid[ getGridIndex(i,j) ].hasVal(val);
  }
  
  static int getGridIndex(int i, int j) 
  {
    return gridIndexList[i][j];
  }

}