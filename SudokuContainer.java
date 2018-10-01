class SudokuContainer
{
  boolean[] container;
  SudokuContainer() {
    container = new boolean[10];
  }

  boolean hasVal(int val) {
    return container[val];
  }

  void addVal(int val) {
    container[val] = true;
  }

  void removeVal(int val) {
    container[val] = false;
  }
}