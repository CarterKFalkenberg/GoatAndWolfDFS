public class Main {
    public static void main(String[] args){
        Solver solver = new Solver();
        boolean foundSolution = solver.DFS();
        if (foundSolution){
            System.out.println(solver.resultToString());
        }

    }
}
