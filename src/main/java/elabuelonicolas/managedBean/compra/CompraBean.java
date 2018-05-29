package elabuelonicolas.managedBean.compra;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import elabuelonicolas.bd.domain.Compra;
import elabuelonicolas.service.compra.CompraService;

@Named
public class CompraBean {
	@Inject
	CompraService compraService;
	private List<Compra> compraList;

	public List<Compra> getCompraList() {
		if (compraList == null)
			setCompraList(compraService.findAll());

		return compraList;
	}

	private void setCompraList(List<Compra> compraList) {
		this.compraList = compraList;
	}

	public void onRowEdit(RowEditEvent event) {
		Compra compras = ((Compra) event.getObject());
		System.out.println("Datos de Compra: " + compras.getId());
		compraService.update(compras);

		FacesMessage msg = new FacesMessage("Compra editada", compras.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Compra) event.getObject()).getId().toString());
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
