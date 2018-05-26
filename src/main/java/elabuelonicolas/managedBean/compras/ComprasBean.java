package elabuelonicolas.managedBean.compras;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Compras;
import elabuelonicolas.service.compras.ComprasService;

@Named
public class ComprasBean {
	@Inject
	ComprasService comprasService;
	private List<Compras> comprasList;

	public List<Compras> getComprasList() {
		if (comprasList == null)
			setComprasList(comprasService.findAll());

		return comprasList;
	}

	private void setComprasList(List<Compras> comprasList) {
		this.comprasList = comprasList;
	}

	public void onRowEdit(RowEditEvent event) {
		Compras compras = ((Compras) event.getObject());
		System.out.println("Datos de Compras: " + compras.getId());
		comprasService.update(compras);

		FacesMessage msg = new FacesMessage("Compra editada", compras.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Compras) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra modificada", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
