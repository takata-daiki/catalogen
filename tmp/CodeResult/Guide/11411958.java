package artemis.vide.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import common.vide.model.Notifier;

//??????????
public class Guide extends Notifier {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String GUIDE_POSTITION = "guide position";
	public static final String GUIDE_HORIZONTAL = "guide horizontal";
	public static final String GUIDE_CHILDREN = "guide children";
	protected int position = 0;
	protected boolean horizontal = false;
	protected Map<AbstractModel, Integer> map = new HashMap<AbstractModel, Integer>();

	public Guide() {

	}

	public Guide(boolean isHorizontal) {
		setHorizontal(isHorizontal);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		if (this.position != position) {
			int oldPosition = this.position;
			this.position = position;
			firePropertyChange(GUIDE_POSTITION, oldPosition, position);
		}
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
		firePropertyChange(GUIDE_HORIZONTAL, null, horizontal);
	}

	public Map<AbstractModel, Integer> getMap() {
		return map;
	}

	public void setMap(Map<AbstractModel, Integer> map) {
		this.map = map;
		firePropertyChange(GUIDE_CHILDREN, null, map);
	}

//	public void attachModel(AbstractModel model, int alignment) {
//		if (map.containsKey(model) && getAlignment(model) == alignment)
//			return;
//		map.put(model, new Integer(alignment));
//		Guide guide = isHorizontal() ? model.getHorizontalGuide() : model
//				.getVerticalGuide();
//		if (guide != null && guide != this)
//			guide.detachModel(model);
//		if (isHorizontal())
//			model.setHorizontalGuide(this);
//		else
//			model.setVerticalGuide(this);
//		firePropertyChange(GUIDE_CHILDREN, null, model);
//
//	}
//
//	public void detachModel(AbstractModel model) {
//		if (!map.containsKey(model))
//			return;
//		map.remove(model);
//		if (isHorizontal())
//			model.setHorizontalGuide(null);
//		else
//			model.setVerticalGuide(null);
//		firePropertyChange(GUIDE_CHILDREN, null, model);
//	}

	public int getAlignment(AbstractModel model) {
		if (map.get(model) != null)
			return map.get(model).intValue();
		return -2;
	}

	public Set<AbstractModel> getModels() {
		return map.keySet();
	}

}
