package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.EstadoDao;
import model.Estado;

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter {
	
	private EstadoDao dao;

	public EstadoConverter() {
		dao = new EstadoDao();
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
		
		return ((Estado) value).getId().toString();
	}

}
