package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.ComboDao;
import model.Combo;

@FacesConverter(forClass = Combo.class)
public class ComboConverter implements Converter {

	private ComboDao dao;

	public ComboConverter() {
		dao = new ComboDao();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		
		return dao.buscarPorID(new Long(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		
		return ((Combo) value).getId().toString();
	}
}
