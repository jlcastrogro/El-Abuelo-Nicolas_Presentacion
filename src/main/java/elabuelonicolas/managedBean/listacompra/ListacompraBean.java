package elabuelonicolas.managedBean.listacompra;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import elabuelonicolas.bd.domain.Listacompra;
import elabuelonicolas.service.listacompra.ListacompraService;

@Named
@ViewScoped
public class ListacompraBean {
	@Inject
	ListacompraService listacomprasService;
	private List<Listacompra> listacomprasList;
	private List<Listacompra> filteredLiscom;

	@PostConstruct
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
	public String deleteAction(Listacompra order){   
		listacomprasList.remove(order);
		listacomprasService.delete(order.getId());
		return null;
	}
	
	public void onRowSelect(SelectEvent event) {
		Listacompra listacompra = ((Listacompra) event.getObject());
		System.out.println("Datos listacompraSelect: " + listacompra.getId());
		listacomprasService.update(listacompra); 
		
		FacesMessage msg = new FacesMessage("Listacompra seleccionado", listacompra.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		Listacompra listacompra = ((Listacompra) event.getObject());
		System.out.println("Datos listacompraUnselect: " + listacompra.getId());
		listacomprasService.update(listacompra); 
		
		FacesMessage msg = new FacesMessage("Listacompra deseleccionado", listacompra.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Listacompra> getFilteredListacompra(){
		return filteredLiscom;
	}
	
	public void setFilteredListacompra(List<Listacompra> filteredLiscom) {
		this.filteredLiscom = filteredLiscom;
	}
}

