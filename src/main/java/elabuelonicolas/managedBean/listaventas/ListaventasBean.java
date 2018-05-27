package elabuelonicolas.managedBean.listaventas;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Listaventas;
import elabuelonicolas.service.listaventas.ListaventasService;

@Named
public class ListaventasBean {
	@Inject
	ListaventasService listaventasService;
	private List<Listaventas> listaventasList;

	public List<Listaventas> getVentasList() {
		if (listaventasList == null)
			setListaventasList(listaventasService.findAll());

		return listaventasList;
	}

	private void setListaventasList(List<Listaventas> listaventasList) {
		this.listaventasList = listaventasList;
	}

	public void onRowEdit(RowEditEvent event) {
		Listaventas listaventas = ((Listaventas) event.getObject());
		System.out.println("Datos de Listaventas: " + listaventas.getId());
		listaventasService.update(listaventas);

		FacesMessage msg = new FacesMessage("Listaventa editada", listaventas.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Listaventas) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Listaventa modificada", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
