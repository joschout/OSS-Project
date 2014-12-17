package be.kuleuven.cs.oss.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationCreator;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class TreeNodeRV {

	private static final int X_OFFSET = 50;
	private static final int Y_OFFSET = 50;
	private ResourceVisualizationCreator rvf;
	private TreeNode treeNode;

	private ResourceVisualization rv = null;
	private List<Connection> connections = new ArrayList<Connection>();
	private TreeMap<String, TreeNodeRV> children = new TreeMap<String, TreeNodeRV>();

	private int maxRight;
	private int level = 0;
	
	private final static Logger LOG = LoggerFactory.getLogger(TreeNodeRV.class);

	/**
	 * Class that represents trees of resourcevisualizations. 
	 * 
	 * @param rvf ResourceVisualizationCreator used to create the visualizations
	 * @param treeNode TreeNode corresponding to this treenode.
	 */
	
	public TreeNodeRV(ResourceVisualizationCreator rvf, TreeNode treeNode) {
		this.rvf = rvf;
		this.treeNode = treeNode;
		
		makeRv();
		//LOG.info("MADE RV OF NODE");
		createChildren();
		//LOG.info("CHILDREN: " + this.children.toString());
		//LOG.info("MADE CHILDREN");
		createConnections();
		//LOG.info("MADE CONNECTIONS");
		
	}

	private void createChildren() {
		TreeMap<String, TreeNode> nodeChildren = treeNode.getChildren();
		
		for(Map.Entry<String, TreeNode> entry : nodeChildren.entrySet()) {
			TreeNode treeNode = entry.getValue();

			TreeNodeRV child = new TreeNodeRV(rvf, treeNode);
			child.setLevel(this.level + 1);
			//maxRight = maxRight + child.getMaxRight() + OFFSET; // Hier moet geen maxRight ook nog eens bij denk ik
			this.children.put(entry.getKey(), child); //Add the child to the internal children list
		}
	}


	private void createConnections() {
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()) {
			TreeNodeRV treeNodeRV = entry.getValue();

			ResourceVisualization rvChild = treeNodeRV.getRv();
			
			Connection connection = new Connection(rvChild, rv);
			connections.add(connection);
		}	
	}


	

	private void makeRv() {
		if(!isRoot()){
			Resource res = treeNode.getResource();
			rv =  rvf.create(res);
		}
	}
	
	/**
	 * The maximal right position held in this TreeNodeRV.
	 * @return int maxRight the maximal right position
	 */
	public int getMaxRight() {
		return maxRight;
	}
	
	/**
	 * Getter for the Resource Visualization of this treenode
	 * @return ResourceVisualization this node's RV
	 */
	public ResourceVisualization getRv() {
		return rv;
	}
	
	/**
	 * Getter for this treenode's RV and all it's children.
	 * 
	 * @return List<ResourceVisualization> list containing this RV and all it's children.
	 */
	public List<ResourceVisualization> getAllRvs() {
		ArrayList<ResourceVisualization> rvs = new ArrayList<ResourceVisualization>();
		//rv is null if this is the root node
		if(rv != null) rvs.add(rv);		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
			rvs.addAll(entry.getValue().getAllRvs());
		}
		return rvs;
	}
	
	/**
	 * Returns this nodes connections and the connections of all its children.
	 * @return List<Connection> list of all connections of this node and its children
	 */
	public List<Connection> getAllConnections(){
		ArrayList<Connection> cons = new ArrayList<Connection>();
		
		if(!isRoot()) {
			cons.addAll(connections);
		}
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
			cons.addAll(entry.getValue().getAllConnections());
		}
		return cons;
	}
	
	/**
	 * Returns all connections of this node
	 * @return List<Connection> all connections of this node
	 */
	public List<Connection> getConnections() {
		return connections;
	}
	
	/**
	 * Checker to see if this node is the root
	 * @return boolean True if it is a root, false if it is not.
	 */
	public boolean isRoot(){
		return treeNode.isRoot();
	}
	
	/**
	 * Setter for the level this node is at in the hierarchy
	 * @param level the level to be set.
	 */
	public void setLevel(int level){
		this.level = level;
	}
	
	/**
	 * getter for the level of this node
	 * @return int level of this node in the hierarchy
	 */
	public int getLevel(){
		return this.level;
	}
	
	/**
	 * Method to update all the x positions of the node so that nothing overlaps.
	 * @param maxRight the current maximal right x position. When first calling this function this should be th place where you want to start the tree
	 * @return returns the structure with updated x positions
	 */
	public TreeNodeRV updateXPosition(int maxRight){

		if (isRoot()) {
			this.maxRight = maxRight;
			
		}
		else {
			//System.out.println("MAXRIGHT: " + maxRight + " " + rv.getWidth());
			this.maxRight = maxRight + rv.getWidth();
		}
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){			
			TreeNodeRV child = entry.getValue().updateXPosition(this.maxRight);
			int childMaxRight = child.getMaxRight();
			if (this.maxRight <= childMaxRight){
				this.maxRight = childMaxRight;
			}			
		}
		this.maxRight += X_OFFSET;
		
		if(!isRoot()){
			calculateXPositionRv();
		}
		return this;
		
	}
	
	private void calculateXPositionRv() {
		int x = 0;
		
		if(children.firstEntry() != null) {
			int xFirstChild = children.firstEntry().getValue().getRv().getPosition().getX();
			int xLastChild = children.lastEntry().getValue().getRv().getPosition().getX();
			
			x = (xFirstChild + xLastChild) / 2;
		}
		else {
			x = this.maxRight - rv.getWidth()/2;
		}
		
		Position position = new Position(x, 0);
		rv.setPosition(position);
		
	}
	
	
	
	/**
	 * method to calculate the maximum height of the resources in each different level
	 * returns a map of the level and its corresponding maximum height.
	 * 
	 * @param currentMaxHeights
	 * @return
	 */
	
	public Map<Integer, Integer> getMaxHeightPerLevel(Map<Integer, Integer> currentMaxHeights){
		int level = getLevel();
		
		int height;
		if(isRoot()) {
			height = 0;
		}
		else {
			height = rv.getHeight();
		}
		
		if(currentMaxHeights.containsKey(level)){
			int currentMaxHeight = currentMaxHeights.get(level);
			if(height > currentMaxHeight){
				currentMaxHeights.put(level, height);
			}
	
		}else{
			currentMaxHeights.put(level, height);
		}
		
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
			currentMaxHeights = entry.getValue().getMaxHeightPerLevel(currentMaxHeights);	
		}
		
		return currentMaxHeights;
	}
	
	public void updateYPositions(Map<Integer, Integer> maxHeights){
		int level = getLevel();
		if(level == 0){ //In case the node is the root, height doens't get set
			for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
				ResourceVisualization childRV = entry.getValue().getRv();
				
				int y = Y_OFFSET + childRV.getHeight()/2;
				childRV.getPosition().setY(y);
				entry.getValue().updateYPositions(maxHeights);
			}
		}
		else{
			for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
				ResourceVisualization childRV = entry.getValue().getRv();
				calcYPositionChild(level, maxHeights, childRV);
				
				entry.getValue().updateYPositions(maxHeights);
			}
			
		}
		
	}
	
	private Position calcYPositionChild(int level, Map<Integer, Integer> maxHeights, ResourceVisualization childRV) {
		if (isRoot()) {
			return new Position(0, 0);
		}
		
		int maxHeight = maxHeights.get(level);
		Position position = childRV.getPosition();
		int y = rv.getPosition().getY() - rv.getHeight()/2 + maxHeight + Y_OFFSET + childRV.getHeight()/2;
		
		position.setY(y);
		return position;
	}

}
