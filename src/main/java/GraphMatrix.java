import org.graphstream.stream.sync.SourceTime;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GraphMatrix {

    public static final int taskGraph = 1;
    public static final int systemGraph = 2;

    private int graphType;
    private int[][] matrix;
    private int numberOfNodes = 0;

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

}
