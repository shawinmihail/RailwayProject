package gui;

import org.graphstream.algorithm.APSP;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import simulation.*;
import staticConstantInit.StaticConstantInit;
import java.util.HashMap;


public class Graphs
{

    org.graphstream.graph.Graph graphWays = new SingleGraph("graphWays");
    org.graphstream.graph.Graph graphRoutes = new SingleGraph("graphRoutes");

    String cssStyle ="graph {fill-color: white;}"
            +" node {fill-color: red; text-mode: normal;}"
            +" edge{fill-color: blue; }"
            +" edge.intersection {fill-color: red; size: 3px;}"
            +" edge.distinguished {fill-color: black; size: 3px;}"
            +" node.firstStation {fill-color: green;}"
            +" node.lastStation {fill-color: blue;}";

    APSP apsp = new APSP();
    View view;

    public Graphs() {

        graphWays.addAttribute("ui.stylesheet", cssStyle);
        buildWaysGraph();
        setIntersections(graphWays);
        distinguishWaysGraphIntersections();
        addWaysGraphToGui();

        buildRoutesGraph();
        setIntersections(graphRoutes);
    }

    protected void buildWaysGraph(){
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            Node lastNode = null;
            for (Station station : way.stations)
            {
                Node newNode = graphWays.addNode(station.name);
                newNode.setAttribute("ui.label", station.name);
                if (lastNode != null)
                {
                    try
                    {
                        HashMap<String, Object> attributes = new HashMap<>();
                        attributes.put("weight", getEdgeLength(lastNode.getId(), newNode.getId()));
                        attributes.put("layout.weight", 1.2);
                        graphWays.addEdge(lastNode.getId() + newNode.getId(), lastNode.getId(), newNode.getId()).addAttributes(attributes);
                        //graphWays.addEdge(newNode.getId() + lastNode.getId(), newNode.getId(), lastNode.getId()).addAttributes(attributes);
                        graphWays.getEdge(lastNode.getId() + newNode.getId()).setAttribute("ui.label", getEdgeLength(lastNode.getId(), newNode.getId()));
                    }
                    catch (EdgeRejectedException e) {e.printStackTrace();}
                }
                lastNode = newNode;
            }
        }
    }

    protected void buildRoutesGraph(){
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            Node lastNode = null;
            for (Station station : trainFactory.route)
            {
                Node newNode = null;
                try
                {
                    newNode = graphRoutes.addNode(station.name);
                }
                catch (IdAlreadyInUseException e)
                {
                    newNode = graphRoutes.getNode(station.name);
                }
                if (lastNode != null)
                {
                    HashMap<String,Object> attributes = new HashMap<>();
                    attributes.put("weight", getEdgeLength(lastNode.getId(), newNode.getId()));
                    attributes.put("layout.weight",1.1);
                    try
                    {
                        graphRoutes.addEdge(lastNode.getId() + newNode.getId(), lastNode.getId(), newNode.getId()).addAttributes(attributes);
                    }
                    catch(IdAlreadyInUseException e) {}
                    catch(EdgeRejectedException e){}
                    newNode.setAttribute("ui.label", station.name);
                }
                lastNode = newNode;
            }
        }
    }

    public void distinguishRoute(String name) {
        String name1 = null;
        String name2 = null;
        int i = 0;
        int routeLength = StaticConstantInit.wayScheme.trainFactories.get(name).route.length;
        for (Station station : StaticConstantInit.wayScheme.trainFactories.get(name).route)
        {
            if(i==0){
                name2 = station.name;
                graphWays.getNode(station.name).addAttribute("ui.class","firstStation");
                i++;
            }
            else {
                name1 = name2;
                name2 = station.name;
                try
                {
                    graphWays.getEdge(name1+name2).setAttribute("ui.class","distinguished");
                }
                catch (NullPointerException e)
                {
                    graphWays.getEdge(name2+name1).setAttribute("ui.class","distinguished");
                }
                i++;
                if(i==routeLength){
                    graphWays.getNode(station.name).addAttribute("ui.class","lastStation");
                }
            }
        }

    }

    protected void distinguishWaysGraphIntersections(){

        for (Intersection intersection : StaticConstantInit.wayScheme.intersections.values())
        {
            String name1 = intersection.firstStation.name;
            String name2 = intersection.secondStation.name;
            try
            {
                graphWays.getEdge(name1+name2).setAttribute("ui.class","intersection");
            }
            catch (NullPointerException e)
            {
                graphWays.getEdge(name2 + name1);
            }
        }
    }

    public void setNormalStyleWaysGraph() {
        for (Edge edge : graphWays.getEachEdge())
        {
            edge.addAttribute("ui.class","");
        }
        for (Node node : graphWays.getEachNode())
        {
            node.setAttribute("ui.class","");
        }
    }

    protected void setIntersections(Graph graph) {
        for (Intersection intersection : StaticConstantInit.wayScheme.intersections.values())
        {
            String name1 = intersection.firstStation.name;
            String name2 = intersection.secondStation.name;
            try
            {
                HashMap<String,Object> attributes = new HashMap<>();
                attributes.put("weight",0);
                attributes.put("layout.weight",0);
                graph.addEdge(name1 + name2, name1, name2).addAttributes(attributes);
            }
            catch (EdgeRejectedException e) {}
            catch (ElementNotFoundException e){}
            catch (IdAlreadyInUseException e){}
        }
    }

    protected int getEdgeLength(String station1_name, String station2_name){
        return (int)StaticConstantInit.wayScheme.times.get(station1_name+station2_name);
    }

    public Station[] findShortestRoute(String stationFrom, String stationTo) {
        apsp.init(graphRoutes);
        apsp.setDirected(false);
        apsp.setWeightAttributeName("weight");
        apsp.compute();
        APSP.APSPInfo info = graphRoutes.getNode(stationFrom).getAttribute(APSP.APSPInfo.ATTRIBUTE_NAME);
        Station[] shortestWay = new Station[info.getShortestPathTo(stationTo).getNodeCount()];
        int counter = 0;
        for (Node edge : info.getShortestPathTo(stationTo).getEachNode()){
            shortestWay[counter] = StaticConstantInit.wayScheme.findStation(edge.getId());
            counter++;
        }
        return shortestWay;
    }

    protected void addWaysGraphToGui() {
        Viewer viewer = new Viewer(graphWays, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);
    }

}
