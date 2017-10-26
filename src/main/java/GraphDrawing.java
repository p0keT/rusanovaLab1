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
    int maxCriticalPathsVariable=0;
    ArrayList<Integer> maxmimalPaths = new ArrayList<Integer>();
    int maxPathsVariable=0;




    public void getCriticalPath(){

        for (int i = 1; i <= getNumberOfNodes() ; i++) {
            ArrayList<Integer> listOfNodePathes = new ArrayList<Integer>();
            listOfNodePathes.add(0);
            ArrayList<Integer> listOfNodePathesV1 = new ArrayList<Integer>();
            listOfNodePathesV1.add(0);
            //System.out.print(i+": ");
            graphRecursion(i, listOfNodePathes, listOfNodePathesV1);
            int max = listOfNodePathes.get(0);
            for (int j = 0; j <listOfNodePathes.size() ; j++) {
                if(listOfNodePathes.get(j)>max)
                    max=listOfNodePathes.get(j);
            }
            criticalPaths.add(max);
            int maxV1 = listOfNodePathesV1.get(0);
            for (int j = 0; j <listOfNodePathesV1.size() ; j++) {
                if(listOfNodePathesV1.get(j)>max)
                    max=listOfNodePathesV1.get(j);
            }
            maxmimalPaths.add(maxV1);
            //System.out.println();
        }
        System.out.println(criticalPaths);
        maxCriticalPathsVariable = criticalPaths.get(0);
        for (int j = 0; j <criticalPaths.size() ; j++) {
            if(criticalPaths.get(j)>maxCriticalPathsVariable)
                maxCriticalPathsVariable=criticalPaths.get(j);
        }
        System.out.println("max: "+maxCriticalPathsVariable);
        System.out.println(maxmimalPaths);
        maxPathsVariable = maxmimalPaths.get(0);
        for (int j = 0; j <maxmimalPaths.size() ; j++) {
            if(maxmimalPaths.get(j)>maxPathsVariable)
                maxPathsVariable=maxmimalPaths.get(j);
        }
        System.out.println("max: "+maxPathsVariable);

        formulaV1();
        //sortingOutput();
    }

    private void formulaV1(){
        ArrayList<Double> results = new ArrayList<>();
        for (int i = 0; i < getNumberOfNodes(); i++) {
            results.add((maxmimalPaths.get(i)*1.0/maxPathsVariable)+(criticalPaths.get(i)*1.0/maxCriticalPathsVariable));
            System.out.println("Result"+i+": " +results.get(results.size()-1));

        }

        HashMap<Integer,Double> mapToSort = new HashMap<Integer, Double>();
        for (int i = 0; i <results.size() ; i++) {
            mapToSort.put(i, results.get(i));
        }
        mapToSort = (HashMap<Integer, Double>) sortByValueDescending2(mapToSort);

        System.out.println(mapToSort);
    }

    public void graphRecursion(int i, ArrayList<Integer> listOfNodePathes, ArrayList<Integer> listOfNodePathesV1){
        for (Edge e : graph.getNode(String.valueOf(i)).getEachLeavingEdge()) {
            int id = Integer.parseInt(e.getNode1().getId());
            //System.out.print(id + "/");
            listOfNodePathes.set(listOfNodePathes.size()-1,
                    listOfNodePathes.get(listOfNodePathes.size()-1)+super.getMatrix()[i-1][i-1]);

            listOfNodePathesV1.set(listOfNodePathesV1.size()-1,
                    listOfNodePathesV1.get(listOfNodePathesV1.size()-1)+1);

            graphRecursion(id, listOfNodePathes, listOfNodePathesV1);

        }
        if(graph.getNode(String.valueOf(i)).getLeavingEdgeSet().isEmpty()){
            listOfNodePathes.set(listOfNodePathes.size()-1,
                    listOfNodePathes.get(listOfNodePathes.size()-1)+super.getMatrix()[i-1][i-1]);
            listOfNodePathesV1.set(listOfNodePathesV1.size()-1,
                    listOfNodePathesV1.get(listOfNodePathesV1.size()-1)+1);
            listOfNodePathes.add(0);
            listOfNodePathesV1.add(0);
            //System.out.println(listOfNodePathes);
        }
//        System.out.print("+");

    }
//
//    private void sortingOutput(){
//        HashMap<Integer,Integer> mapToSort = new HashMap<Integer, Integer>();
//        for (int i = 0; i <criticalPaths.size() ; i++) {
//            mapToSort.put(i, criticalPaths.get(i));
//        }
//        mapToSort = (HashMap<Integer, Integer>) sortByValueAscending(mapToSort);
//
//        System.out.println("Sorting by ascending order:");
//        for (Map.Entry<Integer, Integer> entry : mapToSort.entrySet()) {
//            System.out.println(entry.getKey()
//                    + "(" + entry.getValue()+")");
//        }
//        System.out.println();
//        System.out.println("Sort by descending order:");
//        mapToSort = (HashMap<Integer, Integer>) sortByValueDescending(mapToSort);
//
//        for (Map.Entry<Integer, Integer> entry : mapToSort.entrySet()) {
//            System.out.println(entry.getKey()
//                    + "(" + entry.getValue()+")");
//        }
//    }

    ArrayList<Integer> numberNodeEdges = new ArrayList<Integer>();
    public void getMaxEdges(){
        for (Node node:graph.getEachNode()
             ) {
            numberNodeEdges.add(node.getLeavingEdgeSet().size());
        }
//        System.out.println(numberNodeEdges);
        HashMap<Integer,Integer> mapToSort = new HashMap<Integer, Integer>();
        for (int i = 0; i < numberNodeEdges.size() ; i++) {
            mapToSort.put(i, numberNodeEdges.get(i));
        }

        System.out.println();
        System.out.println("Sort by descending order:");
        mapToSort = (HashMap<Integer, Integer>) sortByValueDescending(mapToSort);

        for (Map.Entry<Integer, Integer> entry : mapToSort.entrySet()) {
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

    public static Map<Integer, Double> sortByValueDescending2(Map<Integer, Double> map) {
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
