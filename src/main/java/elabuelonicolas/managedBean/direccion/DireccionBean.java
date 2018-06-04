package elabuelonicolas.managedBean.direccion;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Direccion;
import elabuelonicolas.service.direccion.DireccionService;

@Named
public class DireccionBean {
	@Inject
	DireccionService direccionService;
	private List<Direccion> direccionList;

	public List<Direccion> getDireccionList() {
		if (direccionList == null)
			setDireccionList(direccionService.findAll());

		return direccionList;
	}

	private void setDireccionList(List<Direccion> direccionList) {
		this.direccionList = direccionList;
	}

	public void onRowEdit(RowEditEvent event) {
		Direccion direccion = ((Direccion) event.getObject());
		System.out.println("Datos direccion: " + direccion.getId());
		direccionService.update(direccion);

		FacesMessage msg = new FacesMessage("Direccion editado", direccion.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Direccion) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Direccion modificado", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
