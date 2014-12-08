package pcrf.betha.base.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract Object getId();
	
	public abstract String getLabel();
	
	public Entity() {
	}
	
	@Override
	public boolean equals(Object other) {
		Entity entity = (Entity) other;
		
		if (entity == null)
			return false;
		
		return (this.getId() == null || entity.getId() == null)? this == other: this.getId().equals(entity.getId());
	}

	@Override
	public String toString() {
		return getLabel();
	}

	public Boolean isTransient() {
		return this.getId() == null;
	}
}
