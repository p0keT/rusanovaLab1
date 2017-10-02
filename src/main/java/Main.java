import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        new Main();
    }

    public Main() {
        int numberOfNodes = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number of nodes:");
        numberOfNodes = in.nextInt();

        ArrayList<String> strMatrix = new ArrayList<String>();

        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j < numberOfNodes; j++) {

                System.out.println("Enter " + j + " element of the " + i + " row of matrix:");
                strMatrix.add(in.next());
            }
        }

        int[][] matrix = new int[numberOfNodes][numberOfNodes];
        int temp2=0;
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j <numberOfNodes ; j++) {
                matrix[i][j]=Integer.parseInt(String.valueOf(strMatrix.get(temp2)));
                temp2++;
            }
        }

        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j <numberOfNodes ; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

        Graph graph = new SingleGraph("tutorial 1");


        for (int i = 1; i <= numberOfNodes; i++) {
            graph.addNode(String .valueOf(i));
        }

        int temp = 1;
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = temp; j <numberOfNodes ; j++) {
                if(matrix[i][j]!=0){
                    graph.addEdge(String.valueOf(i+1)+String.valueOf(j+1),String.valueOf(i+1),String.valueOf(j+1), true).addAttribute("ui.label", String.valueOf(matrix[i][j]));
                }
            }
            temp++;
        }

        int i=0;
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId()+"/"+matrix[i][i]);
            i++;
            node.addAttribute("layer","1");

        }
        int supertemp = 0;
        for (Node node : graph) {
            for (Edge edge : node.getEachEnteringEdge()){
                int source = Integer.parseInt(String.valueOf(edge.getNode0().getAttribute("layer")));
                int target = Integer.parseInt(String.valueOf(edge.getNode1().getAttribute("layer")));
                if(source>=target) {
                    node.setAttribute("layer", String.valueOf(Integer.parseInt(String.valueOf(edge.getNode0().getAttribute("layer"))) + 1));
                    supertemp =Integer.parseInt(String.valueOf(edge.getNode0().getAttribute("layer")))+1;
                }
            }
        }

        int j = -1;
        for (Node node : graph) {
            node.setAttribute("xy",j,Integer.parseInt("-"+String.valueOf(node.getAttribute("layer"))));
            System.out.println(j+"/"+Integer.parseInt(String.valueOf(node.getAttribute("layer"))));
            j--;
        }


        Viewer viewer = graph.display();
// Let the layout work ...
        viewer.disableAutoLayout();

        //explore(graph.getNode("A"));
    }

    public void explore(Node source) {
        Iterator<? extends Node> k = source.getBreadthFirstIterator();

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
            sleep();
        }
    }

    protected void sleep() {
        try { Thread.sleep(1000); } catch (Exception e) {}
    }

    protected String styleSheet =
            "node {" +
                    "	fill-color: green;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";

/*

        //----------------------------------------------------------------------------------------------

        int numberOfNodes = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number of nodes:");
        numberOfNodes = in.nextInt();

        ArrayList<String> strMatrix = new ArrayList<String>();

        for (int i = 0; i <numberOfNodes ; i++) {
            System.out.println("Enter "+i+" row of matrix:");
            strMatrix.add(in.next());
        }

        int[][] matrix = new int[numberOfNodes][numberOfNodes];
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j <numberOfNodes ; j++) {
                matrix[i][j]=Integer.parseInt(String.valueOf(strMatrix.get(i).charAt(j)));
            }
        }

        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = 0; j <numberOfNodes ; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(node(String.valueOf(matrix[i][i])));
        }

        int temp = 1;
        for (int i = 0; i <numberOfNodes ; i++) {
            for (int j = temp; j <numberOfNodes ; j++) {
                if(matrix[i][j]!=0){
                    nodes.get(i).link(to(nodes.get(j)).with(Label.of(String.valueOf(matrix[i][j]))));
                }
            }
            temp++;
        }






    }
    */
}

