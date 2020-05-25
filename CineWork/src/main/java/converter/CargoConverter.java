package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.CargoDao;
import model.Cargo;

@FacesConverter(forClass = Cargo.class)
public class CargoConverter implements Converter {

	private CargoDao dao;

	public CargoConverter() {
		dao = new CargoDao();
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
		
		return ((Cargo) value).getId().toString();
	}
}
