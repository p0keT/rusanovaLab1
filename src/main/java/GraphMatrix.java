import org.graphstream.stream.sync.SourceTime;

import java.util.*;

public class GraphMatrix {

    public static final int taskGraph = 1;
    public static final int systemGraph = 2;

    private int graphType;
    private int[][] matrix;

    private int numberOfNodes = 0;
    private int numberOfEdges = 0;
    private Stack<Integer> stack = new Stack<Integer>();

    private Queue<Integer> queue = new LinkedList<Integer>();


    GraphMatrix(){


        System.out.println("Choose graph for drowing:\n" +
                "1. Task graph\n" +
                "2. System graph");
        Scanner in = new Scanner(System.in);
        graphType = in.nextInt();
        System.out.println("Enter number of edges:");
        numberOfEdges = in.nextInt();
        switch(graphType){
            case taskGraph :
                enterTaskMatrixVitalik();
                break;
            case systemGraph :
                enterSystemMatrixVitalik();
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

    private void enterSystemMatrixVitalik(){

        inputNumberOfNodes();
        ArrayList<String> strMatrix = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the edges: <to> <from>");
        int count = 0, to = 0, from = 0;
        matrix = new int[numberOfNodes][numberOfNodes];
        ArrayList<Integer> fromPoints =new ArrayList<Integer>();
        ArrayList<Integer> toPoints =new ArrayList<Integer>();
        while (count < numberOfEdges)
        {
            boolean theSameInput = false;
            to = in.nextInt();
            from = in.nextInt();
            if(to==from || to>numberOfNodes-1 || from>numberOfNodes-1 || to<0 || from <0){
                System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                continue;
            }
            for (int i = 0; i <fromPoints.size() ; i++) {
                if((fromPoints.get(i)==from && toPoints.get(i)==to) || (fromPoints.get(i)==to && toPoints.get(i)==from)){
                    System.out.println("!!!ALARM!!! Your input already exist !!!ALARM!!!");
                    theSameInput = true;
                }
            }
            if(theSameInput==false) {
                matrix[from][to] = 1;
                fromPoints.add(from);

                matrix[to][from] = 1;
                toPoints.add(to);
                count++;
            }
        }
        if(isGraphConnected(matrix,0)==false){
            System.out.println("!!!ALARM!!! Graph is disconnected. Try again !!!ALARM!!!");
            enterSystemMatrixVitalik();
            return;
        }
    }

    private void enterTaskMatrixVitalik(){
        inputNumberOfNodes();
        ArrayList<String> strMatrix = new ArrayList<String>();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the edges: <from> <to> <weightOfEdge>");
        int count = 0, to = 0,weightTo = 0, from = 0,weightOfEdge = 0;

        ArrayList<Integer> fromPoints =new ArrayList<Integer>();
        ArrayList<Integer> toPoints =new ArrayList<Integer>();
        matrix = new int[numberOfNodes][numberOfNodes];
        while (count < numberOfEdges)
        {
            boolean theSameInput = false;

            from = in.nextInt();
            to = in.nextInt();
            weightOfEdge = in.nextInt();
            if(to<=from || to>numberOfNodes-1 || from>numberOfNodes-1 || to<0 || from <0){
                System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                continue;
            }

            if(weightOfEdge<1) {
                System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                continue;
            }

            for (int i = 0; i <fromPoints.size() ; i++) {
                if((fromPoints.get(i)==from && toPoints.get(i)==to)){
                    System.out.println("!!!ALARM!!! Your input already exist !!!ALARM!!!");
                    theSameInput = true;
                }
            }
            if(theSameInput==false) {
                matrix[from][to] = weightOfEdge;
                fromPoints.add(from);
                toPoints.add(to);
                count++;
            }
        }
        System.out.println("Enter weights of nodes: <weightOfNodes>");
        count = 0;
        while (count < numberOfNodes)
        {
            matrix[count][count]=in.nextInt();
            if(matrix[count][count]<1) {
                System.out.println("!!!ALARM!!! Wrong input !!!ALARM!!!");
                continue;
            }
            count++;
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

        if(isGraphConnected(matrix,0)==false){
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
            } while (numberOfNodes <= 1);
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
            } while (numberOfNodes <= 1);
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


    private boolean isGraphConnected(int adjacency_matrix[][], int source){
        int number_of_nodes = adjacency_matrix[source].length - 1;
        int[] visited = new int[number_of_nodes + 1];
        int i, element;
        visited[source] = 1;
        stack.push(source);
        while (!stack.isEmpty())
        {
            element = stack.pop();
            i = 1;// element;
            while (i <= number_of_nodes)
            {
                if (adjacency_matrix[element][i] == 1 && visited[i] == 0)
                {
                    stack.push(i);
                    visited[i] = 1;
                }
                i++;
            }
        }

        System.out.print("The source node " + source + " is connected to: ");
        int count = 0;
        for (int v = 1; v <= number_of_nodes; v++)
            if (visited[v] == 1)
            {
                System.out.print(v + " ");
                count++;
            }
        System.out.println();
        if (count == number_of_nodes)
            return true;
        else
            return false;
    }


}
