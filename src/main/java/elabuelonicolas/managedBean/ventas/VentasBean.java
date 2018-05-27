package elabuelonicolas.managedBean.ventas;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Ventas;
import elabuelonicolas.service.ventas.VentasService;

@Named
public class VentasBean {
	@Inject
	VentasService ventasService;
	private List<Ventas> ventasList;

	public List<Ventas> getVentasList() {
		if (ventasList == null)
			setVentasList(ventasService.findAll());

		return ventasList;
	}

	private void setVentasList(List<Ventas> ventasList) {
		this.ventasList = ventasList;
	}

	public void onRowEdit(RowEditEvent event) {
		Ventas ventas = ((Ventas) event.getObject());
		System.out.println("Datos de Ventas: " + ventas.getId());
		ventasService.update(ventas);

		FacesMessage msg = new FacesMessage("Venta editada", ventas.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Ventas) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta modificada", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
