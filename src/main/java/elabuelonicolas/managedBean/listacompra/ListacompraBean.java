package elabuelonicolas.managedBean.listacompra;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Listacompra;
import elabuelonicolas.service.listacompra.ListacompraService;

@Named
public class ListacompraBean {
	@Inject
	ListacompraService listacomprasService;
	private List<Listacompra> listacomprasList;

	public List<Listacompra> getListacompraList() {
		if (listacomprasList == null)
			setListacompraList(listacomprasService.findAll());

		return listacomprasList;
	}

	private void setListacompraList(List<Listacompra> listacomprasList) {
		this.listacomprasList = listacomprasList;
	}

	public void onRowEdit(RowEditEvent event) {
		Listacompra listacompras = ((Listacompra) event.getObject());
		System.out.println("Datos de Lista de compras: " + listacompras.getId());
		listacomprasService.update(listacompras);

		FacesMessage msg = new FacesMessage("Lista de Compra editada", listacompras.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Listacompra) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista de Compra modificada", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
