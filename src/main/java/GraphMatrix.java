import org.graphstream.stream.sync.SourceTime;

import java.util.*;

public class GraphMatrix {

    public static final int taskGraph = 1;
    public static final int systemGraph = 2;

    private int graphType;
    private int[][] matrix;
    private int numberOfNodes = 0;

    private Queue<Integer> queue = new LinkedList<Integer>();


    GraphMatrix(){


        System.out.println("Choose graph for drowing:\n" +
                "1. Task graph\n" +
                "2. System graph");
        Scanner in = new Scanner(System.in);
        graphType = in.nextInt();

        switch(graphType){
            case taskGraph :
                enterTaskMatrix();
                break;
            case systemGraph :
                enterSystemMatrix();
                break;
        }
    }

    private void enterTaskMatrix(){

        inputNumberOfNodes();

        ArrayList<String> strMatrix = new ArrayList<String>();

        Scanner in = new Scanner(System.in);
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j < numberOfNodes; j++) {

                System.out.println("Enter " + j + " element of the " + i + " row of matrix:");
                strMatrix.add(in.next());
                try {
                    if(Integer.parseInt(strMatrix.get(strMatrix.size() - 1))<0) {
                        System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                        strMatrix.remove(strMatrix.size() - 1);
                        j--;
                        continue;
                    }
                    if(i>0 && j<i && Integer.parseInt(strMatrix.get(strMatrix.size() - 1))!=0){
                        System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                        strMatrix.remove(strMatrix.size() - 1);
                        j--;
                        continue;
                    }
                    if(i==j && Integer.parseInt(strMatrix.get(strMatrix.size() - 1))==0){
                        System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                        strMatrix.remove(strMatrix.size() - 1);
                        j--;
                        continue;
                    }
                }catch (NumberFormatException e){
                    System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                    strMatrix.remove(strMatrix.size() - 1);
                    j--;
                    continue;
                }


            }
        }

        matrix = new int[numberOfNodes][numberOfNodes];
        int temp2=0;
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j <numberOfNodes ; j++) {
                matrix[i][j]=Integer.parseInt(String.valueOf(strMatrix.get(temp2)));
                temp2++;
            }
        }


    }
    private void enterSystemMatrix(){

        inputNumberOfNodes();

        ArrayList<String> strMatrix = new ArrayList<String>();
        matrix = new int[numberOfNodes][numberOfNodes];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                System.out.println("Enter " + j + " element of the " + i + " row of matrix:");
                strMatrix.add(in.next());
                try {
                    if(Integer.parseInt(strMatrix.get(strMatrix.size() - 1))<0 || Integer.parseInt(strMatrix.get(strMatrix.size() - 1))>1) {
                        System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                        strMatrix.remove(strMatrix.size() - 1);
                        j--;
                        continue;
                    }
                    if(i==j && Integer.parseInt(strMatrix.get(strMatrix.size() - 1))!=0){
                        System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                        strMatrix.remove(strMatrix.size() - 1);
                        j--;
                        continue;
                    }



                }catch (NumberFormatException e){
                    System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                    strMatrix.remove(strMatrix.size() - 1);
                    j--;
                    continue;
                }
                matrix[i][j]=Integer.parseInt(strMatrix.get(strMatrix.size() - 1));
                if(i>0 && j<i && Integer.parseInt(strMatrix.get(strMatrix.size() - 1))!=matrix[j][i]){
                    System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                    strMatrix.remove(strMatrix.size() - 1);
                    j--;
                    continue;
                }
            }
        }

        matrix = new int[numberOfNodes][numberOfNodes];
        int temp2=0;
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j <numberOfNodes ; j++) {
                matrix[i][j]=Integer.parseInt(String.valueOf(strMatrix.get(temp2)));
                temp2++;
            }
        }

        if(isGraphConnected(matrix,1)==false){
            System.out.println("!!!ALARM!!! Graph is disconnected. Try again !!!ALARM!!!");
            enterSystemMatrix();
            return;
        }
    }

    private void inputNumberOfNodes(){
        Scanner in = new Scanner(System.in);
        switch(getGraphType()) {
            case taskGraph : do {
                                    try {
                                        System.out.println("Enter number of nodes:");
                                        numberOfNodes = in.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                                        inputNumberOfNodes();
                                        return;
                                    }
                                } while (numberOfNodes <= 0);
                                break;
            case systemGraph : do {
                                    try {
                                        System.out.println("Enter number of nodes:");
                                        numberOfNodes = in.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                                        inputNumberOfNodes();
                                        return;
                                    }
                                } while (numberOfNodes <= 0);
                                    break;
        }
    }

    public int getGraphType() {
        return graphType;
    }

    public int[][] getMatrix() {
        return matrix;
    }
    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void outputMatrix(){
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j <numberOfNodes ; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }



    private boolean isGraphConnected(int adjacency_matrix[][], int source)
    {
        int number_of_nodes = adjacency_matrix[source].length - 1;

        int[] visited = new int[number_of_nodes + 1];
        int i, element;
        visited[source] = 1;
        queue.add(source);
        while (!queue.isEmpty())
        {
            element = queue.remove();
            i = element;
            while (i <= number_of_nodes)
            {
                if (adjacency_matrix[element][i] == 1 && visited[i] == 0)
                {
                    queue.add(i);
                    visited[i] = 1;
                }
                i++;
            }
        }
        boolean connected = false;

        for (int vertex = 1; vertex <= number_of_nodes; vertex++)
        {
            if (visited[vertex] == 1)
            {
                connected = true;
            } else
            {
                connected = false;
                break;
            }
        }

        return connected;
    }


}
