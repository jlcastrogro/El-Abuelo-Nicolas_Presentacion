package elabuelonicolas.managedBean.venta;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Venta;
import elabuelonicolas.service.venta.VentaService;

@Named
public class VentaBean {
	@Inject
	VentaService ventasService;
	private List<Venta> ventasList;

	public List<Venta> getVentaList() {
		if (ventasList == null)
			setVentaList(ventasService.findAll());

		return ventasList;
	}

	private void setVentaList(List<Venta> ventasList) {
		this.ventasList = ventasList;
	}

	public void onRowEdit(RowEditEvent event) {
		Venta ventas = ((Venta) event.getObject());
		System.out.println("Datos de Venta: " + ventas.getId());
		ventasService.update(ventas);

		FacesMessage msg = new FacesMessage("Venta editada", ventas.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Venta) event.getObject()).getId().toString());
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
