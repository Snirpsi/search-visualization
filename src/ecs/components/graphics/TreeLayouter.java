package ecs.components.graphics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ai_algorithm.SearchNode;
import application.UpdateRegistry;
import ecs.Component;
import ecs.components.Position;
import ecs.components.TreeComponent;

import tools.Vector2D;

//Source --> https://stackoverflow.com/questions/33328245/radial-tree-layout-algorithm

public class TreeLayouter extends Component {

	public static final int leaveDistance = 20;

	public static final Vector2D OFFSET = new Vector2D(0, 0);
	public static final double PARENT_DISTANCE = 100;
	public static final double SIBLING_DISTANCE = 40;
	double angle = 0;
	double angleRange = 0;

	public TreeLayouter() {
		this.angleRange = 2 * Math.PI;
	}

	void setPosition(Vector2D position) {
		this.entity.getComponent(Position.class).setPosition(position);
	}

	public void layout() {
		// Ben�tigte Komponenten holen
		// System.out.println("BAUM UPDATE LAYOUT");
		TreeComponent nodeComp = super.entity.getComponent(TreeComponent.class);
		Position pos = super.entity.getComponent(Position.class);
		nodeComp.updateDepth();

		if (nodeComp.getParent() == null) {
			pos.setPosition(OFFSET);
		}
		int n = nodeComp.getChildren().size();
		for (int i = 0; i < n; i++) {
			TreeComponent child = nodeComp.getChildren().get(i);
			TreeLayouter childTreeLayouter = child.entity.getComponent(TreeLayouter.class);
			if (childTreeLayouter == null) {
				return;
			}

			childTreeLayouter.angle = this.angle + this.angleRange / n * i;
			childTreeLayouter.angleRange = this.angleRange / n;

			// System.out.println(child.getDepth());
			var neuPos = new Vector2D(
					(float) /* (DISTANCE * child.getDepth()) */ (PARENT_DISTANCE * child.getDepth() * child.getDepth()
							+ child.getDepth() * child.getSibling() * SIBLING_DISTANCE
							+ Math.sin(PARENT_DISTANCE * angle * PARENT_DISTANCE) * SIBLING_DISTANCE),
					0);
			Vector2D neuRotPos = neuPos.rotate(childTreeLayouter.angle);
			childTreeLayouter.setPosition(neuRotPos.add(OFFSET));
			// BEN�TIGT WENN Baum zu einem sp�teren zeitpunkt Struktur grundlegend �ndert
			childTreeLayouter.layout();

		}
	}

	public void layout2() { // temporary

		TreeComponent nodeComp = super.entity.getComponent(TreeComponent.class);
		Position pos = super.entity.getComponent(Position.class);
		nodeComp.updateDepth();

		if (nodeComp.getParent() == null) {
			pos.setPosition(OFFSET);
		}

		TreeComponent root = nodeComp.getRoot();
		System.out.println("ROOTDEPTH " +root.getDepth());
		root.updateDepth();
		List<TreeComponent> leaves = root.getLeavesInOrder();
		int i = 0;
		// positionieren der blattknoten

		// positionieren der elternknoten der bl�tter
		for (TreeComponent leave : leaves) {
			TreeComponent now = leave;
			now.updateDepth();
			Position leavePos = leave.entity.getComponent(Position.class);
			leavePos.setPosition(new Vector2D(i * leaveDistance, leaveDistance * (now.updateDepth())));
			i++;

			while (now.getParent() != null) {
				TreeComponent nowParent = now.getParent();
				nowParent.updateDepth();
				Position parentPos = nowParent.entity.getComponent(Position.class);
				// update tiefe der kinder
				for (TreeComponent child : nowParent.getChildren()) {
					child.getDepth();
					
					Vector2D pso = child.entity.getComponent(Position.class).getPosition();
					 child.entity.getComponent(Position.class).setPosition(new Vector2D( pso.x ,leaveDistance * (now.updateDepth())));
				}
				TreeComponent parentsFirstChild = nowParent.getChildren().get(0);
				Position parentsFirstChildPosition = parentsFirstChild.entity.getComponent(Position.class);
				float parentsFirstChildPositionX = parentsFirstChildPosition.getPosition().x;
				Vector2D newParentPos = new Vector2D(parentsFirstChildPositionX,
						leaveDistance * (leave.getDepth() - 1));

				parentPos.setPosition(newParentPos);
//				Vector2D nowPos = now.entity.getComponent(Position.class).getPosition();
//				Vector2D parentPos = now.getParent().entity.getComponent(Position.class).getPosition();
//
//				Vector2D nextParentPos = new Vector2D(nowPos.x, leaveDistance * now.getDepth());
//				now.getParent().entity.getComponent(Position.class).setPosition(nextParentPos);
				now = nowParent;
			}

		}

	}

	@Override
	public void update(float deltaT) {
	}
}