package pcrf.betha.util;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pcrf.betha.base.entity.Entity;

@FacesConverter("entityConverter")
public class EntityConverter implements Converter {

	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value != null && !value.isEmpty())
			return this.getAttributesFrom(component).get(value);
		
		return null;
	}

	public String getAsString(FacesContext ctx, UIComponent component, Object value) {
		if (value != null) {// && !value.equals("0") && !value.equals("")) {
			Entity entity = (Entity) value;

			this.addAttribute(component, entity);

			Object codigo = entity.getId();
			if (codigo != null) {
				return String.valueOf(this.getKey(component, codigo.toString()));
			}
		}

		return null;
	}

	protected void addAttribute(UIComponent component, Entity o) {
		String key = o.getId().toString(); 
		this.getAttributesFrom(component).put(this.getKey(component, key), o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

	protected String getKey(UIComponent component, String id) {
		return id;
	}
	
}
