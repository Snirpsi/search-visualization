package ecs.components.graphics.drawables;

import java.util.List;

import ecs.Component;
import ecs.components.graphics.Graphics;
import javafx.scene.Node;

public abstract class Drawable extends Component {
	public abstract List<Node> getShapes();

	public void notifyGraphics() {
		if (this.entity.hasComponent(Graphics.class)) {
			this.entity.getComponent(Graphics.class).notifyNewDrawable();
		}

	}

}
