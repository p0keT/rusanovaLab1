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
        GraphDrawing graphDrawing = new GraphDrawing( "initial");
        graphDrawing.outputMatrix();
//        graphDrawing.bfs(graphDrawing.getMatrix(),1);
        graphDrawing.formGraph();
        graphDrawing.drawGraph(false);


    }




}

