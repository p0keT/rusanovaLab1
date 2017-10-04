import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

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
                int source = Integer.parseInt(String.valueOf(edge.getNode0().getAttribute("layer")));
                int target = Integer.parseInt(String.valueOf(edge.getNode1().getAttribute("layer")));
                if(source>=target) {
                    node.setAttribute("layer", String.valueOf(Integer.parseInt(String.valueOf(edge.getNode0().getAttribute("layer"))) + 1));

                }
            }
        }

        int j = -1;
        for (Node node : graph) {
            node.setAttribute("xy",j,Integer.parseInt("-"+String.valueOf(node.getAttribute("layer"))));
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
}
