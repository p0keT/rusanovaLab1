

public class Main {
    public static void main(String[] args){
        GraphDrawing graphDrawing = new GraphDrawing( "initial");
        graphDrawing.outputMatrix();
        graphDrawing.formGraph();
        graphDrawing.drawGraph(false);
        graphDrawing.getCriticalPath();
        graphDrawing.getMaxEdges();


    }





}

