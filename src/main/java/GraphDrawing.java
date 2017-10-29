import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.*;

public class GraphDrawing extends GraphMatrix{

    private Graph graph;

    GraphDrawing(String graphID) {
        super();

        graph = new SingleGraph(graphID);
    }

    public void formGraph(){

        for (int i = 1; i <= super.getNumberOfNodes(); i++) {
            graph.addNode(String.valueOf(i));
        }


        switch (super.getGraphType()){
            case taskGraph: formTaskGraph();
            break;
            case systemGraph: formSystemGraph();
            break;
        }

        for (Node node : graph) {
            for (Edge edge : node.getEachEnteringEdge()){
                int source = Integer.parseInt(String.valueOf(edge.getNode0().getAttribute("layer").toString()));
                int target = Integer.parseInt(String.valueOf(edge.getNode1().getAttribute("layer").toString()));
                if(source>=target) {
                    node.setAttribute("layer", String.valueOf(Integer.parseInt(String.valueOf(edge.getNode0().getAttribute("layer").toString())) + 1));

                }
            }
        }

        int j = -1;
        for (Node node : graph) {
            node.setAttribute("xy",j,Integer.parseInt("-"+String.valueOf(node.getAttribute("layer").toString())));
            j--;
        }
    }

    private void formTaskGraph(){
        int temp = 1;
        for (int i = 0; i <super.getNumberOfNodes() ; i++) {
            for (int j = temp; j <super.getNumberOfNodes() ; j++) {
                if(super.getMatrix()[i][j]!=0){
                    graph.addEdge(String.valueOf(i+1)+String.valueOf(j+1),String.valueOf(i+1),String.valueOf(j+1), true).addAttribute("ui.label", String.valueOf(super.getMatrix()[i][j]));
                }
            }
            temp++;
        }
        int i=0;
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId()+"/"+super.getMatrix()[i][i]);
            i++;
            node.addAttribute("layer","1");

        }


    }

    private void formSystemGraph(){
        int temp = 1;
        for (int i = 0; i <super.getNumberOfNodes() ; i++) {
            for (int j = temp; j <super.getNumberOfNodes() ; j++) {
                if(super.getMatrix()[i][j]!=0){
                    graph.addEdge(String.valueOf(i+1)+String.valueOf(j+1),String.valueOf(i+1),String.valueOf(j+1), false);
                }
            }
            temp++;
        }
        int i=0;
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
            i++;
            node.addAttribute("layer","1");

        }
    }

    public void drawGraph(boolean autoDraw){
        if(autoDraw==false){
        Viewer viewer = graph.display();
        viewer.disableAutoLayout();
        } else
            graph.display();
    }

    public Graph getGraph() {
        return graph;
    }


    ArrayList<Integer> criticalPaths = new ArrayList<Integer>();
    ArrayList<Integer> criticalRevercePaths = new ArrayList<Integer>();

    public void getCriticalPath(){

        for (int i = 1; i <= getNumberOfNodes() ; i++) {
            ArrayList<Integer> listOfNodePathes = new ArrayList<Integer>();
            ArrayList<Integer> listOfRevercePaths = new ArrayList<Integer>();
            listOfNodePathes.add(0);
            listOfRevercePaths.add(0);
            //System.out.print(i+": ");
            reverceGraphRecursion(i,listOfRevercePaths);
            graphRecursion(i, listOfNodePathes);
            int max = listOfNodePathes.get(0);
            for (int j = 0; j <listOfNodePathes.size() ; j++) {
                if(listOfNodePathes.get(j)>max)
                    max=listOfNodePathes.get(j);
            }

            int max2 = listOfRevercePaths.get(0);
            for (int j = 0; j <listOfRevercePaths.size() ; j++) {
                if(listOfRevercePaths.get(j)>max2)
                    max2=listOfRevercePaths.get(j);
            }
            criticalPaths.add(max);
            criticalRevercePaths.add(max2);
            //System.out.println();
        }
        System.out.println(criticalPaths);
        System.out.println(criticalRevercePaths);
        sortingOutput();

    }
    public void graphRecursion(int i, ArrayList<Integer> listOfNodePathes){
        for (Edge e : graph.getNode(String.valueOf(i)).getEachLeavingEdge()) {
            int id = Integer.parseInt(e.getNode1().getId());
            //System.out.print(id + "/");
            listOfNodePathes.set(listOfNodePathes.size()-1,
                    listOfNodePathes.get(listOfNodePathes.size()-1)+super.getMatrix()[i-1][i-1]);
            graphRecursion(id, listOfNodePathes);

        }
        if(graph.getNode(String.valueOf(i)).getLeavingEdgeSet().isEmpty()){
            listOfNodePathes.set(listOfNodePathes.size()-1,
                    listOfNodePathes.get(listOfNodePathes.size()-1)+super.getMatrix()[i-1][i-1]);
            listOfNodePathes.add(0);
            //System.out.println(listOfNodePathes);
        }
//        System.out.print("+");

    }
    public void reverceGraphRecursion(int i, ArrayList<Integer> listOfRevercePaths){
        for (Edge e : graph.getNode(String.valueOf(i)).getEachEnteringEdge()) {
            int id = Integer.parseInt(e.getNode0().getId());
            //System.out.print(id + "/");
            listOfRevercePaths.set(listOfRevercePaths.size()-1,
                    listOfRevercePaths.get(listOfRevercePaths.size()-1)+super.getMatrix()[id-1][id-1]);
            reverceGraphRecursion(id, listOfRevercePaths);

        }
        if(graph.getNode(String.valueOf(i)).getEnteringEdgeSet().isEmpty()){
            listOfRevercePaths.add(0);
            //System.out.println(listOfNodePathes);
        }
//        System.out.print("+");

    }

    private void sortingOutput(){
        HashMap<Integer,Integer> mapToSort16 = new HashMap<Integer, Integer>();
        for (int i = 0; i <criticalRevercePaths.size() ; i++) {
            mapToSort16.put(i, criticalRevercePaths.get(i));
        }
        mapToSort16 = (HashMap<Integer, Integer>) sortByValueAscending(mapToSort16);

        System.out.println("Sorting by ascending order:");
        for (Map.Entry<Integer, Integer> entry : mapToSort16.entrySet()) {
            System.out.println(entry.getKey()
                    + "(" + entry.getValue()+")");
        }
        HashMap<Integer,Integer> mapToSort3 = new HashMap<Integer, Integer>();
        for (int i = 0; i <criticalPaths.size() ; i++) {
            mapToSort3.put(i, criticalPaths.get(i));
        }
        System.out.println();
        System.out.println("Sort by descending order:");
        mapToSort3 = (HashMap<Integer, Integer>) sortByValueDescending(mapToSort3);

        for (Map.Entry<Integer, Integer> entry : mapToSort3.entrySet()) {
            System.out.println(entry.getKey()
                    + "(" + entry.getValue()+")");
        }
    }



    public static Map<Integer, Integer> sortByValueDescending(Map<Integer, Integer> map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static Map<Integer, Integer> sortByValueAscending(Map<Integer, Integer> map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
